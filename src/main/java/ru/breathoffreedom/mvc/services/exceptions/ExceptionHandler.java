package ru.breathoffreedom.mvc.services.exceptions;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class using for handle all exceptions happened in Application
 */
@Component
public class ExceptionHandler implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse, Object o, Exception e) {
        System.out.println("Spring MVC ExceptionHandler handling");
        logger.error("ErrorLog: ", e);
        return new ModelAndView("/error/exception", "exceptionMsg", "ExceptionHandler msg: " + e.toString());
    }


    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)   //(1)
    public Object exceptionHandler(IOException e, HttpServletRequest request) {
        if (StringUtils.containsIgnoreCase(ExceptionUtils.getRootCauseMessage(e), "Broken pipe")) {   //(2)
            logger.error("ErrorLog: ", e);
            return null;        //(1)    socket is closed, cannot return any response
        } else {
            logger.error("ErrorLog: ", e);
            return new HttpEntity<>(e.getMessage());  //(3)
        }
    }
}
