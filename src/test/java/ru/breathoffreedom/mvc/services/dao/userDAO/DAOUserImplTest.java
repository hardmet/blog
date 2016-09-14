package ru.breathoffreedom.mvc.services.dao.userDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.breathoffreedom.mvc.models.UserModel;

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
@ContextConfiguration(locations = {"classpath:dispatcher-servlet.xml", "classpath:applicationContext.xml"})
public class DAOUserImplTest {

    @Autowired
    private DAOUserInterface daoUserService;

    private int userId;
    private String email;
    private String anotherFirstName;
    private String anotherLastName;
    private String anotherNickName;
    private Date anotherBirthday;
    private String anotherEmail;
    private String anotherPassword;
    private String anotherRole;

    @Before
    public void setUp() throws Exception {
        userId = 1;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.UK);
        email = "admin@gmail.com";

        anotherNickName = "The Murder";
        anotherFirstName = "Angry";
        anotherLastName = "Woman";
        anotherBirthday = formatter.parse("25-Oct-1996 02:23:52");
        anotherEmail = "dirty@rts.com";
        anotherPassword = "qwerty";
        anotherRole = "ROLE_USER";
    }

    @After
    public void tearDown() throws Exception {
        userId = 0;
        email = null;
        anotherNickName = null;
        anotherFirstName = null;
        anotherLastName = null;
        anotherBirthday = null;
        anotherEmail = null;
        anotherPassword = null;
        anotherRole = null;
    }

    @Test
    public void findUserByIdTest() throws Exception {
        System.out.println("Test findUserByIdTest start");
        UserModel user = daoUserService.findUserById(userId);
        checkUser(user);
    }

    @Test
    public void findUserByEmailTest() throws Exception {
        System.out.println("Test findUserByEmailTest start");
        UserModel user = daoUserService.findUserByEmail(email);
        checkUser(user);
    }

    @Test
    public void findAllUsersTest() throws Exception {
        System.out.println("Test findAllUsersTest start");
        List allUsers = daoUserService.findAllUsers();
        assert allUsers != null;
        assert allUsers.size() != 0;
        for (Object user : allUsers) {
            checkUser((UserModel) user);
            System.out.println(user.toString());
        }
    }

    @Test
    public void findAllUserNamesTest() throws Exception {
        System.out.println("Test findAllUserNamesTest start");
        List allUsers = daoUserService.findAllUserNames();
        assert allUsers != null;
        assert allUsers.size() != 0;
        checkAllResultList(allUsers);
    }

    @Test
    public void findAllNicksTest() {
        List allNicks = daoUserService.findAllNicks();
        assert allNicks != null;
        assert allNicks.size() != 0;
        checkAllResultList(allNicks);
    }

    @Transactional
    @Test
    public void insertAndDeleteUserTest() throws Exception {
        System.out.println("Test insertUserTest start");
        assert  daoUserService.insertUser(anotherEmail, anotherPassword, anotherNickName,
                anotherFirstName, anotherLastName, anotherBirthday, anotherRole);
        UserModel insertedUser = daoUserService.findUserByEmail(anotherEmail);
        assert insertedUser != null;
        assert daoUserService.deleteUserById(insertedUser.getId());
    }

    @Transactional
    @Test
    public void updateUserTest() throws Exception {
        System.out.println("Test updateUserTest start");
        UserModel userToUpdate = daoUserService.findUserById(userId);
        String startPass = userToUpdate.getPassword();
        String startFirstName = userToUpdate.getFirstName();
        String startLastName = userToUpdate.getLastName();
        Date startBirthday = userToUpdate.getBirthday();
        assert daoUserService.updateUser(anotherPassword, userToUpdate.getNickName(),
                anotherFirstName, anotherLastName, anotherBirthday);
        UserModel updatedUser = daoUserService.findUserById(userId);
        assert !updatedUser.getPassword().equals(startPass);
        assert !updatedUser.getFirstName().equals(startFirstName);
        assert !updatedUser.getLastName().equals(startLastName);
        assert !updatedUser.getBirthday().equals(startBirthday);
        assert daoUserService.updateUser(startPass, updatedUser.getNickName(),
                startFirstName, startLastName, startBirthday);
    }

    @Transactional
    @Test
    public void setEnabledUserTest() throws Exception {
        System.out.println("Test setEnabledUserTest start");
        assert  daoUserService.setEnabledUser(email);
        UserModel user = daoUserService.findUserByEmail(email);
        assert user.isEnabled();
    }

    public void checkUser(UserModel user) {
        assert user != null;
        assert user.getId() != 0;
        assert user.getEmail() != null;
        assert user.getNickName() != null;
        assert user.getPassword() != null;
        assert user.getBirthday() != null;
    }

    public void checkAllResultList(List elements) {
        for (int i = 0; i < elements.size(); i++) {
            assert elements.get(i) != null;
            if (i % 6 == 0) {
                System.out.println();
            }
            System.out.print(elements.get(i) + "\t");
        }
    }
}