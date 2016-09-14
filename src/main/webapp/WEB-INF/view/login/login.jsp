<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<page:template>
    <jsp:attribute name="title">Sign in</jsp:attribute>
    <jsp:attribute name="scrypt">
        <script src="<c:url value="/webjars/sockjs-client/1.0.2/sockjs.js"/>"></script>
        <script src="<c:url value="/webjars/stomp-websocket/2.3.3/stomp.min.js"/>"></script>
        <script src="<c:url value="/resources/js/login_ws.js" />"></script>
    </jsp:attribute>
    <jsp:body>
        <style>
            <%@include file="/resources/css/signin.css"%>
        </style>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
        <div class="header"></div>
        <!-- Content Row -->
        <div class="row">
            <div id="session" style="display: none;"><c:out value="${pageContext.session.id}"/></div>
            <security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_SUPER_USER', 'ROLE_USER')"
                                var="isUser"/>
            <form:form name="signIn" id="signIn" action="j_spring_security_check" method="post" class="form-signin">
                <c:if test="${isUser}">
                    You are signed in as <security:authentication property="principal.username"/></c:if>
                <c:if test="${not isUser}">
                    <c:if test="${not empty param.error}">
                        <span style="font-size: x-small; color: red; "><b>Wrong username or pass, try another</b></span>
                    </c:if>

                    <h2 class="form-signin-heading">Sign in</h2>

                    <label for="inputEmail" class="sr-only"><spring:message text="Email"/></label>
                    <input id="inputEmail" class="form-control" name="j_username"
                           required autofocus
                           type="email" data-validation-required-message="Please enter your e-mail"
                           placeholder="e-mail"/>

                    <label for="inputPassword" class="sr-only"><spring:message text="Password"/></label>
                    <input type="password" id="inputPassword" class="form-control" name="j_password"
                           required data-validation-required-message="Please enter your password"
                           placeholder="password"/>

                    <div class="checkbox">
                        <label>
                            <input type="checkbox" id="rememberme" name="_spring_security_remember_me"/>
                            remember me
                        </label>
                    </div>
                    <button type="submit" class="btn btn-default">Sign in</button>
                </c:if>
            </form:form>

            <c:if test="${not isUser}">
                <form:form name="signup" id="signUp" method="POST"
                           class="form-signin" modelAttribute="userModel">
                    <c:if test="${pageContext.request.getParameter('status') == '200'}">
                        <h3 style="color:green">${pageContext.request.getParameter('message')}</h3>
                    </c:if>
                    <c:if test="${pageContext.request.getParameter('status') != '200'}">
                        <h3 style="color:red">${pageContext.request.getParameter('message')}</h3>
                    </c:if>
                    <h2 class="form-signin-heading">Sign up</h2>
                    <label for="regEmail" class="sr-only"></label>
                    <input id="regEmail" class="form-control" name="email" required="" autofocus
                           type="email" data-validation-required-message="Please enter your e-mail" placeholder="e-mail"/>
                    <label for="regPass" class="sr-only"></label>
                    <input type="password" id="regPass" class="form-control" name="password" required=""
                                data-validation-required-message="Please enter your password" placeholder="password"/>
                    <label for="regNick" class="sr-only"></label>
                    <input id="regNick" class="form-control" name="nickName" required=""
                           data-validation-required-message="Please enter your nick name" placeholder="nick name"/>
                    <label for="regFirst" class="sr-only"></label>
                    <input id="regFirst" class="form-control" name="firstName" placeholder="first name"/>
                    <label for="regLast" class="sr-only"></label>
                    <input id="regLast" class="form-control" name="lastName" placeholder="last name"/>
                    <label for="regBirthday" class="sr-only"></label>
                    <input id="regBirthday" class="form-control" name="birthday" type="date" pattern="yyyy-MM-dd"/>
                    <button id="signUpButton" type="submit"
                            class="btn btn-default" disabled="disabled">Sign up</button>
                </form:form>
            </c:if>
            <hr>
        </div>
    </jsp:body>
</page:template>