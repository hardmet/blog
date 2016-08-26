package ru.breathoffreedom.mvc.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.breathoffreedom.mvc.dao.DAOImpl;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.*;

@Controller
public class UserController {

    private final DAOImpl daoService;
//    private Map<User, Integer> registerKeys = null;

    @Autowired
    public UserController(DAOImpl daoService) {
        this.daoService = daoService;
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("userModel") User user, ModelMap model) {
        System.out.println("UserController addUser is called");
        String email = user.getEmail();
        if (daoService.queryFindAllUserNames().contains(email)) {
            model.addAttribute("status", "400");
            model.addAttribute("message", "User with this name already exist!");
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String password = encoder.encode(user.getPassword());
            String nick = user.getNickName();
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            Date birthday = user.getBirthday();
            String role = "ROLE_USER";
            boolean resultInsert = daoService.insertUser(email, password, nick,
                    firstName, lastName, birthday, role);
            if (resultInsert) {
                model.addAttribute("status","200");
                model.addAttribute("message", "Register successful, now you can sign in!");
            } else {
                model.addAttribute("status", "400");
                model.addAttribute("message", "Wrong data, please try another!");
            }
        }
        return "redirect:/login";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPER_USER') || hasRole('ROLE_USER')")
    @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
    public ModelAndView findUserByEmail() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("ORMController queryFindByIdEmail is called");
        User user = daoService.queryFindUserByEmail(userEmail);
        System.out.println(user.getBirthday());
        return new ModelAndView("/user/profile", "user", user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPER_USER') || hasRole('ROLE_USER')")
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

//    @PreAuthorize("!hasRole('ROLE_ADMIN') && !hasRole('ROLE_SUPER_USER') && !hasRole('ROLE_USER')")
//    @RequestMapping(value = "/login/{key}")
//    public String confirmationRegister(@PathVariable("key") int key, ModelMap model) {
//        for (User user:registerKeys.keySet()) {
//            if (key == registerKeys.get(user)) {
//                daoService.querySetEnabledUser(user.getEmail());
//                registerKeys.remove(user);
//                Authentication request = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
//                SecurityContextHolder.getContext().setAuthentication(request);
//                return "/index";
//            }
//        }
//        model.addAttribute("status", "400");
//        model.addAttribute("message", "Error register! Please, try again!");
//        return "/login";
//    }
}
