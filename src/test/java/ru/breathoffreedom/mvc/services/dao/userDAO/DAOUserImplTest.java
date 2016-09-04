package ru.breathoffreedom.mvc.services.dao.userDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.breathoffreedom.mvc.models.UserModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * This is class for test DAOUserImpl
 * There are some tests to check CRUD methods
 * Transactional annotation is for correct test pass and because 'After'
 * annotated methods contains insert/update query for return DB to start state.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class DAOUserImplTest {

    @PersistenceContext(name = "dataSource")
    private EntityManager entityManager;

    private int userId;
    private String firstName;
    private String lastName;
    private String nickName;
    private Date birthday;
    private String email;
    private String password;
    private boolean enabled;

    private int numberOfUsers;

    private String anotherFirstName;
    private String anotherLastName;
    private String anotherNickName;
    private Date anotherBirthday;
    private String anotherEmail;
    private String anotherPassword;
    private boolean anotherEnabled;

    @Before
    public void setUp() throws Exception {
        userId = 1;
        firstName = "admin";
        lastName = "super";
        nickName = "Admin";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.UK);
        birthday = formatter.parse("3-Sep-2016 21:35:07");
        email = "admin@gmail.com";
        password = "12345";
        enabled = true;

        anotherNickName = "The Murder";
        anotherFirstName = "Angry";
        anotherLastName = "Woman";
        anotherBirthday = formatter.parse("25-Oct-1996 02:23:52");
        anotherEmail = "dirty@rts.com";
        anotherPassword = "qwerty";
        anotherEnabled = false;

        numberOfUsers = getNumberOfUsers();
    }

    @After
    public void tearDown() throws Exception {
        userId = 0;
        firstName = null;
        lastName = null;
        nickName = null;
        birthday = null;
        email = null;
        password = null;
        enabled = false;

        anotherNickName = null;
        anotherFirstName = null;
        anotherLastName = null;
        anotherBirthday = null;
        anotherEmail = null;
        anotherPassword = null;
        anotherEnabled = false;

        String query = "ALTER TABLE user AUTO_INCREMENT = ?";
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter(1, numberOfUsers);
        nativeQuery.executeUpdate();

        numberOfUsers = 0;
    }

    @Transactional
    @Test
    public void findUserById() throws Exception {
        System.out.println("Test findUserById start");
        UserModel user = entityManager.find(UserModel.class, userId);
        checkUser(user);
    }

    @Transactional
    @Test
    public void findUserByEmail() throws Exception {
        System.out.println("Test findUserByEmail start");
        UserModel user = (UserModel) entityManager.createQuery(
                "from UserModel as user where user.email = ?")
                .setParameter(1, email)
                .getSingleResult();
        checkUser(user);
    }

    @Transactional
    @Test
    public void findAllUsers() throws Exception {
        System.out.println("Test findAllUsers start");
        String query = "from UserModel order by id desc";
        TypedQuery<UserModel> typedQuery = entityManager.createQuery(query, UserModel.class);
        List allUsers = typedQuery.getResultList();
        assert allUsers != null;
        assert allUsers.size() != 0;
        for (Object user : allUsers) {
            System.out.println(((UserModel) user).getNickName() + "\t" +
                    ((UserModel) user).getFirstName() + "\t" +
                    ((UserModel) user).getLastName() + "\t" +
                    ((UserModel) user).getBirthday() + "\t" +
                    ((UserModel) user).getEmail() + "\t" +
                    ((UserModel) user).getPassword());
        }
    }

    @Transactional
    @Test
    public void findAllUserNames() throws Exception {
        System.out.println("Test findAllUserNames start");
        List allUsers = entityManager.createQuery("SELECT U.email FROM UserModel as U").getResultList();
        assert allUsers != null;
        assert allUsers.size() != 0;
        for (int i = 0; i < allUsers.size(); i++) {
            if (i % 6 == 0) {
                System.out.println();
            }
            System.out.print(allUsers.get(i) + "\t");
        }
    }

    @Transactional
    @Test
    public void findAllNicks() {
        List allNicks = entityManager.createQuery("SELECT U.nickName FROM UserModel as U").getResultList();
        assert allNicks != null;
        assert allNicks.size() != 0;
        for (int i = 0; i < allNicks.size(); i++) {
            if (i % 6 == 0) {
                System.out.println();
            }
            System.out.print(allNicks.get(i) + "\t");
        }
    }

    @Transactional
    @Test
    public void insertUser() throws Exception {
        System.out.println("Test insertUser start");
        UserModel userToInsert = new UserModel(anotherFirstName, anotherLastName,
                anotherNickName, anotherBirthday, anotherEmail, anotherPassword, anotherEnabled);
        entityManager.persist(userToInsert);
        String qlString = "insert into AUTHORITIES (USERNAME, ACCESS_GROUP) values (?,?)";
        Query query = entityManager.createNativeQuery(qlString);
        query.setParameter(1, anotherEmail);
        query.setParameter(2, "ROLE_USER");
        int result = query.executeUpdate();
        assert result > 0;
        assert userToInsert.getId() != 0;
        assert numberOfUsers + 1 == getNumberOfUsers();
        qlString = "delete from AUTHORITIES where USERNAME = ?";
        query = entityManager.createNativeQuery(qlString);
        query.setParameter(1, anotherEmail);
        query.executeUpdate();
        entityManager.remove(userToInsert);
        entityManager.flush();
    }

    @Transactional
    @Test
    public void updateUser() throws Exception {
        System.out.println("Test updateUser start");
        UserModel userToUpdate = (UserModel) entityManager.createQuery(
                "from UserModel as user where user.nickName = ?")
                .setParameter(1, nickName)
                .getSingleResult();
        int id = userToUpdate.getId();
        userToUpdate.setFirstName(anotherFirstName);
        userToUpdate.setLastName(anotherLastName);
        userToUpdate.setBirthday(anotherBirthday);
        userToUpdate.setPassword(anotherPassword);
        entityManager.flush();
        assert numberOfUsers == getNumberOfUsers();
        userToUpdate = entityManager.find(UserModel.class, id);
        assert(userToUpdate.getFirstName().equals(anotherFirstName));
        assert(userToUpdate.getLastName().equals(anotherLastName));
        assert(userToUpdate.getBirthday().equals(anotherBirthday));
        assert(userToUpdate.getPassword().equals(anotherPassword));
        userToUpdate.setFirstName(firstName);
        userToUpdate.setLastName(lastName);
        userToUpdate.setBirthday(birthday);
        userToUpdate.setPassword(password);
        entityManager.persist(userToUpdate);
        entityManager.flush();
    }

    @Transactional
    @Test
    public void setEnabledUser() throws Exception {
        System.out.println("Test setEnabledUser start");
        UserModel userToEnable = (UserModel) entityManager.createQuery(
                "from UserModel as user where user.email = ?")
                .setParameter(1, email)
                .getSingleResult();
        int id = userToEnable.getId();
        userToEnable.setEnabled(anotherEnabled);
        entityManager.persist(userToEnable);
        userToEnable = entityManager.find(UserModel.class, id);
        assert userToEnable.isEnabled() == anotherEnabled;
        assert numberOfUsers == getNumberOfUsers();
        userToEnable.setEnabled(enabled);
        entityManager.persist(userToEnable);
        entityManager.flush();
    }

    public void checkUser(UserModel user) {
        assert user != null;
        assert user.getNickName().equals(nickName);
        assert user.getFirstName().equals(firstName);
        assert user.getLastName().equals(lastName);
        assert user.getEmail().equals(email);
        assert user.getPassword().equals(password);
        assert birthday.equals(user.getBirthday());
        assert user.isEnabled() == enabled;
    }

    public int getNumberOfUsers() {
        String query = "from UserModel";
        return entityManager.createQuery(query, UserModel.class).getResultList().size();
    }
}