<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<page:basePage>
    <jsp:attribute name="title">Breath of freedom</jsp:attribute>

    <jsp:body>
        <header class="intro-header">
            <img class="header-image" src="<c:url value="/img/index-header.jpg"/>">
        </header>

        <div class="titles">
            <h1>Добро пожаловать!</h1>
            <h2 class="subheading">Актуально:</h2>
        </div>
        <!-- Main Content -->
        <div class="container">
            <div class="row">
                <div class="main-content">
                    <c:if test="${!empty data}">
                        <c:forEach items="${data.values()}" var="post" varStatus="postLoop">
                            <div class="post-preview">
                                <a href="post/${post.id}">
                                    <h2 class="post-title">${post.title}</h2>
                                    <h3 class="post-subtitle">${post.subtitle}</h3>
                                </a>
                                <p class="post-meta">
                                    Posted by <a href="<c:url value='/about'/>">${post.author}</a> on
                                    <fmt:setLocale value="en_EN" scope="session"/>
                                    <fmt:formatDate type="published" dateStyle="long" pattern="MMMM dd, yyyy"
                                                    value="${post.published}"/></p>
                            </div>
                            <c:if test="${!postLoop.last}">
                                <hr>
                            </c:if>
                        </c:forEach>
                    </c:if>
                    <!-- Pager -->
                    <ul class="pager">
                        <li class="next">
                            <a class="btn btn-default" href="<c:url value='/allposts'/>">All Posts &rarr;</a>
                        </li>
                    </ul>

                </div>
            </div>
        </div>

    </jsp:body>


</page:basePage>