package ru.breathoffreedom.mvc.services.dao.postDAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.breathoffreedom.mvc.models.PostModel;

import java.util.List;

/**
 * This is Data Access Object class for PostModel entities.
 * This class implements Hibernate CRUD methods.
 */
@Repository
public class DAOPostImpl implements DAOPostInterface {

    /**
     * this context described on applicationContext.xml
     * this manager is Hibernate EntityManager
     */
    @PersistenceContext(name = "dataSource")
    private EntityManager entityManager;


    private DAOPostImpl() {
    }

    /**
     * getting the last id of post table
     *
     * @return - last id
     */
    public long getLastId() {
        System.out.println("DAOPostImpl getLastId is called");
        String query = "from PostModel order by id desc";
        TypedQuery<PostModel> typedQuery = entityManager.createQuery(query, PostModel.class);
        return typedQuery.getResultList().get(0).getId();
    }

    /**
     * Finding all posts in POST table
     *
     * @return - List of all PostModels from POST table at the DataBase
     */
    public List<PostModel> findAllPosts() {
        System.out.println("DAOPostImpl findAllPosts is called");
        String query = "from PostModel order by id desc";
        TypedQuery<PostModel> typedQuery = entityManager.createQuery(query, PostModel.class);
        return typedQuery.getResultList();
    }

    /**
     * finding post by id in the POST table
     *
     * @param id - id of the post
     * @return - PostModel of post with appropriate id
     */
    public PostModel findPostById(int id) {
        System.out.println("DAOPostImpl findPostById is called");
        return entityManager.find(PostModel.class, id);
    }

    /**
     * finding post by id and updating it
     *
     * @param postId   - id of post what you need
     * @param title    - new title of post
     * @param subtitle - new subtitle of post
     * @param text     - new text of post
     * @return - result of updating post
     */
    @Transactional
    public boolean updatePost(int postId, String title, String subtitle, String text) {
        System.out.println("DAOPostImpl updatePost is called");
        PostModel updatedPost = entityManager.find(PostModel.class, postId);
        updatedPost.setTitle(title);
        updatedPost.setSubtitle(subtitle);
        updatedPost.setText(text);
        entityManager.persist(updatedPost);
        entityManager.flush();
        PostModel changedPost = entityManager.find(PostModel.class, postId);
        return changedPost.getTitle().equals(title) && changedPost.getSubtitle().equals(subtitle)
                && changedPost.getText().equals(text);
    }

    /**
     * method insert a new post into POST table
     *
     * @param author   of post
     * @param title    of post
     * @param subtitle subtitle it's second title of post
     * @param text     - container of post, can contains html tags
     * @return - id of inserted post
     */
    @Transactional
    public int insertPost(String author, String title, String subtitle, String text) {
        System.out.println("DAOPostImpl insertPost is called");
        PostModel insertedPost = new PostModel(author, title, subtitle, text);
        entityManager.persist(insertedPost);
        entityManager.flush();
        return insertedPost.getId();
    }

    /**
     * deleting existing post from data base
     *
     * @param postId - id of post that needed to delete
     * @return - result check existing post at the Data Base
     */
    @Transactional
    public boolean deletePost(int postId) {
        System.out.println("DAOPostImpl deletePost is called");
        PostModel postToRemove = entityManager.find(PostModel.class, postId);
        entityManager.remove(postToRemove);
        entityManager.flush();
        return entityManager.find(PostModel.class, postId) == null;
    }
}
