<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<page:template>
    <jsp:attribute name="title">${post.title}</jsp:attribute>

    <jsp:body>
        <fmt:setLocale value="en_EN" scope="session"/>
        <!-- Page Header -->
        <!-- Set your background image for this header on the line below. -->
        <header class="intro-header">
            <img class="header-image" src="<c:url value="/resources/img/post-bg.jpg"/>" height="300px">
        </header>

        <div class="titles">
            <c:if test="${!empty data}">
                <h1>${data.post.title}</h1>
                <h2 class="subheading">${data.post.subtitle}</h2>
            </c:if>
        </div>

        <!-- Post Content -->
        <article>
            <div class="container">
                <div class="row">
                    <div class="main-content">
                        <c:if test="${!empty data.post}">
                            <span>${data.post.text}</span>
                            <span class="meta post-time">Posted by
                                    <a href="#">${data.post.author}</a>
                                    on <fmt:formatDate type="date" dateStyle="long" pattern="MMMM dd, yyyy"
                                                       value="${data.post.date}"/></span>
                        </c:if>
                    </div>
                    <div class="modal-content">
                        <h4>Comments:</h4>

                        <c:if test="${!empty data.comments}">
                            <c:forEach items="${data.comments}" var="comment">
                                <div class="comment-content">
                                        <span class="comment-head"><a href="#">${comment.author}</a>
                                            <span class="comment-time">
                                                <fmt:formatDate type="date" dateStyle="long"
                                                                pattern="MMMM dd, yyyy" value="${comment.date}"/>
                                            </span>
                                        </span>
                                    <span>${comment.text}</span>
                                </div>
                            </c:forEach>
                        </c:if>
                        <c:if test="${pageContext.request.getParameter('resultAdd') == 'true'}">
                            <h3 style="color:green">Comment added!</h3>
                        </c:if>

                        <c:if test="${pageContext.request.getParameter('resultAdd') == 'false'}">
                            <h3 style="color:red">Something wrong, comment didn't add!</h3>
                        </c:if>

                        <c:url value="/post/${data.post.id}/comment/add" var="send"/>

                        <form:form name="sentComment" id="contactForm" action="${send}"
                                   method="post" modelAttribute="commentModel">
                            <div class="row control-access_group">
                                <div class="form-access_group floating-label-form-access_group controls">
                                    <label>Add comment</label>
                                    <textarea rows="10" cols="100" class="form-control" id="text" name="text"
                                              required maxlength="1500" style="resize:none"
                                              placeholder="Your comment"
                                              data-validation-required-message="Please enter your message"></textarea>
                                    <p class="help-block text-danger"></p>
                                </div>
                            </div>
                            <div id="success"></div>
                            <div class="row">
                                <div class="form-access_group">
                                    <button type="submit" class="btn btn-default">Send</button>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </article>

        <hr>
    </jsp:body>
</page:template>