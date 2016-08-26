package ru.breathoffreedom.mvc.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.breathoffreedom.mvc.user.User;
import ru.breathoffreedom.mvc.post.PostModel;

import java.util.Date;
import java.util.List;

@Repository
@Transactional //need to update\delete queries. Don't forget <tx:annotation-driven/>
public class DAOImpl implements DAOInterface {

    @PersistenceContext(name = "dataSource")
    private EntityManager entityManager;

    @Transactional
    public List<PostModel> queryFindAllPostsJPA() {
        System.out.println("ORMService queryfindAllPosts is called");
        String query = "from PostModel order by id desc";
        TypedQuery<PostModel> typedQuery = entityManager.createQuery(query, PostModel.class);
        return typedQuery.getResultList();
    }

    public PostModel queryFindPostById(int id) {
        System.out.println("ORMService queryFindPostById is called");
        return entityManager.find(PostModel.class, id);
    }

    //    public boolean updatePost(int id, boolean enabled) {
//        System.out.println("ORMService updateUser is called");
//
//        String query= "update user set enabled = ? where iduser = ?";
//        Query nativeQuery = entityManager.createNativeQuery(query);
//        nativeQuery.setParameter(1, enabled);
//        nativeQuery.setParameter(2, id);
//        int result = nativeQuery.executeUpdate();
//        return result > 0; // result show how many rows was updated.
//    }
//
    public boolean insertPost(String title, String subtitle, String text) {
        System.out.println("ORMService insertUser is called");
        String qlString = "insert into POST (AUTHOR_ID,TITLE,SUBTITLE,TEXT) values (?,?,?,?)";
        Query query = entityManager.createNativeQuery(qlString);
        query.setParameter(1, "1");
        query.setParameter(2, title);
        query.setParameter(3, subtitle);
        query.setParameter(4, text);
        int result = query.executeUpdate();
        return result > 0;
    }

    public boolean deletePost(int idPost) {
        System.out.println("ORMExample deleteUser is called");
        String qlString = "delete from POST where iduser=?";
        Query query = entityManager.createNativeQuery(qlString);
        query.setParameter(1, idPost);
        int result = query.executeUpdate();
        return result > 0;
    }

    public User queryFindUserById(int id) {
        System.out.println("ORMService queryFindUserById is called");
        return entityManager.find(User.class, id);
    }

    public User queryFindUserByEmail(String email) {
        System.out.println("ORMService queryFindUserByEmail is called");
        return (User) entityManager.createQuery(
                "from User as user where user.email = ?")
                .setParameter(1, email)
                .getSingleResult();
    }

    public List<User> queryFindAllUsersJPA() {
        System.out.println("ORMService queryfindAllUsers is called");
        String query = "from User order by id desc";
        TypedQuery<User> typedQuery = entityManager.createQuery(query, User.class);
        return typedQuery.getResultList();
    }

    public List queryFindAllUserNames() {
        System.out.println("ORMService queryfindAllUsers is called");
        return entityManager.createQuery("SELECT U.email FROM User as U").getResultList();
    }

    @Transactional
    public boolean insertUser(String email, String password, String nick, String firstName,
                              String lastName, Date birthday, String role) {
        System.out.println("ORMService insertUser is called");
        String qlString = "insert into USER " +
                "(FIRST_NAME,LAST_NAME,NICK_NAME,BIRTHDAY,EMAIL,PASSWORD,ENABLED) values (?,?,?,?,?,?,?)";
        Query query = entityManager.createNativeQuery(qlString);
        query.setParameter(1, firstName);
        query.setParameter(2, lastName);
        query.setParameter(3, nick);
        query.setParameter(4, birthday);
        query.setParameter(5, email);
        query.setParameter(6, password);
        query.setParameter(7, "1");
        int result = query.executeUpdate();
        if (result < 0) {
            return result > 0;
        } else {
            qlString = "insert into AUTHORITIES (USERNAME, ACCESS_GROUP) values (?,?)";
            query = entityManager.createNativeQuery(qlString);
            query.setParameter(1, email);
            query.setParameter(2, role);
            result = query.executeUpdate();
            return result > 0;
        }
    }

    @Transactional
    public boolean updateUser(String password, String nick,
                              String firstName, String lastName, Date birthday) {
        System.out.println("ORMService updateUser is called");
        String query = "update user set password = ?, first_name = ?, " +
                "last_name = ?, birthday = ?  where nick_name = ?";
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter(1, password);
        nativeQuery.setParameter(2, firstName);
        nativeQuery.setParameter(3, lastName);
        nativeQuery.setParameter(4, birthday);
        nativeQuery.setParameter(5, nick);
        int result = nativeQuery.executeUpdate();
        return result > 0; // result show how many rows was updated.
    }

    @Transactional
    public boolean insertComment(String author, String text, int postId) {
        System.out.println("ORMService insertComment is called");
        String qlString = "insert into Comment " +
                "(AUTHOR,TEXT,POST_ID) values (?,?,?)";
        Query query = entityManager.createNativeQuery(qlString);
        query.setParameter(1, author);
        query.setParameter(2, text);
        query.setParameter(3, postId);
        return query.executeUpdate() > 0;
    }

    public List queryFindCommentsByPostId(int id) {
        System.out.println("ORMService queryFindCommentsByPostId is called");
        return entityManager.createQuery(
                "from CommentModel as comment where comment.post = ?")
                .setParameter(1, id)
                .getResultList();
    }

    public boolean querySetEnabledUser(String email) {
        String query = "update user set enabled = ? where email = ?";
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter(1, "TRUE");
        nativeQuery.setParameter(2, email);
        int result = nativeQuery.executeUpdate();
        return result > 0;
    }
}
