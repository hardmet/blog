<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<page:template>
    <jsp:attribute name="title">Contact to Get Inspiration</jsp:attribute>

    <jsp:body>
        <!-- Page Header -->
        <!-- Set your background image for this header on the line below. -->
        <header class="intro-header">
            <img class="header-image" src="<c:url value="/resources/img/contact-header.jpg"/>" height="300px">
        </header>
        <div class="titles">
            <h1>Contact Me</h1>
            <h2 class="subheading">Have questions? I have answers (maybe).</h2>
        </div>

        <!-- Post Content -->
        <div class="container">
            <div class="row">
                <div class="main-content">
                    <!-- Content Row -->
                    <div class="row">
                        <!-- Contact Details Column -->
                        <div class="col-md-4">
                            <c:if test="${pageContext.request.getParameter('resultSending') == 'true'}">
                                <h3 style="color:green">Email already send!</h3>
                            </c:if>
                            <c:if test="${pageContext.request.getParameter('resultSending') == 'false'}">
                                <h3 style="color:red">Email wasn't send!</h3>
                            </c:if>
                            <h3>Contact Details</h3>
                            <p>
                                Kupchino district<br>St. Petersburg, Russia<br>
                            </p>
                            <p><i class="fa fa-phone"></i>
                                <abbr title="Phone">P</abbr>: +7-981-751-05-08</p>
                            <p><i class="fa fa-envelope-o"></i>
                                <abbr title="Email">E</abbr>: <a
                                        href="mailto:borkafedor@mail.ru">borkafedor@mail.ru</a>
                            </p>
                            <p><i class="fa fa-clock-o"></i>
                                <abbr title="Hours">H</abbr>: 24/7</p>
                            <ul class="list-unstyled list-inline list-social-icons">
                                <li>
                                    <a href="https://www.facebook.com/profile.php?id=100012197762749"><i
                                            class="fa fa-facebook-square fa-2x"></i></a>
                                </li>
                                <li>
                                    <a href="https://www.linkedin.com/in/borisazanov"><i
                                            class="fa fa-linkedin-square fa-2x"></i></a>
                                </li>
                                <li>
                                    <a href="https://github.com/hardmet"><i class="fa fa-github-square fa-2x"></i></a>
                                </li>
                            </ul>
                        </div>
                        <div>Want to get in touch with me? Fill out the form below to send me a message and I will try
                            to
                            get back to you within 24 hours!
                        </div>
                    </div>
                    <!-- /.row -->
                    <c:url value="/contact/send" var="send"/>
                    <form:form name="sentMessage" id="contactForm" action="${send}"
                               method="post" modelAttribute="emailModel">
                        <div class="row control-access_group">
                            <div class="form-access_group col-xs-12 floating-label-form-access_group controls">
                                <label>Name</label>
                                <input type="text" class="form-control" id="name" name="name"
                                       required="" placeholder="Your name"
                                       data-validation-required-message="Please enter your name.">
                                <p class="help-block text-danger"></p>
                            </div>
                        </div>
                        <div class="row control-access_group">
                            <div class="form-access_group col-xs-12 floating-label-form-access_group controls">
                                <label>Email Address</label>
                                <input type="email" class="form-control" id="email"
                                       name="email" required="" placeholder="Your email adress"
                                       data-validation-required-message="Please enter your email address.">
                                <p class="help-block text-danger"></p>
                            </div>
                        </div>
                        <div class="row control-access_group">
                            <div class="form-access_group col-xs-12 floating-label-form-access_group controls">
                                <label>Message</label>
                                <textarea rows="10" cols="100" class="form-control" id="message" name="message"
                                          required="" maxlength="1500" style="resize:none" placeholder="Your message"
                                          data-validation-required-message="Please enter your message"></textarea>
                                <p class="help-block text-danger"></p>
                            </div>
                        </div>
                        <div id="success"></div>
                        <div class="row">
                            <div class="form-access_group col-xs-12">
                                <button type="submit" class="btn btn-default">Send</button>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>

        <hr>
    </jsp:body>
</page:template>
