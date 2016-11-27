<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:basePage>
    <jsp:body>
        <!-- Page Header -->
        <!-- Set your background image for this header on the line below. -->
        <header class="intro-header">
            <img class="header-image" src="<c:url value="/resources/img/error.jpg"/>">
        </header>
        <div class="titles">
            <h1>Страница ошибки</h1>
            <h2 class="subheading">exception handler</h2>
        </div>

        <!-- Page Content -->
        <div class="container">
            <div class="row">
                <div class="main-content">
                    <div class="row">
                        <div class="col-md-4">
                            <ol class="breadcrumb">
                                <li><a href="${pageContext.request.contextPath}/index">Home</a>
                                </li>
                                <li class="active">Exception Handler</li>
                            </ol>
                        </div>
                    </div>
                    <!-- /.row -->

                    <!-- Content Row -->
                    <div class="row">

                        <div class="col-lg-12">
                            <p>This is message from ExceptionHandler:</p>
                            <b>${exceptionMsg}</b>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <!-- /.row -->
        <!-- /.container -->
    </jsp:body>
</page:basePage>
