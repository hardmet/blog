<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<page:basePage>
    <jsp:attribute name="title">Регистрация и авторизация</jsp:attribute>
    <jsp:attribute name="link">
        <link rel="stylesheet" href="<c:url value="/resources/css/signin.css"/>"/>
        <link rel="stylesheet" href="<c:url value="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css"/>"/>
    </jsp:attribute>
    <jsp:attribute name="scrypt">
        W.initLoginService();
    </jsp:attribute>
    <jsp:body>
        <div class="header"></div>
        <!-- Content Row -->
        <div class="js-login-page row">
            <input class="js-session" type="hidden" value="${pageContext.session.id}"/>
            <security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_SUPER_USER', 'ROLE_USER')"
                                var="isUser"/>
            <form:form name="signIn" id="signIn" action="j_spring_security_check" method="post" class="form-signin">
                <c:if test="${isUser}">
                    You are signed in as <security:authentication property="principal.username"/></c:if>
                <c:if test="${not isUser}">
                    <c:if test="${not empty param.error}">
                        <span class="login-error"><b>Неправильный e-mail или пароль!</b></span>
                    </c:if>

                    <h2 class="form-signin-heading">Вход</h2>
                    <label for="inputEmail" class="sr-only"><spring:message text="Email"/></label>
                    <input id="inputEmail" class="form-control" name="j_username"
                           required autofocus
                           type="email" data-validation-required-message="Please enter your e-mail"
                           placeholder="e-mail"/>

                    <label for="inputPassword" class="sr-only"><spring:message text="Password"/></label>
                    <input type="password" id="inputPassword" class="form-control" name="j_password"
                           required data-validation-required-message="Please enter your password"
                           placeholder="пароль"/>

                    <div class="checkbox">
                        <label>
                            <input type="checkbox" id="rememberme" name="_spring_security_remember_me"/>
                            Запомнить меня
                        </label>
                    </div>
                    <button type="submit" class="btn btn-default">Войти</button>
                </c:if>
            </form:form>

            <c:if test="${not isUser}">
                <form:form name="signup" method="POST"
                           class="js-sign-up-form form-signin" modelAttribute="author">
                    <h3 class="js-result-registration info-hat" style="display:none;"></h3>
                    <h2 class="form-signin-heading">Регистрация</h2>
                    <label class="sr-only"></label>
                    <input class="form-control js-reg-email" name="email" required="" autofocus
                           type="email" data-validation-required-message="Please enter your e-mail"
                           placeholder="обязательное поле: e-mail"/>
                    <label class="sr-only"></label>
                    <input type="password" class="js-reg-pass form-control" name="password" required=""
                           data-validation-required-message="Please enter your password"
                           placeholder="обязательное поле: пароль"/>
                    <label class="sr-only"></label>
                    <input class="js-reg-nick form-control" name="nickName" required=""
                           data-validation-required-message="Please enter your nick name"
                           placeholder="обязательное поле: ник"/>
                    <label class="sr-only"></label>
                    <input class="js-reg-first form-control" name="firstName" placeholder="имя"/>
                    <label class="sr-only"></label>
                    <input class="js-reg-last form-control" name="lastName" placeholder="фамилия"/>
                    <label class="sr-only"></label>
                    <input class="js-actual-date form-control" name="birthday" type="text" placeholder="день рождения"/>
                    <input class="js-reg-birthday form-control" type="hidden"/>
                    <button type="submit" class="btn btn-default js-sign-up-button" disabled="disabled">Отправить</button>
                </form:form>
            </c:if>
            <hr>
        </div>
    </jsp:body>
</page:basePage>