package ru.breathoffreedom.mvc.services.dao.commentDAO;

import java.util.List;

/**
 *
 */

public interface DAOCommentInterface {

    boolean insertComment(String author, String text, int postId);

    List findCommentsByPostId(int id);

    boolean updateComment(int commentId, String text);

    boolean deleteComment(int idComment);
}
