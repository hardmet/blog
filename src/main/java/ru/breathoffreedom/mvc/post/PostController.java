package ru.breathoffreedom.mvc.post;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.breathoffreedom.mvc.dao.DAOImpl;

import javax.annotation.security.RolesAllowed;
import java.util.*;

@Controller
public class PostController {

    private final DAOImpl daoService;

    @Autowired
    public PostController(DAOImpl daoService) {
        this.daoService = daoService;
    }


    @RolesAllowed(value={"ROLE_ADMIN"})
    @RequestMapping(value = "/post/send", method = RequestMethod.POST)
    public ModelAndView post(@ModelAttribute("postModel") PostModel postModel) {
        System.out.println("PostController post is called");
        String title = postModel.getTitle();
        String subtitle = postModel.getSubtitle();
        String text = postModel.getText();
        boolean result = daoService.insertPost(title, subtitle, text);
        return new ModelAndView("redirect:/newpost", "resultSending", result);
    }

    @RequestMapping("/index")
    public ModelAndView listPosts() {
        List<PostModel> allPosts = daoService.queryFindAllPostsJPA();
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

    @RequestMapping("/allposts")
    public ModelAndView listAllPosts() {
        List<PostModel> allPosts =  daoService.queryFindAllPostsJPA();
        Map <String, PostModel> data = new TreeMap<>(Collections.reverseOrder());
        for (PostModel post:allPosts) {
            Date date = post.getDate();
            data.put(String.valueOf(date), post);
        }
        return new ModelAndView("/post/allposts", "data", data);
    }

    @RequestMapping(value = "/post/{postId}", method = RequestMethod.GET)
    public ModelAndView findPostById(@PathVariable("postId") int postId) {
        System.out.println("ORMController queryFindByIdPost is called");
        PostModel post = daoService.queryFindPostById(postId);
        List comments = daoService.queryFindCommentsByPostId(postId);
        Map<String, Object> data = new HashMap<>();
        data.put("post", post);
        data.put("comments", comments);
        return new ModelAndView("/post/post", "data", data);
    }

}