package ru.breathoffreedom.mvc.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.breathoffreedom.mvc.dao.DAOImpl;

import java.util.Date;

@Controller
public class UserController {

    private final DAOImpl daoService;

    @Autowired
    public UserController(DAOImpl daoService) {
        this.daoService = daoService;
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute("userModel") User user) {
        System.out.println("UserController addUser is called");
        String email = user.getEmail();
        String password = user.getPassword();
        String nick = user.getNickName();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        Date birthday = user.getBirthday();
        String role = "ROLE_USER";
        boolean result = daoService.insertUser(email, password, nick, firstName, lastName, birthday, role);
        return new ModelAndView("redirect:/login", "resultReg", result);
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
    public ModelAndView findUserByEmail() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("ORMController queryFindByIdEmail is called");
        User user = daoService.queryFindUserByEmail(userEmail);
        System.out.println(user.getBirthday());
        return new ModelAndView("/user/profile", "user", user);
    }

    @RequestMapping(value = "/user/save", method = RequestMethod.POST)
    public ModelAndView updateUser(@ModelAttribute("userModel") User user) {
        System.out.println("UserController updateUser is called");
        String password = user.getPassword();
        String nick = user.getNickName();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        Date birthday = user.getBirthday();
        boolean result = daoService.updateUser(password, nick, firstName, lastName, birthday);
        return new ModelAndView("redirect:/user/profile", "resultReg", result);
    }
}
