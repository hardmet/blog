<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<page:editorBasePage>
    <jsp:attribute name="title">Создание поста</jsp:attribute>
    <jsp:attribute name="scrypt">
        E.initEditorPost();
    </jsp:attribute>
    <jsp:body>
        <!-- Page Header -->
        <!-- Set your background image for this header on the line below. -->
        <header class="intro-header">
            <img class="header-image" src="<c:url value="/resources/img/edit-post-header.jpg"/>" height="300px">
        </header>

        <div class="titles">
            <h1>Creating new post</h1>
            <h2 class="subheading">be confident, it's really that you need</h2>
        </div>

        <!-- Page Content -->
        <div class="js-post-content container">
            <!-- Page Heading/Breadcrumbs -->
            <div class="row">
                <c:if test="${pageContext.request.getParameter('resultAdding') == 'true'}">
                    <h3 style="color:green">New post has been created!</h3>
                </c:if>
                <c:if test="${pageContext.request.getParameter('resultAdding') == 'false'}">
                    <h3 style="color:red">New post hasn't been created! Smth. wrong!</h3>
                </c:if>
                <div class="main-content">
                    <h1 class="page-header">Post
                        <small>Create new post</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="index">Home</a>
                        </li>
                        <li class="active">Create post</li>
                    </ol>

                    <!-- Contact Form -->
                    <!-- In order to set the email address and subject line for the contact form go to the bin/contact_me.php file. -->
                    <h3>Enter fields</h3>
                    <form:form name="sentPost" class="js-post-form"
                          method="POST" modelAttribute="post" data-id="${post.id}">
                        <form:input type="text" path="id" hidden="true"/>
                        <div class="row control-access_group">
                            <div class="form-access_group col-xs-12 floating-label-form-access_group controls">
                                <div class="controls">
                                    <label for="title">Title:</label>
                                    <form:input id="title" type="text" class="form-control" name="title"
                                           required="required" data-validation-required-message="Please enter title."
                                                path="title"/>
                                    <p class="help-block"></p>
                                </div>
                            </div>
                        </div>
                        <div class="row control-access_group">
                            <div class="form-access_group col-xs-12 floating-label-form-access_group controls">
                                <div class="controls">
                                    <label for="subtitle">Subtitle:</label>
                                    <form:input type="tel" class="form-control" id="subtitle" name="subtitle"
                                           required="required" path="subtitle"
                                                data-validation-required-message="Please enter subtitle."/>
                                </div>
                            </div>
                        </div>
                        <div class="row control-access_group">
                            <div class="form-access_group col-xs-12 floating-label-form-access_group controls">
                                <label for="text">Text:</label>
                                <form:textarea rows="10" cols="100" class="form-control" id="text" name="text"
                                          required="" maxlength="1500" style="resize:none" placeholder="Text"
                                          data-validation-required-message="Please enter your text" path="text"/>
                                <p class="help-block text-danger"></p>
                            </div>
                        </div>
                        <div class="row control-access_group">
                            <div class="form-access_group col-xs-12 floating-label-form-access_group controls">
                                <label for="actual-published">Опубликовано:</label>
                                <fmt:formatDate type="date" dateStyle="short" pattern="yyyy-MM-dd" var="theFormattedDate"
                                                value="${post.published}"/>
                                <input id="actual-published" class="js-actual-published form-control" type="text"
                                       readonly="readonly" value="${theFormattedDate}"/>
                                <form:input path="published" class="js-published form-control" type="hidden"/>
                            </div>
                        </div>
                        <div class="row control-access_group">
                            <div class="form-access_group col-xs-12 floating-label-form-access_group controls">
                                <label>Отобразить</label>
                                <form:checkbox path="visible" title="Отобразить"/>
                            </div>
                        </div>
                        <div id="success"></div>
                        <div class="row">
                            <div class="form-access_group col-xs-12">
                                <button type="submit" class="btn btn-default">Send</button>
                            </div>
                        </div>
                    </form:form>
                    <c:if test="${post.id ne 0}">
                        <div class="js-main-image">
                            <c:if test="${post.hasMainImage}">
                                <div class="js-image-block">
                                    <img width="100px" src="${postMainImage}"/>
                                    <button class="js-remove-main-image">Удалить</button>
                                </div>
                            </c:if>
                        </div>
                        <div class="js-main-image">
                            <jsp:include page="../panel/uploadImagePanel.jsp">
                                <jsp:param name="type" value="postMain" />
                                <jsp:param name="isMultiple" value="false" />
                            </jsp:include>
                        </div>
                        <hr>
                        <c:if test="${not empty images}">
                            <div class="js-image-gallery">
                                <c:forEach items="${images.values()}" var="image">
                                    <div class="js-image-block">
                                        <img width="100px" src="${image.get(1)}"/>
                                        <button class="js-remove-image" data-id="${image.get(0)}">Удалить</button>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>
                        <div class="js-content-images">
                            <jsp:include page="../panel/uploadImagePanel.jsp">
                                <jsp:param name="type" value="postContent" />
                                <jsp:param name="isMultiple" value="true" />
                            </jsp:include>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
        <!-- /.row -->
    </jsp:body>
</page:editorBasePage>