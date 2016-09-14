package ru.breathoffreedom.mvc.services.dao.commentDAO;

import ru.breathoffreedom.mvc.models.CommentModel;

import java.util.List;

/**
 *
 */

public interface DAOCommentInterface {

    CommentModel findCommentById(int commentId);

    CommentModel insertComment(String author, String text, int postId);

    List findCommentsByPostId(int id);

    boolean updateComment(int commentId, String text);

    boolean deleteComment(int idComment);
}
