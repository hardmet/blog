package ru.breathoffreedom.mvc.services.dao.postDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.breathoffreedom.mvc.models.PostModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.util.List;

/**
 * This is class for test DAOPostImpl
 * There are some tests to check CRUD methods
 * Transactional annotation is for correct test pass and because 'After'
 * annotated method contains insert/update query for return DB to start state.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class DAOPostImplTest {

    @PersistenceContext(name = "dataSource")
    private EntityManager entityManager;

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
        numberOfPosts =  entityManager.createQuery("from PostModel",
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
    public void findAllPosts() throws Exception {
        System.out.println("Test findAllPosts start");
        String query = "from PostModel order by id desc";
        TypedQuery<PostModel> typedQuery = entityManager.createQuery(query, PostModel.class);
        List<PostModel> allPostsList = typedQuery.getResultList();
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
    public void findPostById() throws Exception {
        System.out.println("Test findPostById start");
        PostModel testPost = entityManager.find(PostModel.class, postId);
        if (testPost != null) {
            assert (testPost.getAuthor().equals(author));
            assert (testPost.getTitle().equals(title));
            assert (testPost.getSubtitle().equals(subtitle));
            assert (testPost.getId() == (postId));
            assert (testPost.getText().equals(text));
        } else {
            System.out.println("testPost is null!");
        }
    }

    @Transactional
    @Test
    public void updatePostAndCheckResult() throws Exception {
        System.out.println("Test updatePostAndCheckResult start");
        PostModel testPost = entityManager.find(PostModel.class, postId);
        testPost.setTitle(anotherTitle);
        testPost.setSubtitle(anotherSubtitle);
        testPost.setText(anotherText);
        entityManager.persist(testPost);
        entityManager.flush();
        PostModel changedPost = entityManager.find(PostModel.class, postId);
        assert (changedPost.getTitle().equals(anotherTitle));
        assert (changedPost.getSubtitle().equals(anotherSubtitle));
        assert (changedPost.getText().equals(anotherText));
        testPost.setTitle(title);
        testPost.setSubtitle(subtitle);
        testPost.setText(text);
        entityManager.persist(testPost);
        entityManager.flush();
    }

    @Transactional
    @Test
    public void insertPost() throws Exception {
        System.out.println("Test insertPost start");
        PostModel post = new PostModel(author, anotherTitle, anotherSubtitle, anotherText);
        entityManager.persist(post);
        PostModel createdPost = entityManager.find(PostModel.class, post.getId());
        assert (createdPost.getAuthor().equals(author));
        assert (createdPost.getTitle().equals(anotherTitle));
        assert (createdPost.getSubtitle().equals(anotherSubtitle));
        assert (createdPost.getText().equals(anotherText));
        assert (post.getId() != 0);
        entityManager.remove(post);
        entityManager.flush();
    }

    @Transactional
    @Test
    public void deletePost() throws Exception {
        System.out.println("Test deletePost start");
        PostModel postForRemove = new PostModel(author, anotherTitle, anotherSubtitle, anotherText);
        entityManager.persist(postForRemove);
        entityManager.flush();
        PostModel postToRemove = entityManager.find(PostModel.class, numberOfPosts+1);
        entityManager.remove(postToRemove);
        entityManager.flush();
        assert entityManager.find(PostModel.class, numberOfPosts + 1) == null;
    }

}