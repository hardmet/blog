<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<page:basePage>
    <jsp:attribute name="title">Profile</jsp:attribute>
    <jsp:attribute name="scrypt">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signin.css">
    </jsp:attribute>
    <jsp:body>
        <div class="header"></div>
        <fmt:formatDate type="published" dateStyle="short" pattern="dd-MM-yy" var="theFormattedDate"
                        value="${user.birthday}"/>
        <!-- Content Row -->
        <div class="row">
            <c:if test="${pageContext.request.getParameter('resultOfChangingProfile') == 'true'}">
                <h3 style="color:green">Profile has been update!</h3>
            </c:if>
            <c:if test="${pageContext.request.getParameter('resultOfChangingProfile') == 'false'}">
                <h3 style="color:red">Profile hasn't been update!</h3>
            </c:if>
            <form name="editForm" id="editForm" class="form-signin">
                <span id="results"></span>
                <h2 class="form-signin-heading">Your author</h2>
                <label for="regEmail" class="sr-only"></label>
                <input id="regEmail" class="form-control" name="email" required="" autofocus
                type="email" value="${user.email}" disabled="disabled"/>
                <label for="regPass" class="sr-only"></label>
                <label for="regPass" class="sr-only"></label>
                <input type="password" id="regPass" class="form-control" name="regPass" required
                       data-validation-required-message="Please enter your password"
                       placeholder="change password"/>
                <label for="regPassRet" class="sr-only"></label>
                <input type="password" id="regPassRet" class="form-control" name="regPassConfirm" required
                       data-validation-required-message="retry your password"
                       placeholder="retry password"/>
                <label for="regNick" class="sr-only"></label>
                <input id="regNick" class="form-control" name="regNick" disabled="disabled" required
                       data-validation-required-message="Please enter your nick name"
                       placeholder="nick name" value="${user.nickName}"/>
                <label for="regFirst" class="sr-only"></label>
                <input id="regFirst" class="form-control" name="regFirst"
                       placeholder="first name" value="${user.firstName}"/>
                <label for="regLast" class="sr-only"></label>
                <input id="regLast" class="form-control" name="regLast"
                       placeholder="last name" value="${user.lastName}"/>
                <label for="actualDate" class="sr-only"></label>
                <input id="actualDate" class="form-control" name="birthday" type="text" value="${theFormattedDate}"/>
                <input id="regBirthday" class="form-control" name="regBirthday" type="hidden"/>
                <button id="saveUser" class="btn btn-default">Save</button>
            </form>
            <hr>
        </div>
    </jsp:body>
</page:basePage>
