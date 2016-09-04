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
                "from UserModel as user where user.email = ?")
                .setParameter(1, email)
                .getSingleResult();
    }


    public List<UserModel> findAllUsers() {
        System.out.println("DAOUserImpl findAllUsers is called");
        String query = "from UserModel order by id desc";
        TypedQuery<UserModel> typedQuery = entityManager.createQuery(query, UserModel.class);
        return typedQuery.getResultList();
    }


    public List findAllUserNames() {
        System.out.println("DAOUserImpl findAllUserNames is called");
        return entityManager.createQuery("SELECT U.email FROM UserModel as U").getResultList();
    }

    public List findAllNicks() {
        System.out.println("DAOUserImpl findAllNicks is called");
        return entityManager.createQuery("SELECT U.nickName FROM UserModel as U").getResultList();
    }

    @Transactional
    public boolean insertUser(String email, String password, String nick, String firstName,
                              String lastName, Date birthday, String role) {
        System.out.println("DAOUserImpl insertUser is called");
        UserModel userToInsert = new UserModel(firstName, lastName,
                nick, birthday, email, password, true);
        entityManager.persist(userToInsert);
        String qlString = "insert into AUTHORITIES (USERNAME, ACCESS_GROUP) values (?,?)";
        Query query = entityManager.createNativeQuery(qlString);
        query.setParameter(1, email);
        query.setParameter(2, role);
        int result = query.executeUpdate();
        entityManager.flush();
        return result > 0;
    }

    @Transactional
    public boolean updateUser(String password, String nick,
                              String firstName, String lastName, Date birthday) {
        System.out.println("DAOUserImpl updateUser is called");
        UserModel userToUpdate = (UserModel) entityManager.createQuery(
                "from UserModel as user where user.nickName = ?")
                .setParameter(1, nick)
                .getSingleResult();
        userToUpdate.setFirstName(firstName);
        userToUpdate.setLastName(lastName);
        userToUpdate.setBirthday(birthday);
        userToUpdate.setPassword(password);
        entityManager.flush();
        return true;
    }

    @Transactional
    public boolean setEnabledUser(String email) {
        System.out.println("DAOUserImpl setEnabledUser is called");
        UserModel userToEnable = (UserModel) entityManager.createQuery(
                "from UserModel as user where user.email = ?")
                .setParameter(1, email)
                .getSingleResult();
        userToEnable.setEnabled(true);
        entityManager.flush();
        return true;
    }
}
