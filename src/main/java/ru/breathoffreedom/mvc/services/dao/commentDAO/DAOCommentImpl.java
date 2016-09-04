package ru.breathoffreedom.mvc.services.dao.commentDAO;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.breathoffreedom.mvc.models.CommentModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @Transactional
    public boolean insertComment(String author, String text, int postId) {
        CommentModel comment = new CommentModel(author, text, postId);
        entityManager.persist(comment);
        entityManager.flush();
        return comment.getId() != 0;
    }


    public List findCommentsByPostId(int id) {
        System.out.println("ORMService queryFindCommentsByPostId is called");
        return entityManager.createQuery(
                "from CommentModel as comment where comment.post = ?")
                .setParameter(1, id)
                .getResultList();
    }


    @Override
    @Transactional
    public boolean updateComment(int commentId, String text) {
        CommentModel updatedComment = entityManager.find(CommentModel.class, commentId);
        updatedComment.setText(text);
        entityManager.flush();
        return true;
    }

    @Override
    @Transactional
    public boolean deleteComment(int commentId) {
        CommentModel commentToRemove = entityManager.find(CommentModel.class, commentId);
        entityManager.remove(commentToRemove);
        entityManager.flush();
        return true;
    }
}
