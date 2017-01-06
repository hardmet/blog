package ru.breathoffreedom.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import ru.breathoffreedom.mvc.services.blog.BlogService;

/**
 * This class is controller in Spring MVC, it controls operations with Users.
 */
@Controller
public class AuthorController {

    private BlogService blogService;

    /**
     * Important! Use here interfaces, because Spring framework sensitive on it
     * @param blogService - needed for work with user table in database
     */
    @Autowired
    public AuthorController(BlogService blogService) {
        this.blogService = blogService;
    }

    @MessageMapping("/check/{sessionId}")
    @SendTo("/service/registration/result/{sessionId}")
    public MessageResponse addUser(Author author) {
        if (blogService.hasAuthorWithEmail(author.getEmail())
                || blogService.hasAuthorWithNickName(author.getNickName())) {
            return new MessageResponse(HttpStatus.CONFLICT,
                    "Пользователь с таким e-mail или ником уже существует!");
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            author.setPassword(encoder.encode(author.getPassword()));
            author = blogService.save(author);
            if (author != null && author.getId() != 0) {
                return new MessageResponse(HttpStatus.OK, "Пользователь успешно зарегистрирован");
            } else {
                return new MessageResponse(HttpStatus.UNAUTHORIZED, "Произошла ошибка! Пользователь не зарегистрирован!");
            }
        }
    }

    /**
     * This method for start editing user author
     * @return edited user model for editing
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPER_USER') || hasRole('ROLE_USER')")
    @RequestMapping(value = "/author/edit", method = RequestMethod.GET)
    public ModelAndView startEditUser() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Author author = blogService.getAuthor(userEmail);
        return new ModelAndView("/web/author/author", "author", author);
    }

    /**
     * This method save changes in user author
     * @param author - is changed or maybe not user information in author
     * @return - result of saving changes at the user author
     */
    @RequestMapping(value = "/author/save", method = RequestMethod.POST)
    public @ResponseBody MessageResponse updateUser(@RequestBody Author author) {
        Author oldAuthor = blogService.getAuthor(author.getId());
        if (author.getPassword().equals("")) {
            author.setPassword(oldAuthor.getPassword());
        } else {
            String password = author.getPassword();
            if (password.length() > 5) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                author.setPassword(encoder.encode(password));
            } else {
                return new MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Пароль слишком короткий! " +
                        "Пароль должен состоять как минимум из 6 символов");
            }
        }
        try {
            author = blogService.save(author);
            if (author != null && author.getId() != 0) {
                return new MessageResponse(HttpStatus.OK, "Ваш профиль успешно сохранен!");
            }
        } catch (Exception e) {
            return new MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Введены некорректные данные!" +
                    "Проверьте пожалуйста соответствие ограничениям: Профиль с таким ником уже существует, " +
                    "попробуйте ввести другой.");
        }
        return new MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Что-то пошло не так, изменения не сохранены. Пожалуйста, попробуйте позже.");
    }

    @RequestMapping(value = "/author/ajax/check/nick", method = RequestMethod.POST)
    public @ResponseBody boolean checkNickName(@RequestParam("nickName") String nickName) {
        return blogService.hasAuthorWithNickName(nickName);
    }
}
