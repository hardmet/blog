package ru.breathoffreedom.mvc.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.breathoffreedom.mvc.models.CommentModel;
import ru.breathoffreedom.mvc.services.dao.commentDAO.DAOCommentInterface;
import ru.breathoffreedom.mvc.services.dao.userDAO.DAOUserInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;


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
    @MessageMapping("/commentToPost/{postId}")
    @SendTo("/service/add/{postId}")
    public CommentModel addComment(CommentModel comment, @DestinationVariable("postId") int postId) {
        System.out.println("CommentController addComment is called");
        String email = comment.getAuthor();
        String author;
        if (!email.equals("")) {
            author = daoUserService.findUserByEmail(email).getNickName();
        } else {
            author = "Guest";
        }
        String text = comment.getText();
        CommentModel addedComment = daoCommentService.insertComment(author, text, postId);
        if (addedComment.getId() == 0 ) {
            return null;
        }
        return addedComment;
    }

    /**
     * deleting the comment from the post
     * @param commentId - id which contained in the path of MessageMapping
     * @return - result of deleting
     */
    @MessageMapping("/commentToDelete/{commentId}")
    @SendTo("/comment/delete/{commentId}")
    public boolean deleteComment(@DestinationVariable("commentId") int commentId) {
        System.out.println("CommentController deleteComment is called");
        return daoCommentService.deleteComment(commentId);
    }
}
