<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:template>
    <jsp:attribute name="title">Error 403</jsp:attribute>

    <jsp:body>
        <header class="intro-header">
            <img class="header-image" src="<c:url value="/resources/img/error.jpg"/>" height="300px">
        </header>

        <!-- Post Content -->
        <div class="container">
            <div class="row">
                <div class="main-content">
                        <span>
                            К сожалению, или к счастью, у вас недостаточно прав доступа для
                            просмотра запрашиваемой страницы! Войдите в систему или зарегестрируйтесь на странице -
                            <a href="<c:url value="/login"/>">Sign in</a>.
                        </span>
                </div>
            </div>
        </div>
        <hr>
    </jsp:body>

</page:template>
