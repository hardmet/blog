package ru.breathoffreedom.mvc.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.breathoffreedom.mvc.models.blog.Post;
import ru.breathoffreedom.mvc.models.file.Image;
import ru.breathoffreedom.mvc.services.dao.commentDAO.DAOCommentInterface;
import ru.breathoffreedom.mvc.services.dao.postDAO.DAOPostInterface;
import ru.breathoffreedom.mvc.services.dao.userDAO.DAOUserInterface;
import ru.breathoffreedom.mvc.services.vfs.VFS;

import javax.annotation.security.RolesAllowed;
import java.io.File;
import java.util.*;

/**
 * This class is controller in Spring MVC, it controls operations with Posts.
 */
@Controller
@RequestMapping(value = "/post")
public class PostController {

    private final DAOPostInterface daoPostService;
    private final DAOCommentInterface daoCommentService;
    private final DAOUserInterface daoUserService;
    private final DAOImageInterface daoImageService;
    private final VFS fileSystem;

    /**
     * Important! Use here interfaces, because Spring framework sensitive on it
     * @param daoPostService    - needed for work with post table in database
     * @param daoCommentService - needed for work with comment table in database
     * @param daoUserService    - needed for work with user table in database
     * @param daoImageService   - needed for work with image table in database
     */
    @Autowired
    public PostController(DAOPostInterface daoPostService, DAOCommentInterface daoCommentService,
                          DAOUserInterface daoUserService, DAOImageInterface daoImageService,
                          VFS fileSystem) {
        this.daoPostService = daoPostService;
        this.daoCommentService = daoCommentService;
        this.daoUserService = daoUserService;
        this.daoImageService = daoImageService;
        this.fileSystem = fileSystem;
    }

    /**
     * This method for main page
     * @return - jsp with 3 last posts in the database
     */
    @RequestMapping("/index")
    public ModelAndView listPosts() {
        List<Post> allPosts = daoPostService.findAllPosts();
        Iterator<Post> it = allPosts.iterator();
        int countPosts = 0;
        Map<String, Post> data = new TreeMap<>(Collections.reverseOrder());
        while (countPosts < 3 && it.hasNext()) {
            Post post = it.next();
            Date date = post.getPublished();
            data.put(String.valueOf(date), post);
            countPosts++;
        }
        return new ModelAndView("/web/index", "data", data);
    }

    @RequestMapping("/")
    public String home() {
        return "redirect:/index";
    }

    /**
     * This method for page with all posts in blog
     *
     * @return - all posts that exists in the data base
     */
    @RequestMapping("/allposts")
    public ModelAndView listAllPosts() {
        List<Post> allPosts = daoPostService.findAllPosts();
        Map<String, Post> data = new TreeMap<>(Collections.reverseOrder());
        for (Post post : allPosts) {
            Date date = post.getPublished();
            data.put(String.valueOf(date), post);
        }
        return new ModelAndView("/web/allposts", "data", data);
    }

    /**
     * This method is for one post
     * @param postId - it's id of post in the post table at the data base
     * @return - post and comments related with it
     */
    @RequestMapping(value = "/{postId}/edit/", method = RequestMethod.GET)
    public ModelAndView getPostById(@PathVariable("postId") int postId) {
        Post post = daoPostService.findPostById(postId);
        ModelAndView mav = new ModelAndView("/editor/editorPost");
        if (post == null) {
            return new ModelAndView("redirect:/error404");
        }

        List images = daoImageService.findImagesByPostId(postId);
        for (Object image : images) {
            ((Image) image).setPathToImage(
                    File.separator + ((Image) image).getPathToImage() +
                    File.separator + ((Image) image).getId() + ".jpg");
        }

        mav.addObject("post", post);
        mav.addObject("images", images);
        return mav;
    }

    /**
     * This method is for counting current Id of post and get View of post create form
     * @return - view with current post Id at the Data Base
     */
    @RolesAllowed(value={"ROLE_ADMIN"})
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView getNewPostForm() {
        return new ModelAndView("/editor/editorPost", "post", new Post());
    }

    /**
     * This method is for add new post to the blog.
     * @param post - is model of Post what been sent to server from post/newpost jsp
     * @return - the jsp with result of adding post to the blog and database in all
     */
    @RolesAllowed(value={"ROLE_ADMIN"})
    @RequestMapping(value = {"/add", "/{postId}/edit/"}, method = RequestMethod.POST)
    public ModelAndView createPost(@ModelAttribute("postModel") Post post, @PathVariable("postId") Integer postId) {
        if (postId == null) {
            return getNewPostForm();
        } else {

        }
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        String author = daoUserService.findUserByEmail(userEmail).getNickName();
        String title = post.getTitle();
        String subtitle = post.getSubtitle();
        String text = post.getText();
        int postId = daoPostService.insertPost(author, title, subtitle, text);
        if (postId == 0) {
            return getNewPostForm();
        } else {
            String pathToImages = "post" + File.separator + postId;
            int countOfImages = fileSystem.getCountOfImages(pathToImages);
            int [] imageIds = daoImageService.insertImages(postId, pathToImages, countOfImages);
            for (int imageId:imageIds) {
                System.out.print(imageId + "\t");
            }
            if (!fileSystem.renameImages(pathToImages, imageIds)) {
                return new ModelAndView("redirect:/newpost", "resultAdding", false);
            }
            return new ModelAndView("redirect:/post/" + postId);
        }
    }
}