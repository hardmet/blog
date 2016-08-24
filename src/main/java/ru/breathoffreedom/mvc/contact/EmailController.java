package ru.breathoffreedom.mvc.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping(value = "/contact/send", method = RequestMethod.POST)
    public ModelAndView email(@ModelAttribute("emailModel") EmailModel emailModel) {
        System.out.println("EmailController email is called");
        boolean result = false;
        if (emailModel.getEmail() != null) {
            Map<String, Object> model = new HashMap<>();
            model.put("from", emailModel.getEmail());
            model.put("subject", "Question from " + emailModel.getName() + "!");
            model.put("to", "borkafedor@mail.ru");
            model.put("ccList", new ArrayList<>());
            model.put("bccList", new ArrayList<>());
            model.put("userName", "guest of GI");
            model.put("urlbreathoffreedom", "breathoffreedom.ru");
            model.put("message", emailModel.getMessage());
            result = emailService.sendEmail("registered.vm", model);
        }

        return new ModelAndView("redirect:/contact", "resultSending", result);
    }
}
