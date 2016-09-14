package ru.breathoffreedom.mvc.services.dao.userDAO;

import ru.breathoffreedom.mvc.models.UserModel;

import java.util.Date;
import java.util.List;

/**
 *
 */
public interface DAOUserInterface {

    UserModel findUserById(int id);

    UserModel findUserByEmail(String email);

    List<UserModel> findAllUsers();

    List findAllUserNames();

    List findAllNicks();

    boolean insertUser(String email, String password, String nick, String firstName,
                       String lastName, Date birthday, String role);

    boolean updateUser(String password, String nick, String firstName, String lastName, Date birthday);

    boolean setEnabledUser(String email);

    boolean deleteUserById(int userId);

}
