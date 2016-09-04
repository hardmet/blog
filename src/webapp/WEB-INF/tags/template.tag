<!DOCTYPE html>
<%@tag description="Template Site tag" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@attribute name="title" fragment="true" %>
<html>
<head>
    <title>
        <jsp:invoke fragment="title"/>
    </title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">

    <!-- Bootstrap Core CSS -->
    <spring:url value="/resources/vendor/bootstrap/css/bootstrap.min.css" var="bootstrap"/>
    <link href="${bootstrap}" rel="stylesheet"/>

    <!-- Custom CSS -->
    <spring:url value="/resources/css/clean-blog.min.css" var="startertemplate"/>
    <link href="${startertemplate}" rel="stylesheet"/>

    <!-- Custom Fonts -->
    <spring:url value="/resources/vendor/font-awesome/css/font-awesome.min.css" var="fontawesome"/>
    <link href="${fontawesome}" rel="stylesheet"/>
    <link href='http://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic' rel='stylesheet'
          type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
          rel='stylesheet' type='text/css'>


    <!-- jQuery -->
    <spring:url value="/resources/vendor/jquery/jquery.min.js" var="jqueryjs"/>
    <script src="${jqueryjs}"></script>

    <!-- Bootstrap Core JavaScript -->
    <spring:url value="/resources/vendor/bootstrap/js/bootstrap.min.js" var="js"/>
    <script src="${js}"></script>

    <!-- Theme JavaScript -->
    <spring:url value="/resources/js/clean-blog.min.js" var="themejs"/>
    <script src="${themejs}"></script>

    <!-- jQuery Validate data -->
    <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
    <spring:url value="/resources/vendor/jquery/validate.js" var="validate"/>
    <script src="${validate}"></script>
</head>

<body>
<div class="wrapper">
    <div class="content" rel="main">

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-custom navbar-fixed-top">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header page-scroll">
                    <button type="button" class="navbar-toggle" data-toggle="collapse"
                            data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span>
                        Menu <i class="fa fa-bars"></i>
                    </button>
                    <a class="navbar-brand" href="<c:url value="/index"/>">Breath of freedom</a>
                </div>

                <!--This is form for searching, need to fix some hard-code
                    <form name="findMessage" id="findForm" novalidate>
                        <span class="fa-stack fa-lg pull-left search" style="color: transparent; top: 15px;">
                                            <i class="fa fa-search fa-stack-1x fa-inverse"></i>
                                        </span>
                        <div class="navbar-header page-scroll">
                            <div class="col-xs-12 floating-label-form-access_group controls floating-search-form-access_group">
                                <label>searching...</label>
                                <input type="text" class="form-control" placeholder="enter query"
                                       id="search" required data-validation-required-message="search">
                            </div>
                        </div>
                    </form>
                    -->

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="<c:url value="/index"/>">Blog</a>
                        </li>
                        <li>
                            <a href="<c:url value="/projects"/>">Projects</a>
                        </li>
                        <li>
                            <a href="<c:url value="/about"/>">Bio</a>
                        </li>
                        <li>
                            <a href="<c:url value="/contact"/>">Contact</a>
                        </li>

                        <security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_SUPER_USER', 'ROLE_USER')"
                                            var="isUSer"/>

                        <c:if test="${not isUSer}">
                            <li><a href="<c:url value="/login"/>">Sign in</a></li>
                        </c:if>
                        <c:if test="${isUSer}">
                            <security:authentication property="principal.username" var="name"/>
                            <li>
                                <a href="<c:url value="/j_spring_security_logout"/>">Sign out</a>
                                <ul class="submenu">
                                    <li><a href="<c:url value="/userModel/edit"/>">Profile</a></li>
                                </ul>
                            </li>
                        </c:if>
                    </ul>
                </div>
                <!-- /.navbar-collapse -->
            </div>
            <!-- /.container -->
        </nav>
        <jsp:doBody/>
    </div>
    <!-- Footer -->
    <footer>
        <div class="container">
            <div class="row">
                <div class="col-lg-offset-2 col-md-offset-1">
                    <ul class="list-inline text-center">
                        <li>
                            <a class="footer-buttons social-btns"
                               href="https://www.facebook.com/profile.php?id=100012197762749">
                                <span class="fa-stack fa-lg">
                                    <i class="fa fa-circle fa-stack-2x"></i>
                                    <i class="fa fa-facebook fa-stack-1x fa-inverse"></i>
                                </span>
                            </a>
                        </li>
                        <li>
                            <a class="footer-buttons social-btns" href="https://www.linkedin.com/in/borisazanov">
                                <span class="fa-stack fa-lg">
                                    <i class="fa fa-circle fa-stack-2x"></i>
                                    <i class="fa fa-linkedin fa-stack-1x fa-inverse"></i>
                                </span>
                            </a>
                        </li>
                        <li>
                            <a class="footer-buttons social-btns" href="https://vk.com/boris_azanov">
                                <span class="fa-stack fa-lg">
                                    <i class="fa fa-circle fa-stack-2x"></i>
                                    <i class="fa fa-vk fa-stack-1x fa-inverse"></i>
                                </span>
                            </a>
                        </li>
                        <li>
                            <a class="footer-buttons social-btns" href="https://github.com/hardmet">
                                <span class="fa-stack fa-lg">
                                    <i class="fa fa-circle fa-stack-2x"></i>
                                    <i class="fa fa-github fa-stack-1x fa-inverse"></i>
                                </span>
                            </a>
                        </li>
                    </ul>
                    <p class="copyright">Copyright &copy; Breath of freedom 2016
                        <br>
                        Images - <a class="footer-buttons" href="https://vk.com/id10052664">Dmitry Azanov</a>
                    </p>
                </div>
            </div>
        </div>
    </footer>
</div>

</body>

</html>