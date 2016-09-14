package ru.breathoffreedom.mvc.services.dao.postDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.breathoffreedom.mvc.models.PostModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

/**
 * This is class for test DAOPostImpl
 * There are some tests to check CRUD methods
 * Transactional annotation is for correct test pass and because 'After'
 * annotated method contains insert/update query for return DB to start state.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:dispatcher-servlet.xml", "classpath:applicationContext.xml"})
public class DAOPostImplTest {

    @PersistenceContext(name = "dataSource")
    private EntityManager entityManager;

    @Autowired
    private DAOPostInterface daoPostService;

    private String author;
    private String title;
    private String subtitle;
    private String text;
    private int postId;
    private int numberOfPosts;
    private String anotherTitle;
    private String anotherSubtitle;
    private String anotherText;

    @Before
    public void setUp() throws Exception {
        author = "Admin";
        title = "FIRSTTITLE";
        subtitle = "SUBTITLE";
        text = "My first post!";
        postId = 1;
        numberOfPosts = entityManager.createQuery("from PostModel",
                PostModel.class).getResultList().size();
        anotherTitle = "New Title";
        anotherSubtitle = "New SubTitle";
        anotherText = "New Text";
    }

    @After
    public void tearDown() throws Exception {
        author = null;
        title = null;
        subtitle = null;
        text = null;
        postId = 0;
        anotherTitle = null;
        anotherSubtitle = null;
        anotherText = null;
        String query = "ALTER TABLE post AUTO_INCREMENT = ?";
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter(1, numberOfPosts);
        nativeQuery.executeUpdate();
        numberOfPosts = 0;
    }

    @Transactional
    @Test
    public void findAllPostsTest() throws Exception {
        System.out.println("Test findAllPosts start");
        List<PostModel> allPostsList = daoPostService.findAllPosts();
        assert (allPostsList.size() == numberOfPosts);
        for (int i = 0; i < allPostsList.size(); i++) {
            if (i % 6 == 0) {
                System.out.println();
            }
            System.out.print(allPostsList.get(i).getTitle() + "\t");
        }
    }

    @Transactional
    @Test
    public void findPostByIdTest() throws Exception {
        System.out.println("Test getPostById start");
        int id = daoPostService.insertPost(author, title, subtitle, text);
        PostModel testPost = daoPostService.findPostById(id);
        assert testPost != null;
        assert (testPost.getAuthor().equals(author));
        assert (testPost.getTitle().equals(title));
        assert (testPost.getSubtitle().equals(subtitle));
        assert (testPost.getId() == id);
        assert (testPost.getText().equals(text));
        assert daoPostService.deletePost(id);
    }

    @Transactional
    @Test
    public void updatePostTest() throws Exception {
        System.out.println("Test updatePostAndCheckResult start");
        PostModel testPost = daoPostService.findPostById(postId);
        String startPostTitle = testPost.getTitle();
        String startPostSubtitle = testPost.getSubtitle();
        String startPostText = testPost.getText();
        assert daoPostService.updatePost(postId, anotherTitle, anotherSubtitle,anotherText);
        PostModel changedPost = daoPostService.findPostById(postId);
        assert (changedPost.getTitle().equals(anotherTitle));
        assert (changedPost.getSubtitle().equals(anotherSubtitle));
        assert (changedPost.getText().equals(anotherText));
        assert daoPostService.updatePost(postId, startPostTitle, startPostSubtitle,startPostText);
    }

    @Transactional
    @Test
    public void insertAndDeletePostTest() throws Exception {
        System.out.println("Test insertPost start");
        int insertedPostId = daoPostService.insertPost(author, anotherTitle, anotherSubtitle, anotherText);
        PostModel createdPost = daoPostService.findPostById(insertedPostId);
        assert (createdPost.getAuthor().equals(author));
        assert (createdPost.getTitle().equals(anotherTitle));
        assert (createdPost.getSubtitle().equals(anotherSubtitle));
        assert (createdPost.getText().equals(anotherText));
        assert daoPostService.deletePost(insertedPostId);
        assert daoPostService.findPostById(insertedPostId) == null;
    }

}