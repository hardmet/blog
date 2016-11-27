package ru.breathoffreedom.mvc.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.breathoffreedom.mvc.models.blog.Comment;
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
    @MessageMapping("/commentToPost/{postId}")
    @SendTo("/email/add/{postId}")
    public Comment addComment(Comment comment, @DestinationVariable("postId") int postId) {
        String email = comment.getAuthor();
        String author;
        if (!email.equals("")) {
            author = daoUserService.findUserByEmail(email).getNickName();
        } else {
            author = "Guest";
        }
        String text = comment.getText();
        Comment addedComment = daoCommentService.insertComment(author, text, postId);
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
        return daoCommentService.deleteComment(commentId);
    }
}
