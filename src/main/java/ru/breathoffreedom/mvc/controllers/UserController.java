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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.breathoffreedom.mvc.models.MessageResponse;
import ru.breathoffreedom.mvc.models.user.Author;
import ru.breathoffreedom.mvc.services.dao.userDAO.DAOUserInterface;

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
    @SendTo("/email/registration/result/{sessionId}")
    public HttpStatus addUser(Author author, @DestinationVariable("sessionId") String sessionId) {
        String email = author.getEmail();
        String nick = author.getNickName();
        if (daoUserService.findAllUserNames().contains(email)
                || daoUserService.findAllNicks().contains(nick)) {
            return HttpStatus.CONFLICT;
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String password = encoder.encode(author.getPassword());
            String firstName = author.getFirstName();
            String lastName = author.getLastName();
            Date birthday = author.getBirthday();
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
     * This method for start editing user author
     * @return edited user model for editing
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPER_USER') || hasRole('ROLE_USER')")
    @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
    public ModelAndView startEditUser() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Author author = daoUserService.findUserByEmail(userEmail);
        return new ModelAndView("/web/author", "user", author);
    }

    /**
     * This method save changes in user author
     * @param author - is changed or maybe not user information in author
     * @return - result of saving changes at the user author
     */
    @RequestMapping(value = "/user/save", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody MessageResponse updateUser(@RequestBody Author author) {
        String password = author.getPassword();
        String nick = author.getNickName();
        String firstName = author.getFirstName();
        String lastName = author.getLastName();
        Date birthday = author.getBirthday();
        boolean resultOfChangingProfile = daoUserService.updateUser(password, nick,
                firstName, lastName, birthday);
        if (resultOfChangingProfile) {
            return new MessageResponse("User successfully updated!");
        }
        return new MessageResponse("Something was going wrong! Updating rejected!");
    }


}
