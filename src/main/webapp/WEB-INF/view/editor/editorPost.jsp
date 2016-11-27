<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:basePage>
    <jsp:attribute name="title">Create post</jsp:attribute>

    <jsp:body>
        <!-- Page Header -->
        <!-- Set your background image for this header on the line below. -->
        <header class="intro-header">
            <img class="header-image" src="<c:url value="/img/newpost-header.jpg"/>" height="300px">
        </header>

        <div class="titles">
            <h1>Creating new post</h1>
            <h2 class="subheading">be confident, it's really that you need</h2>
        </div>

        <!-- Page Content -->
        <div class="container">
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
                    <c:url value="/post/add" var="send"/>
                    <!-- Contact Form -->
                    <!-- In order to set the email address and subject line for the contact form go to the bin/contact_me.php file. -->
                    <h3>Enter fields</h3>
                    <form name="sentPost" id="contactForm" action="${send}"
                          method="post" modelAttribute="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input class="createdPostId" type="hidden" value="${post.id}"/>
                        <div class="row control-access_group">
                            <div class="form-access_group col-xs-12 floating-label-form-access_group controls">
                                <div class="controls">
                                    <label for="title">Title:</label>
                                    <input id="title" type="text" class="form-control" name="title"
                                           required data-validation-required-message="Please enter title."
                                           value="${post.title}">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                        </div>
                        <div class="row control-access_group">
                            <div class="form-access_group col-xs-12 floating-label-form-access_group controls">
                                <div class="controls">
                                    <label for="subtitle">Subtitle:</label>
                                    <input type="tel" class="form-control" id="subtitle" name="subtitle"
                                           required data-validation-required-message="Please enter subtitle."
                                        ${post.subtitle}>
                                </div>
                            </div>
                        </div>
                        <div class="row control-access_group">
                            <div class="form-access_group col-xs-12 floating-label-form-access_group controls">
                                <label for="text">Text:</label>
                                <textarea rows="10" cols="100" class="form-control" id="text" name="text"
                                          required="" maxlength="1500" style="resize:none" placeholder="Text"
                                          data-validation-required-message="Please enter your text"
                                value="${post.text}"></textarea>
                                <p class="help-block text-danger"></p>
                            </div>
                        </div>
                        <div id="success"></div>
                        <div class="row">
                            <div class="form-access_group col-xs-12">
                                <button type="submit" class="btn btn-default">Send</button>
                            </div>
                        </div>
                    </form>
                    <!-- Upload file form -->
                    <form id="upload-file-form">
                        <label for="upload-file-input">Upload your file:</label>
                        <input id="upload-file-input" type="file" name="uploadImages" accept="image/*" multiple />
                    </form>
                    <progress id="prog" value="0" max="100" class="progress-bar"></progress>
                    <div id="imgContainer"></div>
                </div>
            </div>
        </div>
        <!-- /.row -->
    </jsp:body>
</page:basePage>