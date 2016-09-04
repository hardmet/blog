package ru.breathoffreedom.mvc.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.breathoffreedom.mvc.models.PostModel;
import ru.breathoffreedom.mvc.services.dao.commentDAO.DAOCommentInterface;
import ru.breathoffreedom.mvc.services.dao.postDAO.DAOPostInterface;
import ru.breathoffreedom.mvc.services.dao.userDAO.DAOUserInterface;

import javax.annotation.security.RolesAllowed;
import java.util.*;

/**
 * This class is controller in Spring MVC, it controls operations with Posts.
 */
@Controller
public class PostController {

    private final DAOPostInterface daoPostService;
    private final DAOCommentInterface daoCommentService;
    private final DAOUserInterface daoUserService;

    /**
     * Important! Use here interfaces, because Spring framework sensitive on it
     * @param daoPostService - needed for work with post table in database
     * @param daoCommentService - needed for work with comment table in database
     * @param daoUserService - needed for work with user table in database
     */
    @Autowired
    public PostController(DAOPostInterface daoPostService,
                          DAOCommentInterface daoCommentService,
                          DAOUserInterface daoUserService) {
        this.daoPostService = daoPostService;
        this.daoCommentService = daoCommentService;
        this.daoUserService = daoUserService;
    }


    /**
     * This method is for add new post to the blog.
     * @param postModel - is model of Post what been sent to server from post/newpost jsp
     * @return - the jsp with result of adding post to the blog and database in all
     */
    @RolesAllowed(value={"ROLE_ADMIN"})
    @RequestMapping(value = "/post/add", method = RequestMethod.POST)
    public ModelAndView post(@ModelAttribute("postModel") PostModel postModel) {
        System.out.println("PostController is called");
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        String author = daoUserService.findUserByEmail(userEmail).getNickName();
        String title = postModel.getTitle();
        String subtitle = postModel.getSubtitle();
        String text = postModel.getText();
        boolean result = daoPostService.insertPost(author, title, subtitle, text);
        return new ModelAndView("redirect:/newpost", "resultAdding", result);
    }

    /**
     * This method for main page
     * @return - jsp with 3 last posts in the database
     */
    @RequestMapping("/index")
    public ModelAndView listPosts() {
        List<PostModel> allPosts = daoPostService.findAllPosts();
        Iterator<PostModel> it = allPosts.iterator();
        int countPosts = 0;
        Map <String, PostModel> data = new TreeMap<>(Collections.reverseOrder());
        while (countPosts < 3 && it.hasNext()) {
            PostModel post = it.next();
            Date date = post.getDate();
            data.put(String.valueOf(date), post);
            countPosts++;
        }
        return new ModelAndView("/index", "data", data);
    }

    @RequestMapping("/")
    public String home() {
        return "redirect:/index";
    }

    /**
     * This method for page with all posts in blog
     * @return - all posts that exists in the data base
     */
    @RequestMapping("/allposts")
    public ModelAndView listAllPosts() {
        List<PostModel> allPosts =  daoPostService.findAllPosts();
        Map <String, PostModel> data = new TreeMap<>(Collections.reverseOrder());
        for (PostModel post:allPosts) {
            Date date = post.getDate();
            data.put(String.valueOf(date), post);
        }
        return new ModelAndView("/post/allposts", "data", data);
    }

    /**
     * This method is for one post
     * @param postId - it's id of post in the post table at the data base
     * @return - post and comments related with it
     */
    @RequestMapping(value = "/post/{postId}", method = RequestMethod.GET)
    public ModelAndView findPostById(@PathVariable("postId") int postId) {
        System.out.println("ORMController queryFindByIdPost is called");
        PostModel post = daoPostService.findPostById(postId);
        if (post == null) {
            return new ModelAndView("redirect:/errorpage");
        }
        List comments = daoCommentService.findCommentsByPostId(postId);
        Map<String, Object> postAndComments = new HashMap<>();
        postAndComments.put("post", post);
        postAndComments.put("comments", comments);
        return new ModelAndView("/post/post", "postAndComments", postAndComments);
    }
}