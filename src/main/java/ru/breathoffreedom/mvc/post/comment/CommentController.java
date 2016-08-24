package ru.breathoffreedom.mvc.post.comment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.breathoffreedom.mvc.dao.DAOImpl;

@Controller
public class CommentController {

    private final DAOImpl daoService;

    @Autowired
    public CommentController(DAOImpl daoService) {
        this.daoService = daoService;
    }

    @RequestMapping(value = "/post/{postId}/comment/add", method = RequestMethod.POST)
    public ModelAndView addComment(@ModelAttribute("commentModel") CommentModel comment,
                                   @PathVariable("postId") int postId) {
        System.out.println("CommentController addComment is called");
        String author;
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                //when Anonymous Authentication is enabled
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken)) {
            String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            author = daoService.queryFindUserByEmail(userEmail).getNickName();
        } else {
            author = "Guest";
        }
        String text = comment.getText();
        boolean result = daoService.insertComment(author, text, postId);
        return new ModelAndView("redirect:/post/"+postId, "resultAdd", result);
    }
}
