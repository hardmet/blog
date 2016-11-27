package ru.breathoffreedom.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.breathoffreedom.mvc.models.email.Email;
import ru.breathoffreedom.mvc.services.email.EmailServiceImpl;
import ru.breathoffreedom.mvc.services.email.EmailServiceInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This is class for control operations with email
 */
@Controller
public class EmailController {

    private final EmailServiceInterface emailService;

    /**
     * Important! Use here interfaces, because Spring framework sensitive on it
     * @param emailService - needed for work with email services implemented in EmailServiceImpl
     */
    @Autowired
    public EmailController(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    /**
     * method is send message to author of blog from contact form
     * @param email - the model of email message
     * @return - result of sending message
     */
    @RequestMapping(value = "/contact/send", method = RequestMethod.POST)
    public ModelAndView email(@ModelAttribute("emailModel") Email email) {
        boolean result = false;
        if (email.getEmail() != null) {
            Map<String, Object> model = new HashMap<>();
            model.put("from", email.getEmail());
            model.put("subject", "Question from " + email.getName() + "!");
            model.put("to", "borkafedor@mail.ru");
            model.put("ccList", new ArrayList<>());
            model.put("bccList", new ArrayList<>());
            model.put("userName", "guest of GI");
            model.put("urlbreathoffreedom", "breathoffreedom.ru");
            model.put("message", email.getMessage());
            result = emailService.sendEmail("baseEmail.vm", model);
        }

        return new ModelAndView("redirect:/contact", "resultSending", result);
    }
}
