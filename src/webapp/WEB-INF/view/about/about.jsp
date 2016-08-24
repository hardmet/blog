<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:template>
    <jsp:attribute name="title">About author</jsp:attribute>

    <jsp:body>
        <header class="intro-header">
            <img class="header-image" src="<c:url value="/resources/img/bio-header.jpg"/>" height="300px">
        </header>

        <div class="titles">
            <h1>About author</h1>
            <h2 class="subheading">Little story about me...</h2>
        </div>
        <!-- Post Content -->
        <div class="container">
            <div class="row">
                    <div class="main-content">
                        <span>Hello! At first I want to welcome you to my blog named "Get Inspiration"! My name is Boris and I'm 25 years old.
                            I have bachelors degree of chemistry from Ural State University at Yekaterinburg city (now Ural Federal University)
                            now I'm studying computer technologies and informatics. Live in St.Petersburg.
                            <br>
                            <br>
                            Also, I have similar hobbies: travelling, riding on bike, something adventures.
                            I like to discover the world and I have some interesting (at least I think so) thoughts.
                            Maybe you will go away after reading it maybe not, my main hope is you will get some inspiration here,
                            just a little bit or maybe more :)
                            <br>
                            <br>
                            This blog is collection progressive mind, wonderful places and lifestyle.
                            Are you ready for it?! It doesn't matter because we are start!</span>
                    </div>
                </div>
        </div>
        <hr>
    </jsp:body>

</page:template>