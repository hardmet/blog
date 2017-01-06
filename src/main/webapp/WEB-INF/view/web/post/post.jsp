<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<page:basePage>
    <jsp:attribute name="title">${post.title}</jsp:attribute>
    <jsp:attribute name="scrypt">
        W.initCommentService();
    </jsp:attribute>
    <jsp:body>
        <noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on
            Javascript being
            enabled. Please enable
            Javascript and reload this page!</h2></noscript>
        <fmt:setLocale value="ru_RU" scope="session"/>
        <security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_SUPER_USER')"
                            var="isUniqueUser"/>
        <security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_SUPER_USER', 'ROLE_USER')"
                            var="isUser"/>

        <!-- Page Header -->
        <!-- Set your background image for this header on the line below. -->
        <header class="intro-header">
            <c:if test="${hasMainImage}">
                <img class="header-image" src="${postMainImage}" alt="header-image">
            </c:if>
            <c:if test="${!hasMainImage}">
                <img class="header-image" src="/resources/img/post-bg.jpg" alt="header-image">
            </c:if>
        </header>

        <div class="titles">
            <c:if test="${!empty post}">
                <h1>${post.title}</h1>
                <h2 class="subheading">${post.subtitle}</h2>
            </c:if>
        </div>

        <!-- Post Content -->
        <article class="js-post-content">
            <div class="container">
                <div class="row">
                    <div class="main-content">
                        <c:if test="${!empty post}">
                            <div class="js-post" data-post-id="${post.id}">${post.text}</div>
                            <div class="gallery">
                                <c:forEach items="${images}" var="image">
                                    <a class="fancybox-thumb" rel="fancybox-thumb"
                                       href="${image}"
                                       title="">
                                        <img src="${image}" alt="thumb"/>
                                    </a>
                                </c:forEach>
                            </div>
                            <span class="meta post-time">Автор -
                                    <a href="<c:url value="/about"/>">
                                            ${post.author.nickName}</a>, дата публикации:
                                <fmt:formatDate type="date" dateStyle="long" pattern="d MMMM yyyy"
                                                       value="${post.published}"/></span>
                            <c:if test="${isUser}">
                                <security:authentication property="principal.username" var="userName"/>
                                <div class="js-commentator-email" style="display: none;" data-user-id="${userName}"></div>
                            </c:if>
                        </c:if>
                    </div>
                    <c:if test="${!empty comments || isUser}">
                        <div class="modal-content">
                            <h4>Комментарии:</h4>
                            <div id="comments">
                                    <c:forEach items="${comments}" var="comment">
                                        <div class="js-comment comment-content">
                                            <input type="hidden" class="js-comment-id" value="${comment.id}">
                                            <span class="comment-head"><a class="js-comment-a"
                                                                          href=''>${comment.author.nickName}</a>
                                                <span class="js-comment-delete fa fa-close delete-content"
                                                      ${not isUniqueUser ? 'style="visibility: hidden";' : ''}
                                                      title="удалить комментарий"></span>
                                            </span>
                                            <span class="js-comment-text comment-text">${comment.text}</span>
                                            <span class="js-comment-date comment-published">
                                                    <fmt:formatDate type="date" dateStyle="long"
                                                                    pattern="d MMMM yyyy" value="${comment.published}"/>
                                            </span>
                                        </div>
                                    </c:forEach>
                                <c:if test="${pageContext.request.getParameter('resultOfAdd') == 'true'}">
                                    <h3 style="color:green">Комментарий добавлен!</h3>
                                </c:if>

                                <c:if test="${pageContext.request.getParameter('resultOfAdd') == 'false'}">
                                    <h3 style="color:red">Something wrong, comment didn't add!</h3>
                                </c:if>
                            </div>
                            <c:if test="${isUser}">
                                <form name="sentComment" class="commentForm js-comment-form"
                                      method="post" data-author="${isUser ? userName : ''}">
                                    <div class="row control-access_group">
                                        <div class="form-access_group floating-label-form-access_group controls">
                                            <textarea rows="10" cols="100" class="form-control js-comment-text-area"
                                                      name="text" maxlength="1500" style="resize:none"
                                                      placeholder="Ваш комментарий"
                                                      data-validation-required-message="Введите свой комментарий"></textarea>
                                            <p class="help-block text-danger"></p>
                                            <button class="btn btn-default js-send-comment"
                                                    type="submit" disabled="disabled">Отправить
                                            </button>
                                        </div>
                                    </div>
                                    <div class="js-comment comment-content" hidden>
                                        <input type="hidden" class="js-comment-id" value="">
                                        <span class="comment-head"><a class="js-comment-a" href=''></a>
                                            <span class="js-comment-delete fa fa-close delete-content"
                                                  title="удалить комментарий"></span>
                                            <span class="js-comment-date comment-published"></span>
                                        </span>
                                        <span class="js-comment-text"></span>
                                    </div>
                                </form>
                            </c:if>
                        </div>
                    </c:if>
                </div>
            </div>
        </article>
    </jsp:body>
</page:basePage>