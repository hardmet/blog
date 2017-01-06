<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<page:editorBasePage>
    <jsp:attribute name="title">Изменить профиль</jsp:attribute>
    <jsp:attribute name="link">
        <link rel="stylesheet" href="<c:url value="/resources/css/signin.css"/>">
    </jsp:attribute>
    <jsp:attribute name="scrypt">
        E.initEditorProfile();
    </jsp:attribute>
    <jsp:body>
        <div class="header"></div>
        <!-- Content Row -->
        <div class="js-author-content row">
            <c:if test="${pageContext.request.getParameter('resultOfChangingProfile') == 'true'}">
                <h3 style="color:green">Profile has been update!</h3>
            </c:if>
            <c:if test="${pageContext.request.getParameter('resultOfChangingProfile') == 'false'}">
                <h3 style="color:red">Profile hasn't been update!</h3>
            </c:if>
            <form:form id="editForm" class="js-editAuthorForm form-signin" data-id="${author.id}"
                       method="POST" modelAttribute="author">
                <div class="js-results result-edit-author">
                    <i class="fa fa-info-circle"></i><span class="js-message message"></span>
                </div>
                <h2 class="form-signin-heading">Ваш профиль</h2>
                <label>E-mail:</label>
                <form:input class="js-email form-control" path="email" required="true" autofocus="true"
                            type="email" disabled="true"/>

                <label>Смена пароля:</label>
                <input type="password" id="password" class="js-password js-change-pass form-control"
                       data-validation-required-message="Please enter your password"
                       placeholder="для смены пароля введите новый"/>
                <div class="js-password-confirmation js-change-pass confirmation">
                    <label>Подтверждение пароля</label>
                    <input type="password" id="confirm_password" class="js-confirm form-control"
                           data-validation-required-message="retry your password"
                           placeholder="повторите пароль"/>
                </div>
                <p class="js-pass-validation validation-mes"></p>
                <label>Ник</label>
                <form:input class="js-nick form-control" id="nick_name" path="nickName" disabled="disabled" required="true"
                       data-validation-required-message="Please enter your nick name" placeholder="Ник"/>
                <p class="js-nick-validation validation-mes"></p>
                <label>Имя</label>
                <form:input class="js-first-name form-control" path="firstName" placeholder="Имя"/>
                <label>Фамилия</label>
                <form:input class="js-last-name form-control" path="lastName" placeholder="Фамилия"/>
                <fmt:formatDate type="date" dateStyle="short" pattern="yyyy-MM-dd" var="theFormattedDate"
                                value="${author.birthday}"/>
                <label for="birthday">День рождения</label>
                <input id="birthday" class="js-actual-birthday form-control" type="text" value="${theFormattedDate}"
                       placeholder="День рождения"/>
                <form:input class="js-birthday form-control" path="birthday" type="hidden"/>
                <button class="js-saveAuthor btn btn-default">Сохранить</button>
            </form:form>
        </div>
    </jsp:body>
</page:editorBasePage>
