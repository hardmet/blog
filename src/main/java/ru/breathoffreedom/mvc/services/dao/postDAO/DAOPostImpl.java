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

    public List<PostModel> findAllPosts() {
        System.out.println("ORMService queryfindAllPosts is called");
        String query = "from PostModel order by id desc";
        TypedQuery<PostModel> typedQuery = entityManager.createQuery(query, PostModel.class);
        return typedQuery.getResultList();
    }

    public PostModel findPostById(int id) {
        System.out.println("ORMService queryFindPostById is called");
        return entityManager.find(PostModel.class, id);
    }

    @Transactional
    public boolean updatePost(int postId, String title, String subtitle, String text) {
        System.out.println("ORMService updateUser is called");
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

    @Transactional
    public boolean insertPost(String author, String title, String subtitle, String text) {
        System.out.println("ORMService insertUser is called");
        PostModel insertedPost = new PostModel(author, title, subtitle, text);
        entityManager.persist(insertedPost);
        entityManager.flush();
        return insertedPost.getId() != 0;
    }

    @Transactional
    public boolean deletePost(int postId) {
        System.out.println("ORMExample deleteUser is called");
        PostModel postToRemove = entityManager.find(PostModel.class, postId);
        entityManager.remove(postToRemove);
        entityManager.flush();
        return entityManager.find(PostModel.class, postId) == null;
    }
}
