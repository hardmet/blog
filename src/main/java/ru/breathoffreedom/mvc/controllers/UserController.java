package ru.breathoffreedom.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.breathoffreedom.mvc.services.dao.userDAO.DAOUserInterface;
import ru.breathoffreedom.mvc.models.UserModel;

import java.util.*;

/**
 * This class is controller in Spring MVC, it controls operations with Users.
 */
@Controller
public class UserController {

    private final DAOUserInterface daoUserService;

    /**
     * Important! Use here interfaces, because Spring framework sensitive on it
     * @param daoUserService - needed for work with user table in database
     */
    @Autowired
    public UserController(DAOUserInterface daoUserService) {
        this.daoUserService = daoUserService;
    }

    @MessageMapping("/check/{sessionId}")
    @SendTo("/service/registration/result/{sessionId}")
    public HttpStatus addUser(UserModel userModel, @DestinationVariable("sessionId") String sessionId) {
        System.out.println("UserController resultOfRegistration is called");
        String email = userModel.getEmail();
        String nick = userModel.getNickName();
        if (daoUserService.findAllUserNames().contains(email)
                || daoUserService.findAllNicks().contains(nick)) {
            return HttpStatus.CONFLICT;
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String password = encoder.encode(userModel.getPassword());
            String firstName = userModel.getFirstName();
            String lastName = userModel.getLastName();
            Date birthday = userModel.getBirthday();
            String role = "ROLE_USER";
            boolean resultInsert = daoUserService.insertUser(email, password, nick,
                    firstName, lastName, birthday, role);
            if (resultInsert) {
                return HttpStatus.OK;
            } else {
                return HttpStatus.UNAUTHORIZED;
            }
        }
    }

    /**
     * This method for start editing user profile
     * @return edited user model for editing
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPER_USER') || hasRole('ROLE_USER')")
    @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
    public ModelAndView findUserByEmail() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("ORMController queryFindByIdEmail is called");
        UserModel userModel = daoUserService.findUserByEmail(userEmail);
        System.out.println(userModel.getBirthday());
        return new ModelAndView("/user/profile", "user", userModel);
    }

    /**
     * This method save changes in user profile
     * @param userModel - is changed or maybe not user information in profile
     * @return - result of saving changes at the user profile
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPER_USER') || hasRole('ROLE_USER')")
    @RequestMapping(value = "/user/save", method = RequestMethod.POST)
    public ModelAndView updateUser(@ModelAttribute("userModel") UserModel userModel) {
        System.out.println("UserController updateUser is called");
        String password = userModel.getPassword();
        String nick = userModel.getNickName();
        String firstName = userModel.getFirstName();
        String lastName = userModel.getLastName();
        Date birthday = userModel.getBirthday();
        boolean resultOfChangingProfile = daoUserService.updateUser(password, nick,
                firstName, lastName, birthday);
        return new ModelAndView("redirect:/userModel/profile",
                "resultOfChangingProfile", resultOfChangingProfile);
    }
}
