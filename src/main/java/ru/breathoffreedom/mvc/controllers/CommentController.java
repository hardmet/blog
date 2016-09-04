package ru.breathoffreedom.mvc.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.breathoffreedom.mvc.models.CommentModel;
import ru.breathoffreedom.mvc.services.dao.commentDAO.DAOCommentInterface;
import ru.breathoffreedom.mvc.services.dao.userDAO.DAOUserInterface;

/**
 * This class is controller in Spring MVC, it controls operations with Comments.
 */
@Controller
public class CommentController {
    private final DAOCommentInterface daoCommentService;
    private final DAOUserInterface daoUserService;

    /**
     * Important! Use here interfaces, because Spring framework sensitive on it
     * @param daoCommentService - needed for work with comment table in database
     * @param daoUserService - needed for work with user table in database
     */
    @Autowired
    public CommentController(DAOCommentInterface daoCommentService, DAOUserInterface daoUserService) {
        this.daoCommentService = daoCommentService;
        this.daoUserService = daoUserService;
    }

    /**
     * This method adds new comment to post
     * @param comment - it's a model from post/{postId}/ view page
     * @param postId - it's a id of post that calls add comment method
     * @return - the page of post with added new comment
     */
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
            author = daoUserService.findUserByEmail(userEmail).getNickName();
        } else {
            author = "Guest";
        }
        String text = comment.getText();
        boolean resultOfAddComent = daoCommentService.insertComment(author, text, postId);
        return new ModelAndView("redirect:/post/"+postId, "resultOfAdd", resultOfAddComent);
    }
}
