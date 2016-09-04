package ru.breathoffreedom.mvc.services.email;

import java.util.Map;

/**
 *
 */
public interface EmailServiceInterface {

    boolean sendEmail(final String templateName, final Map<String, Object> model);

}
