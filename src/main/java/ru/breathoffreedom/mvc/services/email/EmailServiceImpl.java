package ru.breathoffreedom.mvc.services.email;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * this class is work with email messages, construct messages and send it
 */
@Service
public class EmailServiceImpl implements EmailServiceInterface {

    /*Email From*/
    public static final String FROM = "from";
    /*Email To*/
    public static final String TO = "to";
    /*Email Subject*/
    public static final String SUBJECT = "subject";
    /*Email BCC*/
    public static final String BCC_LIST = "bccList";
    /*Email CCC*/
    public static final String CCC_LIST = "ccList";

    private final JavaMailSender mailSender; //see applicationContext.xml

    private final VelocityEngine velocityEngine; //see applicationContext.xml

    /**
     * @param mailSender - needed for send email
     * @param velocityEngine - needed for using velocity templates
     */
    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, VelocityEngine velocityEngine) {
        this.mailSender = mailSender;
        this.velocityEngine = velocityEngine;
    }

    /**
     * this method sends email
     * @param templateName - name of velocity template to construct the message
     * @param model - model from contact form contains field from, text, author, etc.
     * @return - result of sending email
     */
    public boolean sendEmail (final String templateName, final Map<String, Object> model) {
        boolean res = false;
        try {
            MimeMessagePreparator preparator = mimeMessage -> {
                String from = (String) model.get(FROM);
                String to = (String) model.get(TO);
                String subject = (String) model.get(SUBJECT);

                List<String> bccList = (List<String>) model.get(BCC_LIST);
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8"); //ENCODING IMPORTANT!
                message.setFrom(from);
                message.setTo(to);
                message.setSubject(subject);
                message.setSentDate(new Date());
                if (bccList != null) {
                    for (String bcc : bccList) {
                        message.addBcc(bcc);
                    }
                }

                model.put("noArgs", new Object());
                String text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, templateName, "UTF-8", model);

                message.setText(text,true);
            };

            mailSender.send(preparator);
            res = true;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return res;
    }

}
