<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<page:basePage>
    <jsp:attribute name="title">All posts BoF</jsp:attribute>

    <jsp:body>
        <header class="intro-header">
            <img class="header-image" src="<c:url value="/resources/img/all-posts-header.jpg"/>" height="300px">
        </header>

        <div class="titles">
            <h1>Все посты</h1>
            <h2 class="subheading">за все время</h2>
        </div>
        <!-- Main Content -->
        <div class="container">
            <div class="row">
                <div class="main-content">
                    <c:if test="${!empty posts}">
                        <c:forEach items="${posts}" var="post" varStatus="postLoop">
                            <div class="post-preview">
                                <a href="/post/${post.id}">
                                    <h2 class="post-title">
                                            ${post.title}
                                    </h2>
                                    <h3 class="post-subtitle">
                                            ${post.subtitle}
                                    </h3>
                                </a>
                                <p class="post-meta">
                                    <a href="<c:url value="/about"/>">${post.author.nickName}</a>
                                    <fmt:setLocale value="ru_RU" scope="session"/>
                                    <fmt:formatDate type="date" dateStyle="long" pattern="d MMMM yyyy"
                                                    value="${post.published}"/></p>
                            </div>
                            <c:if test="${!postLoop.last}">
                                <hr>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </div>
            </div>
        </div>
    </jsp:body>
</page:basePage>