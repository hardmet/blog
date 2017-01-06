<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<page:basePage>
    <jsp:attribute name="title">Контакты - Breath of freedom</jsp:attribute>

    <jsp:body>
        <!-- Page Header -->
        <!-- Set your background image for this header on the line below. -->
        <header class="intro-header">
            <img class="header-image" src="<c:url value="/resources/img/contact-header.jpg"/>" height="300px">
        </header>
        <div class="titles">
            <h1>Связь со мной</h1>
            <h2 class="subheading">Выбор за вами: письмо или телефонный звонок</h2>
        </div>

        <!-- Post Content -->
        <div class="container">
            <div class="row">
                <div class="main-content">
                    <!-- Content Row -->
                    <div class="row">
                        <!-- Contact Details Column -->
                        <div class="col-md-6 contact-left-part">
                            <!-- /.row -->
                            <h3>Отправить мне письмо</h3>
                            <c:url value="/contact/send" var="send"/>
                            <form:form name="sentMessage" id="contactForm" action="${send}"
                                       method="post" modelAttribute="email">
                                <div class="row control-access_group">
                                    <div class="form-access_group col-xs-12 floating-label-form-access_group controls">
                                        <label></label>
                                        <input type="text" class="form-control" id="name" name="name"
                                               required="" placeholder="Ваше имя"
                                               data-validation-required-message="Пожалуйста, введите свое имя!">
                                        <p class="help-block text-danger"></p>
                                    </div>
                                </div>
                                <div class="row control-access_group">
                                    <div class="form-access_group col-xs-12 floating-label-form-access_group controls">
                                        <label></label>
                                        <input type="email" class="form-control" id="email"
                                               name="email" required="" placeholder="Ваша эл. почта"
                                               data-validation-required-message="Пожалуйста, введите свою эл. почту!">
                                        <p class="help-block text-danger"></p>
                                    </div>
                                </div>
                                <div class="row control-access_group">
                                    <div class="form-access_group col-xs-12 floating-label-form-access_group controls">
                                        <label></label>
                                        <textarea rows="10" cols="100" class="form-control" id="message" name="message"
                                                  required="" maxlength="1500" style="resize:none" placeholder="Ваше сообщение"
                                                  data-validation-required-message="Пожалуйста, введите сообщение"></textarea>
                                        <p class="help-block text-danger"></p>
                                        <button type="submit" class="btn btn-default">Отправить</button>
                                    </div>
                                </div>
                                <div id="success"></div>
                            </form:form>
                        </div>
                        <div class="col-md-6">
                            <c:if test="${pageContext.request.getParameter('resultSending') == 'true'}">
                                <h3 style="color:green">Email already send!</h3>
                            </c:if>
                            <c:if test="${pageContext.request.getParameter('resultSending') == 'false'}">
                                <h3 style="color:red">Email wasn't send!</h3>
                            </c:if>
                            <h3>Контакты:</h3>
                            <p><i class="fa fa-phone"></i>
                                <abbr title="Phone">Телефон</abbr>: +7-977-710-74-67</p>
                            <p><i class="fa fa-phone"></i>
                                <abbr title="Phone">Телефон</abbr>: +7-981-751-05-08</p>
                            <p><i class="fa fa-envelope-o"></i>
                                <abbr title="Email">Эл. почта</abbr>: <a
                                        href="mailto:borkafedor@mail.ru">borkafedor@mail.ru</a>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</page:basePage>
