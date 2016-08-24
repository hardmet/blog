package ru.getinspiration.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.breathoffreedom.mvc.post.comment.CommentModel;
import ru.breathoffreedom.mvc.user.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class DAOImplTest {

    @PersistenceContext(name = "dataSource")
    private EntityManager entityManager;

    private String email = "admin@gmail.com";
    private String password = "qwerty";
    private String nick = "Admin";
    private String firstName = "Robert";
    private String lastName = "Polson";
    private String role = "ROLE_USER";
    private Date birthday = Calendar.getInstance().getTime();
    private String text = "Good article, I'm inspired!";
    private int postId = 1;

    @Test
    public void insertLogTest() {
        System.out.println(entityManager.find(User.class, 1).getNickName());
    }

    @Transactional
    @Test
    public void insertUser() {
        System.out.println("ORMService insertUser is called");
        String qlString = "insert into USER " +
                "(FIRST_NAME,LAST_NAME,NICK_NAME,BIRTHDAY,EMAIL,PASSWORD,ENABLED) values (?,?,?,?,?,?,TRUE)";
        Query query = entityManager.createNativeQuery(qlString);
        query.setParameter(1, firstName);
        query.setParameter(2, lastName);
        query.setParameter(3, nick);
        query.setParameter(4, birthday);
        query.setParameter(5, email);
        query.setParameter(6, password);
        int result = query.executeUpdate();
        assert (result == 1);
        qlString = "insert into AUTHORITIES (USERNAME, ACCESS_GROUP) values (?,?)";
        query = entityManager.createNativeQuery(qlString);
        query.setParameter(1, email);
        query.setParameter(2, role);
        result = query.executeUpdate();
        assert (result == 1);
    }

    @Test
    public void queryFindUserByEmail() {
        System.out.println("ORMService queryFindUserByEmail is called");
        User user = (User) entityManager.createQuery(
                "from User as user where user.email = ?")
                .setParameter(1, email)
                .getSingleResult();
        assert (user.getNickName().equals("Admin"));
    }

    @Test
    @Transactional
    public void updateUser() {
        System.out.println("ORMService updateUser is called");
        queryFindUserByEmail();
        String query = "update user set password = ?, first_name = ?, " +
                "last_name = ?, birthday = ?  where nick_name = ?";
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter(1, password);
        nativeQuery.setParameter(2, firstName);
        nativeQuery.setParameter(3, lastName);
        nativeQuery.setParameter(4, birthday);
        nativeQuery.setParameter(5, nick);
        int result = nativeQuery.executeUpdate();
        System.out.println(result);
    }

    @Test
    @Transactional
    public void insertComment() {
        System.out.println("ORMService insertComment is called");
        String qlString = "insert into Comment " +
                "(AUTHOR,TEXT,POST_ID) values (?,?,?)";
        Query query = entityManager.createNativeQuery(qlString);
        query.setParameter(1, nick);
        query.setParameter(2, text);
        query.setParameter(3, postId);
        int result = query.executeUpdate();
        assert (result > 0);
    }

    @Test
    public void queryFindCommentsByPostId() {
        System.out.println("ORMService queryFindCommentsByPostId is called");
        List<CommentModel> results = entityManager.createQuery(
                "from CommentModel as comment where comment.post = ?")
                .setParameter(1, postId)
                .getResultList();
        assert (!results.isEmpty());
        for (CommentModel comment : results) {
            System.out.println(comment.getAuthor());
            System.out.println(comment.getText());
        }
    }
}
