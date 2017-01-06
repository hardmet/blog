package ru.breathoffreedom.mvc.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import ru.breathoffreedom.mvc.models.blog.Comment;
import ru.breathoffreedom.mvc.models.user.Author;
import ru.breathoffreedom.mvc.services.blog.BlogService;


/**
 * This class is controller in Spring MVC, it controls operations with Comments.
 */
@Controller
public class CommentController {

    private BlogService blogService;

    /**
     * Important! Use here interfaces, because Spring framework sensitive on it
     * @param blogService - needed for work with comment table in database
     */
    @Autowired
    public CommentController(BlogService blogService) {
        this.blogService = blogService;
    }

    /**
     * This method adds new comment to post
     * @param comment - it's a model from post/{postId}/ view page
     * @param postId - it's a id of post that calls add comment method
     * @return - the page of post with added new comment
     */
    @MessageMapping("/commentToPost/{postId}")
    @SendTo("/service/add/{postId}")
    public Comment addComment(Comment comment, @DestinationVariable("postId") int postId) {
        if (comment.getAuthor().getEmail() != null) {
            String email = comment.getAuthor().getEmail();
            Author author = blogService.getAuthor(email);
            if (author == null) {
                return null;
            }
            comment.setAuthor(author);
            return blogService.save(comment);
        }
        return null;
    }

    /**
     * deleting the comment from the post
     * @param commentId - id which contained in the path of MessageMapping
     * @return - result of deleting
     */
    @MessageMapping("/commentToDelete/{commentId}")
    @SendTo("/service/comment/delete/{commentId}")
    public String deleteComment(@DestinationVariable("commentId") int commentId) {
        if (blogService.getComment(commentId) == null) {
            return "failure";
        }
        blogService.removeComment(commentId);
        return "success";
    }
}
