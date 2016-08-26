<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<page:template>
    <jsp:attribute name="title">Profile</jsp:attribute>
    <jsp:body>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
        <style>
            <%@include file="/resources/css/signin.css"%>
        </style>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
        <div class="header"></div>
        <fmt:formatDate type="date" dateStyle="short" pattern="yyyy-MM-dd" var="theFormattedDate"
                        value="${user.birthday}"/>
        <!-- Content Row -->
        <div class="row">
            <c:url value="/user/save" var="editProfile"/>
            <c:if test="${pageContext.request.getParameter('resultReg') == 'true'}">
                <h3 style="color:green">Profile has been update!</h3>
            </c:if>
            <c:if test="${pageContext.request.getParameter('resultReg') == 'false'}">
                <h3 style="color:red">Profile hasn't been update!</h3>
            </c:if>
            <form name="editForm" id="editForm" action="${editProfile}" method="post"
                  class="form-signin" modelAttribute="userModel">
                <h2 class="form-signin-heading">Your profile</h2>
                <span>e-mail: <security:authentication property="principal.username"/></span>
                <label for="regPass" class="sr-only"></label>
                <input type="password" id="regPass" class="form-control" name="regPass" required
                       data-validation-required-message="Please enter your password"
                       placeholder="change password"/>
                <label for="regPassRet" class="sr-only"></label>
                <input type="password" id="regPassRet" class="form-control" name="regPassConfirm" required
                       data-validation-required-message="retry your password"
                       placeholder="retry password"/>
                <label for="regNick" class="sr-only"></label>
                <input id="regNick" class="form-control" name="regNick" required
                       data-validation-required-message="Please enter your nick name"
                       placeholder="nick name" value="${user.nickName}"/>
                <label for="regFirst" class="sr-only"></label>
                <input id="regFirst" class="form-control" name="regFirst"
                       placeholder="first name" value="${user.firstName}"/>
                <label for="regLast" class="sr-only"></label>
                <input id="regLast" class="form-control" name="regLast"
                       placeholder="last name" value="${user.lastName}"/>
                <label for="regBirthday" class="sr-only"></label>
                <input id="regBirthday" class="form-control" name="regBirthday"
                       type="date" value="${theFormattedDate}"/>
                <button type="submit" class="btn btn-default">Save</button>
            </form>
            <hr>
        </div>
    </jsp:body>
</page:template>
