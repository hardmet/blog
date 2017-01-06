package ru.breathoffreedom.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.breathoffreedom.mvc.models.blog.Post;
import ru.breathoffreedom.mvc.services.blog.BlogService;
import ru.breathoffreedom.mvc.services.blog.filter.PostFilter;
import ru.breathoffreedom.mvc.services.image.service.ImageService;
import ru.breathoffreedom.mvc.services.vfs.VFS;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by boris_azanov on 03.12.16.
 */
@Controller
public class MainPageController {

    private BlogService blogService;

    @Autowired
    public MainPageController(BlogService blogService) {
        this.blogService = blogService;
    }

    /**
     * This method for main page
     * @return - jsp with 3 last posts in the database
     */
    @RequestMapping("/index")
    public ModelAndView listPosts() {
        PostFilter filter = new PostFilter();
        filter.setVisible(true);
        List<Post> allPosts = blogService.getPosts(filter, new PageRequest(0, 3,
                Sort.Direction.DESC, "published")).getContent();
        ModelAndView model = new ModelAndView("/web/index");
        model.addObject("posts", allPosts);
        return model;
    }

    @RequestMapping("/")
    public String onHomePage() {
        return "redirect:/index";
    }
}
