package ru.breathoffreedom.mvc.services.dao.commentDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.breathoffreedom.mvc.models.CommentModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.util.List;

/**
 * This is class for test DAOCommentImpl
 * There are some tests to check CRUD methods
 * Transactional annotation is for correct test pass and because 'After'
 * annotated method contains insert/update query for return DB to start state.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class DAOCommentImplTest {

    @PersistenceContext(name = "dataSource")
    private EntityManager entityManager;

    private int commentId;
    private String author;
    private String text;
    private int postId;
    private int numberOfComments;
    private String anotherText;

    @Before
    public void setUp() throws Exception {
        author = "Admin";
        text = "My first post!";
        postId = 1;
        anotherText = "New Text";
        commentId = 1;
        numberOfComments = getNumberOfComments();
    }

    @After
    public void tearDown() throws Exception {
        author = null;
        text = null;
        postId = 0;
        anotherText = null;

        String query = "ALTER TABLE comment AUTO_INCREMENT = ?";
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter(1, numberOfComments);
        nativeQuery.executeUpdate();

        numberOfComments = 0;
    }

    /**
     * This method for count current number of comments in Table Comment
     * @return - current number of comments at the table
     */
    public int getNumberOfComments() {
        String query = "from CommentModel";
        TypedQuery<CommentModel> typedQuery = entityManager.createQuery(query, CommentModel.class);
        return typedQuery.getResultList().size();
    }

    @Transactional
    @Test
    public void insertComment() throws Exception {
        System.out.println("Test insertComment start");
        CommentModel comment = new CommentModel(author, anotherText, postId);
        entityManager.persist(comment);
        CommentModel createdComment = entityManager.find(CommentModel.class, comment.getId());
        assert (createdComment.getAuthor().equals(author));
        assert (createdComment.getText().equals(anotherText));
        assert (comment.getId() != 0);
        assert ((numberOfComments + 1) == getNumberOfComments());
        entityManager.remove(comment);
        entityManager.flush();
    }

    @Transactional
    @Test
    public void findCommentsByPostId() throws Exception {
        System.out.println("Test findCommentsByPostId start");
        List commentsToPost = entityManager.createQuery(
                "from CommentModel as comment where comment.post = ?")
                .setParameter(1, postId)
                .getResultList();
        assert commentsToPost.size() != 0;
        for (Object comment : commentsToPost) {
            System.out.print("Comment id is: " + ((CommentModel)comment).getId() + "\t");
            System.out.println("Comment text is: " + ((CommentModel)comment).getText());
        }
    }

    @Transactional
    @Test
    public void updateComment() throws Exception {
        System.out.println("Test updateComment start");
        CommentModel updatedComment = entityManager.find(CommentModel.class, commentId);
        updatedComment.setText(anotherText);
        updatedComment = entityManager.find(CommentModel.class, commentId);
        entityManager.flush();
        assert updatedComment.getText().equals(anotherText);
        updatedComment.setText(text);
        entityManager.persist(updatedComment);
        entityManager.flush();
    }

    @Transactional
    @Test
    public void deleteComment() throws Exception {
        System.out.println("Test deleteComment start");
        CommentModel commentToRemove = new CommentModel(author, text, postId);
        entityManager.persist(commentToRemove);
        entityManager.flush();
        entityManager.remove(commentToRemove);
        entityManager.flush();
        assert entityManager.find(CommentModel.class, numberOfComments+1) == null;
        assert ((numberOfComments) == getNumberOfComments());
    }

}