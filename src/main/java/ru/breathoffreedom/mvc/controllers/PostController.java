package ru.breathoffreedom.mvc.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import ru.breathoffreedom.mvc.models.user.Author;
import ru.breathoffreedom.mvc.services.blog.BlogService;
import ru.breathoffreedom.mvc.services.blog.filter.PostFilter;
import ru.breathoffreedom.mvc.services.image.ImageFormat;
import ru.breathoffreedom.mvc.services.image.service.ImageService;
import ru.breathoffreedom.mvc.util.ModelBuilder;

import javax.annotation.security.RolesAllowed;
import java.util.*;

/**
 * This class is controller in Spring MVC, it controls operations with Posts.
 */
@Controller
@RequestMapping(value = "/post")
public class PostController {

    private BlogService blogService;
    private ImageService imageService;

    /**
     * Important! Use here interfaces, because Spring framework sensitive on it
     *
     * @param blogService  - needed for work with post table in database
     *                     needed for work with comment table in database
     *                     needed for work with user table in database
     * @param imageService - needed for work with image table in database
     */
    @Autowired
    public PostController(BlogService blogService, ImageService imageService) {
        this.blogService = blogService;
        this.imageService = imageService;
    }

    /**
     * This method for page with all posts in blog
     *
     * @return - all posts that exists in the data base
     */
    @RequestMapping("/all")
    public ModelAndView listAllPosts() {
        PostFilter filter = new PostFilter();
        filter.setVisible(true);
        List<Post> posts = blogService.getPosts(filter, new PageRequest(0, 100,
                Sort.Direction.DESC, "published")).getContent();
        return new ModelAndView("/web/post/all", "posts", posts);
    }

    @RequestMapping(value = "/{postId}", method = RequestMethod.GET)
    public ModelAndView getPostById(@PathVariable("postId") int postId) {
        Post post = blogService.getPost(postId);
        ModelAndView mav = new ModelAndView("/web/post/post");
        if (post == null) {
            return new ModelAndView("redirect:/error404");
        }
        mav.addObject("post", post);
        if (post.isHasMainImage()) {
            mav.addObject("postMainImage", imageService.getPostMainImage(post.getId()));
            mav.addObject("hasMainImage", true);
        } else {
            mav.addObject("hasMainImage", false);
        }
        mav.addObject("images", imageService.getImagesFromFileSystem(post));
        mav.addObject("comments", blogService.getComments(post));
        return mav;
    }

    /**
     * This method is for one post
     *
     * @param postId - it's id of post in the post table at the data base
     * @return - post and comments related with it
     */
    @RequestMapping(value = "/{postId}/edit/", method = RequestMethod.GET)
    public ModelAndView onEditPost(@PathVariable("postId") int postId) {
        Post post = blogService.getPost(postId);
        if (post == null) {
            return new ModelAndView("redirect:/error404");
        }

        ModelAndView mav = new ModelAndView("/editor/editorPost");
        fillPost(mav, post);
        return mav;
    }

    /**
     * This method is for counting current Id of post and get View of post create form
     *
     * @return - view with current post Id at the Data Base
     */
    @RolesAllowed(value = {"ROLE_ADMIN"})
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView getNewPostForm() {
        return new ModelAndView("/editor/editorPost",
                "post", new Post());
    }

    /**
     * This method is for add new post to the blog.
     *
     * @param post - is model of Post what been sent to server from post/newpost jsp
     * @return - the jsp with result of adding post to the blog and database in all
     */
    @RolesAllowed(value = {"ROLE_ADMIN"})
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addPost(@ModelAttribute Post post) {
        Post postModel = new Post();
        save(postModel, post);

        ModelAndView model = new ModelAndView("redirect:/post/" + postModel.getId() + "/edit/");
        fillPost(model, postModel);
        return model;
    }

    @RolesAllowed(value = {"ROLE_ADMIN"})
    @RequestMapping(value = "/{postId}/edit/", method = RequestMethod.POST)
    public ModelAndView editPost(@ModelAttribute Post post, @PathVariable("postId") Integer postId) {
        Post postModel = new Post();
        if (postId != null && postId != 0) {
            postModel.setId(postId);
        }
        save(postModel, post);

        ModelAndView model = new ModelAndView("/editor/editorPost");
        fillPost(model, postModel);
        return model;
    }

    private void save(Post newPost, Post oldPost) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Author author = blogService.getAuthor(userEmail);
        newPost.setAuthor(author);
        newPost.setPublished(oldPost.getPublished());
        newPost.setTitle(oldPost.getTitle());
        newPost.setSubtitle(oldPost.getSubtitle());
        newPost.setVisible(oldPost.isVisible());
        newPost.setText(oldPost.getText());
        blogService.save(newPost);
    }

    private void fillPost(ModelAndView mav, Post post) {
        mav.addObject("post", post);
        if (post.isHasMainImage()) {
            mav.addObject("postMainImage", imageService.getPostMainImage(post.getId()));
        }

        List<Image> postImages = imageService.getImages(post);
        Map<String, List<String>> images = new HashMap<>();
        int i = 0;
        for (Image image : postImages) {
            List<String> img = new ArrayList<>();
            img.add(String.valueOf(image.getId()));
            img.add(imageService.getImageFromFileSystem(image, ImageFormat.postContentImage));
            images.put(String.valueOf(i++), img);
        }
        mav.addObject("images", images);
    }
}