package ru.breathoffreedom.mvc.services.dao.commentDAO;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.breathoffreedom.mvc.models.CommentModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * This is Data Access Object class for CommentModel entities.
 * This class implements Hibernate CRUD methods.
 */
@Repository
public class DAOCommentImpl implements DAOCommentInterface {

    /**
     * this context described on applicationContext.xml
     * this manager is Hibernate EntityManager
     */
    @PersistenceContext(name = "dataSource")
    private EntityManager entityManager;


    private DAOCommentImpl() {
    }

    @Override
    public CommentModel findCommentById(int commentId) {
        return entityManager.find(CommentModel.class, commentId);
    }

    @Transactional
    public CommentModel insertComment(String author, String text, int postId) {
        System.out.println("DAOCommentImpl insertComment is called");
        CommentModel comment = new CommentModel(author, text, postId);
        comment.setDate(new Date());
        entityManager.persist(comment);
        entityManager.flush();
        return comment;
    }


    public List findCommentsByPostId(int postId) {
        System.out.println("DAOCommentImpl findCommentsByPostId is called");
        return entityManager.createQuery(
                "from CommentModel as comment where comment.post = :postId")
                .setParameter("postId", postId)
                .getResultList();
    }


    @Override
    @Transactional
    public boolean updateComment(int commentId, String text) {
        System.out.println("DAOCommentImpl updateComment is called");
        CommentModel updatedComment = entityManager.find(CommentModel.class, commentId);
        updatedComment.setText(text);
        entityManager.flush();
        return true;
    }

    @Override
    @Transactional
    public boolean deleteComment(int commentId) {
        System.out.println("DAOCommentImpl deleteComment is called");
        CommentModel commentToRemove = entityManager.find(CommentModel.class, commentId);
        entityManager.remove(commentToRemove);
        entityManager.flush();
        return true;
    }
}
