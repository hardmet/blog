package ru.breathoffreedom.mvc.services.dao.commentDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.breathoffreedom.mvc.models.CommentModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

/**
 * This is class for test DAOCommentImpl
 * There are some tests to check CRUD methods
 * Transactional annotation is for correct test pass and because 'After'
 * annotated method contains insert/update query for return DB to start state.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:dispatcher-servlet.xml", "classpath:applicationContext.xml"})
public class DAOCommentImplTest {

    @PersistenceContext(name = "dataSource")
    private EntityManager entityManager;

    @Autowired
    private DAOCommentInterface daoCommentService;

    private String author;
    private String text;
    private int postId;
    private int numberOfLastComment;
    private String anotherText;

    @Before
    public void setUp() throws Exception {
        author = "Admin";
        text = "My first post!";
        postId = 1;
        anotherText = "New Text";
        numberOfLastComment = getNumberOfLastComment();
    }

    @After
    public void tearDown() throws Exception {
        author = null;
        text = null;
        postId = 0;
        anotherText = null;

        String query = "ALTER TABLE comment AUTO_INCREMENT = ?";
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter(1, numberOfLastComment);
        nativeQuery.executeUpdate();

        numberOfLastComment = 0;
    }

    /**
     * This method for count current number of comments in Table Comment
     * @return - current number of comments at the table
     */
    public int getNumberOfLastComment() {
        List comments = entityManager.createQuery("from CommentModel order by id desc")
                .getResultList();
        return ((CommentModel) comments.get(0)).getId();
    }

    @Transactional
    @Test
    public void insertAndDeleteCommentTest() throws Exception {
        System.out.println("Test insertComment start");
        CommentModel createdComment = daoCommentService.insertComment(author, anotherText, postId);
        int commentId = createdComment.getId();
        assert (createdComment.getAuthor().equals(author));
        assert (createdComment.getText().equals(anotherText));
        assert (commentId != 0);
        assert ((numberOfLastComment + 1) == getNumberOfLastComment());
        assert daoCommentService.deleteComment(commentId);
    }

    @Transactional
    @Test
    public void findCommentsByPostIdTest() throws Exception {
        System.out.println("Test findCommentsByPostId start");
        List commentsToPost = daoCommentService.findCommentsByPostId(postId);
        assert commentsToPost.size() != 0;
        for (Object comment : commentsToPost) {
            System.out.print("Comment id is: " + ((CommentModel)comment).getId() + "\t");
            System.out.println("Comment text is: " + ((CommentModel)comment).getText());
        }
    }

    @Transactional
    @Test
    public void updateCommentTest() throws Exception {
        System.out.println("Test updateComment start");
        CommentModel commentForTest = daoCommentService.insertComment(author, text, postId);
        int testId = commentForTest.getId();
        assert daoCommentService.updateComment(testId, anotherText);
        assert daoCommentService.updateComment(testId, text);
        assert daoCommentService.deleteComment(testId);
    }
}