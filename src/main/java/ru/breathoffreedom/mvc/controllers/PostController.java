package ru.breathoffreedom.mvc.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.breathoffreedom.mvc.models.ImageModel;
import ru.breathoffreedom.mvc.models.PostModel;
import ru.breathoffreedom.mvc.services.dao.commentDAO.DAOCommentInterface;
import ru.breathoffreedom.mvc.services.dao.imageDAO.DAOImageInterface;
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
public class PostController {

    private final DAOPostInterface daoPostService;
    private final DAOCommentInterface daoCommentService;
    private final DAOUserInterface daoUserService;
    private final DAOImageInterface daoImageService;
    private final VFS fileSystem;
    private volatile long currentIdOfPost;

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
        currentIdOfPost = daoPostService.getLastId();
    }


    /**
     * This method is for add new post to the blog.
     * @param postModel - is model of Post what been sent to server from post/newpost jsp
     * @return - the jsp with result of adding post to the blog and database in all
     */
    @RolesAllowed(value={"ROLE_ADMIN"})
    @RequestMapping(value = "/post/add", method = RequestMethod.POST)
    public ModelAndView createPost(@ModelAttribute("postModel") PostModel postModel) {
        System.out.println("PostController post is called");
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        String author = daoUserService.findUserByEmail(userEmail).getNickName();
        String title = postModel.getTitle();
        String subtitle = postModel.getSubtitle();
        String text = postModel.getText();
        int postId = daoPostService.insertPost(author, title, subtitle, text);
        if (postId == 0) {
            return new ModelAndView("redirect:/newpost", "resultAdding", false);
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

    /**
     * This method for main page
     * @return - jsp with 3 last posts in the database
     */
    @RequestMapping("/index")
    public ModelAndView listPosts() {
        System.out.println("PostController listPosts is called");
        List<PostModel> allPosts = daoPostService.findAllPosts();
        Iterator<PostModel> it = allPosts.iterator();
        int countPosts = 0;
        Map<String, PostModel> data = new TreeMap<>(Collections.reverseOrder());
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
        System.out.println("PostController home is called");
        return "redirect:/index";
    }

    /**
     * This method for page with all posts in blog
     *
     * @return - all posts that exists in the data base
     */
    @RequestMapping("/allposts")
    public ModelAndView listAllPosts() {
        System.out.println("PostController listAllPosts is called");
        List<PostModel> allPosts = daoPostService.findAllPosts();
        Map<String, PostModel> data = new TreeMap<>(Collections.reverseOrder());
        for (PostModel post : allPosts) {
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
    public ModelAndView getPostById(@PathVariable("postId") int postId) {
        System.out.println("PostController getPostById is called");
        PostModel post = daoPostService.findPostById(postId);
        if (post == null) {
            return new ModelAndView("redirect:/error404");
        }
        List comments = daoCommentService.findCommentsByPostId(postId);
        List images = daoImageService.findImagesByPostId(postId);
        for (Object image : images) {
            ((ImageModel) image).setPathToImage(fileSystem.getRoot() +
                    File.separator + ((ImageModel) image).getPathToImage() +
                    File.separator + ((ImageModel) image).getId() + ".jpg");
            System.out.println(((ImageModel) image).getPathToImage());
        }
        Map<String, Object> postRelatedData = new HashMap<>();
        postRelatedData.put("post", post);
        postRelatedData.put("comments", comments);
        postRelatedData.put("images", images);
        return new ModelAndView("/post/post", "postRelatedData", postRelatedData);
    }

    /**
     * This method is for counting current Id of post and get View of post create form
     * @return - view with current post Id at the Data Base
     */
    @RolesAllowed(value={"ROLE_ADMIN"})
    @RequestMapping(value = "/newpost", method = RequestMethod.GET)
    public ModelAndView getNewPostForm() {
        System.out.println("PostController getNewPostForm is called");
        currentIdOfPost++;
        return new ModelAndView("/post/newpost", "currentIdOfPost", currentIdOfPost);
    }
}