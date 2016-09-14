package ru.breathoffreedom.mvc.services.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
}
