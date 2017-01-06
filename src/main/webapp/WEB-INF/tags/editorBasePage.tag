<!DOCTYPE html>
<%@tag description="Template Site tag" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@attribute name="title" fragment="true" %>
<%@attribute name="link" fragment="true" %>
<%@attribute name="scrypt" fragment="true" %>
<html lang="ru">
<head>
    <title>
        <jsp:invoke fragment="title"/>
    </title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <security:csrfMetaTags />

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
    <link rel="stylesheet" href="<c:url value="/resources/css/jquery-ui.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/fix.css"/>">

    <jsp:invoke fragment="link"/>

    <!-- jQuery -->
    <%--<spring:url value="/webjars/jquery/3.1.0/jquery.min.js" var="jqueryjs"/>--%>
    <spring:url value="/resources/vendor/jquery/jquery.min.js" var="jquery"/>
    <script src="${jquery}"></script>

    <!-- This script needed for ajax post requests when csrf enabled -->
    <script type="text/javascript" src="<c:url value="/resources/js/initXHR.js"/>"></script>

    <!-- Bootstrap Core JavaScript -->
    <spring:url value="/resources/vendor/bootstrap/js/bootstrap.min.js" var="bootstrap"/>
    <script src="${bootstrap}"></script>

    <!-- Theme JavaScript -->
    <spring:url value="/resources/js/clean-blog.min.js" var="theme"/>
    <script src="${theme}"></script>

    <!-- jQuery Validate data -->
    <script src="<c:url value="/resources/vendor/jquery/jquery.validate.min.js"/>"></script>
    <!-- Scripts for sending forms -->
    <script src="<c:url value="/resources/vendor/jquery/jquery-ui.js"/>"></script>
    <spring:url value="/resources/js/service.js" var="validate"/>
    <script src="${validate}"></script>

    <link rel="stylesheet" href="<c:url value="/resources/fancybox/jquery.fancybox.css"/>" type="text/css"
          media="screen"/>
    <link rel="stylesheet" href="<c:url value="/resources/fancybox/helpers/jquery.fancybox-buttons.css"/>"
          type="text/css" media="screen"/>
    <link rel="stylesheet" href="<c:url value="/resources/fancybox/helpers/jquery.fancybox-thumbs.css"/>"
          type="text/css" media="screen"/>
    <script src="<c:url value="/webjars/sockjs-client/1.0.2/sockjs.js"/>"></script>
    <script src="<c:url value="/webjars/stomp-websocket/2.3.3/stomp.min.js"/>"></script>

    <script type="text/javascript" src="<c:url value="/resources/fancybox/jquery.fancybox.pack.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="/resources/fancybox/jquery.mousewheel-3.0.6.pack.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="/resources/fancybox/helpers/jquery.fancybox-buttons.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="/resources/fancybox/helpers/jquery.fancybox-media.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="/resources/fancybox/helpers/jquery.fancybox-thumbs.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/fancybox/custom-fancybox.js"/>"></script>

    <!-- Script editor -->
    <script src="<c:url value="/resources/js/editor.js" />"></script>

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
                        <span class="sr-only">Панель навигации</span>
                        Меню <i class="fa fa-bars"></i>
                    </button>

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
                    <ul class="nav navbar-nav">
                        <li><a href="<c:url value="/index"/>">Блог</a>
                        <li><a href="<c:url value="/projects"/>">Проекты</a>
                        <li><a href="<c:url value="/about"/>">О себе</a>
                        <li><a href="<c:url value="/contact"/>">Контакты</a>
                        <security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_SUPER_USER', 'ROLE_USER')"
                                            var="isUSer"/>

                        <c:if test="${not isUSer}">
                            <li><a href="<c:url value="/login"/>">Sign in</a></li>
                        </c:if>
                        <c:if test="${isUSer}">
                            <li>
                                <c:url var="logoutUrl" value="/j_spring_security_logout"/>
                                <form action="${logoutUrl}" method="post">
                                    <button type="submit">Выйти</button>
                                </form>
                                <ul class="submenu">
                                    <li><a href="<c:url value="/author/edit"/>">Профиль</a></li>
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
                               href="https://www.facebook.com/author.php?id=100012197762749"
                               target="_blank">
                                <span class="fa-stack fa-lg">
                                    <i class="fa fa-circle fa-stack-2x"></i>
                                    <i class="fa fa-facebook fa-stack-1x fa-inverse"></i>
                                </span>
                            </a>
                        </li>
                        <li>
                            <a class="footer-buttons social-btns" href="https://www.linkedin.com/in/borisazanov"
                               target="_blank">
                                <span class="fa-stack fa-lg">
                                    <i class="fa fa-circle fa-stack-2x"></i>
                                    <i class="fa fa-linkedin fa-stack-1x fa-inverse"></i>
                                </span>
                            </a>
                        </li>
                        <li>
                            <a class="footer-buttons social-btns" href="https://vk.com/boris_azanov"
                               target="_blank">
                                <span class="fa-stack fa-lg">
                                    <i class="fa fa-circle fa-stack-2x"></i>
                                    <i class="fa fa-vk fa-stack-1x fa-inverse"></i>
                                </span>
                            </a>
                        </li>
                        <li>
                            <a class="footer-buttons social-btns" href="https://github.com/hardmet"
                               target="_blank">
                                <span class="fa-stack fa-lg">
                                    <i class="fa fa-circle fa-stack-2x"></i>
                                    <i class="fa fa-github fa-stack-1x fa-inverse"></i>
                                </span>
                            </a>
                        </li>
                    </ul>
                    <p class="copyright">Copyright &copy; Breath of freedom 2016
                        <br>
                        Images - <a class="footer-buttons" href="https://vk.com/id10052664" target="_blank">Dmitry Azanov</a>,
                        <a class="footer-buttons" href="https://vk.com/rteg31" target="_blank">Egor Sergienko</a>
                        <br>
                    </p>
                </div>
            </div>
        </div>
    </footer>
</div>
</body>
<script>
    $(document).ready(function () {
        <jsp:invoke fragment="scrypt"/>
    });
</script>
</html>