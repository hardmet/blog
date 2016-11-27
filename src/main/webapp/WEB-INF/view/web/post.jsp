<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<page:basePage>
    <jsp:attribute name="title">${postRelatedData.post.title}</jsp:attribute>
    <jsp:attribute name="scrypt">
        <link rel="stylesheet" href="<c:url value="/fancybox/jquery.fancybox.css"/>" type="text/css"
              media="screen"/>
        <link rel="stylesheet" href="<c:url value="/fancybox/helpers/jquery.fancybox-buttons.css"/>"
              type="text/css" media="screen"/>
        <link rel="stylesheet" href="<c:url value="/fancybox/helpers/jquery.fancybox-thumbs.css"/>"
              type="text/css" media="screen"/>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
        <script src="<c:url value="/webjars/sockjs-client/1.0.2/sockjs.js"/>"></script>
        <script src="<c:url value="/webjars/stomp-websocket/2.3.3/stomp.min.js"/>"></script>
        <script src="<c:url value="/js/post_ws.js" />"></script>
        <script type="text/javascript" src="<c:url value="/fancybox/custom-fancybox.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/fancybox/jquery.fancybox.pack.js"/>"></script>
        <script type="text/javascript"
                src="<c:url value="/fancybox/jquery.mousewheel-3.0.6.pack.js"/>"></script>
        <script type="text/javascript"
                src="<c:url value="/fancybox/helpers/jquery.fancybox-buttons.js"/>"></script>
        <script type="text/javascript"
                src="<c:url value="/fancybox/helpers/jquery.fancybox-media.js"/>"></script>
        <script type="text/javascript"
                src="<c:url value="/fancybox/helpers/jquery.fancybox-thumbs.js"/>"></script>
    </jsp:attribute>
    <jsp:body>
        <noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on
            Javascript being
            enabled. Please enable
            Javascript and reload this page!</h2></noscript>
        <fmt:setLocale value="en_EN" scope="session"/>
        <security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_SUPER_USER')"
                            var="isUniqUser"/>
        <security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_SUPER_USER', 'ROLE_USER')"
                            var="isUSer"/>

        <!-- Page Header -->
        <!-- Set your background image for this header on the line below. -->
        <header class="intro-header">
            <img class="header-image" src="<c:url value="/img/post-bg.jpg"/>" alt="header-image">
        </header>

        <div class="titles">
            <c:if test="${!empty postRelatedData}">
                <h1>${postRelatedData.post.title}</h1>
                <h2 class="subheading">${postRelatedData.post.subtitle}</h2>
            </c:if>
        </div>

        <!-- Post Content -->
        <article>
            <div class="container">
                <div class="row">
                    <div class="main-content">
                        <c:if test="${!empty postRelatedData.post}">
                            <span>${postRelatedData.post.text}</span>
                            <div class="gallery">
                                <c:forEach items="${postRelatedData.images}" var="image">
                                    <a class="fancybox-thumb" rel="fancybox-thumb"
                                       href="${image.pathToImage}"
                                       title="">
                                        <img src="${image.pathToImage}" alt="thumb"/>
                                    </a>
                                </c:forEach>
                            </div>
                            <span class="meta post-time">Posted by
                                    <a href="${pageContext.request.contextPath}/about">
                                            ${postRelatedData.post.author}</a>
                                    on <fmt:formatDate type="published" dateStyle="long" pattern="MMMM dd, yyyy"
                                                       value="${postRelatedData.post.published}"/></span>
                            <span id="post" style="display: none;">${postRelatedData.post.id}</span>
                            <c:if test="${isUSer}"><security:authentication property="principal.username" var="userName"/></c:if>
                        </c:if>
                    </div>
                    <div class="modal-content">
                        <h4>Comments:</h4>
                        <div id="comments">
                            <c:if test="${!empty postRelatedData.comments}">
                                <c:forEach items="${postRelatedData.comments}" var="comment">
                                    <div class="comment-content">
                                        <input type="hidden" class="js-comment-id" value="${comment.id}">
                                        <span class="comment-head"><a href=''>${comment.author}</a>
                                            <span class="fa fa-close delete-content ${not isUniqUser ? 'hidden' : ''}"
                                                  title="удалить комментарий"></span>
                                            <span class="comment-time"><fmt:formatDate type="published" dateStyle="long"
                                                  pattern="MMMM dd, yyyy" value="${comment.published}"/></span>
                                        </span>
                                        <span>${comment.text}</span>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${pageContext.request.getParameter('resultOfAdd') == 'true'}">
                                <h3 style="color:green">Comment added!</h3>
                            </c:if>

                            <c:if test="${pageContext.request.getParameter('resultOfAdd') == 'false'}">
                                <h3 style="color:red">Something wrong, comment didn't add!</h3>
                            </c:if>
                        </div>
                            <%--<c:url value="/post/${postRelatedData.post.id}/comment/add" var="send"/>--%>

                        <form name="sentComment" class="commentForm"
                              method="post">
                            <div class="row control-access_group">
                                <div class="form-access_group floating-label-form-access_group controls">
                                    <input class="comment-id" type="hidden" value="${isUSer ? userName : ''}"/>
                                    <label>Add comment</label>
                                    <textarea rows="10" cols="100" class="form-control" id="comment_text_area"
                                              name="text" maxlength="1500" style="resize:none"
                                              placeholder="Your comment"
                                              data-validation-required-message="Please enter your message"></textarea>
                                    <p class="help-block text-danger"></p>
                                    <button id="send" class="btn btn-default"
                                            type="submit" disabled="disabled">Send
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </article>

        <hr>
    </jsp:body>
</page:basePage>