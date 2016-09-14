package ru.breathoffreedom.mvc.services.dao.userDAO;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.breathoffreedom.mvc.models.UserModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;


/**
 * This is Data Access Object class for UserModel entities.
 * This class implements Hibernate CRUD methods.
 */
@Repository
public class DAOUserImpl implements DAOUserInterface {

    /**
     * this context described on applicationContext.xml
     * this manager is Hibernate EntityManager
     */
    @PersistenceContext(name = "dataSource")
    private EntityManager entityManager;

    private DAOUserImpl() {
    }

    public UserModel findUserById(int id) {
        System.out.println("DAOUserImpl findUserById is called");
        return entityManager.find(UserModel.class, id);
    }

    public UserModel findUserByEmail(String email) {
        System.out.println("DAOUserImpl findUserByEmail is called");
        return (UserModel) entityManager.createQuery(
                "from UserModel as user where user.email = :email")
                .setParameter("email", email)
                .getSingleResult();
    }

    /**
     * finding all users in DB USER table
     * @return - List of all users saving at the Data Base
     */
    public List<UserModel> findAllUsers() {
        System.out.println("DAOUserImpl findAllUsers is called");
        String query = "from UserModel order by id desc";
        TypedQuery<UserModel> typedQuery = entityManager.createQuery(query, UserModel.class);
        return typedQuery.getResultList();
    }

    /**
     * Finding all user emails
     * @return - List of all emails of users
     */
    public List findAllUserNames() {
        System.out.println("DAOUserImpl findAllUserNames is called");
        return entityManager.createQuery("SELECT U.email FROM UserModel as U").getResultList();
    }

    /**
     * Finding all nick names
     * @return - List of all nick names of users
     */
    public List findAllNicks() {
        System.out.println("DAOUserImpl findAllNicks is called");
        return entityManager.createQuery("SELECT U.nickName FROM UserModel as U").getResultList();
    }

    /**
     * inserting new user to the USER table at the Data Base
     * @param email - email of user
     * @param password - bcrypt encoded password of user
     * @param nick - nick name of user
     * @param firstName - first name of user
     * @param lastName - last name of user
     * @param birthday - birthday of user
     * @param role - any role of (ROLE_ADMIN or ROLE_USER or ROLE_SUPER_USER), by default is ROLE_USER
     * @return - result of inserting user into Data Base
     */
    @Transactional
    public boolean insertUser(String email, String password, String nick, String firstName,
                              String lastName, Date birthday, String role) {
        System.out.println("DAOUserImpl insertUser is called");
        UserModel userToInsert = new UserModel(firstName, lastName,
                nick, birthday, email, password, true);
        entityManager.persist(userToInsert);
        entityManager.flush();
        String qlString = "insert into AUTHORITIES (USERNAME, ACCESS_GROUP) values (:email,:role)";
        Query query = entityManager.createNativeQuery(qlString);
        query.setParameter("email", email);
        query.setParameter("role", role);
        int result = query.executeUpdate();
        entityManager.flush();
        return result > 0;
    }

    /**
     * updating user fields:
     * @param password - changed bcrypt encoded password of user
     * @param nick - changed nick name of user
     * @param firstName - changed first name of user
     * @param lastName - changed last name of user
     * @param birthday - changed birthday of user
     * @return - result of updating user
     */
    @Transactional
    public boolean updateUser(String password, String nick,
                              String firstName, String lastName, Date birthday) {
        System.out.println("DAOUserImpl updateUser is called");
        UserModel userToUpdate = (UserModel) entityManager.createQuery(
                "from UserModel as user where user.nickName = :nick")
                .setParameter("nick", nick)
                .getSingleResult();
        userToUpdate.setFirstName(firstName);
        userToUpdate.setLastName(lastName);
        userToUpdate.setBirthday(birthday);
        userToUpdate.setPassword(password);
        entityManager.flush();
        return true;
    }

    /**
     * enabling of user after registration
     * @param email - email of user that needed to enable
     * @return - result of updating enable status
     */
    @Transactional
    public boolean setEnabledUser(String email) {
        System.out.println("DAOUserImpl setEnabledUser is called");
        UserModel userToEnable = (UserModel) entityManager.createQuery(
                "from UserModel as user where user.email = :email")
                .setParameter("email", email)
                .getSingleResult();
        userToEnable.setEnabled(true);
        entityManager.flush();
        return true;
    }

    /**
     * !IMPORTANT!!!!
     * Unsafe deleting, the check of related entities at the post and comment table doesn't execute
     * @param userId - id of user what you going to delete
     * @return - result of deleting
     */
    @Transactional
    @Override
    public boolean deleteUserById(int userId) {
        UserModel userToDelete = entityManager.find(UserModel.class, userId);
        String qlString = "delete from AUTHORITIES where USERNAME = :email";
        Query query = entityManager.createNativeQuery(qlString);
        query.setParameter("email", userToDelete.getEmail());
        if (query.executeUpdate() < 0) {
            return false;
        }
        entityManager.remove(userToDelete);
        entityManager.flush();
        return entityManager.find(UserModel.class, userId) == null;
    }
}
