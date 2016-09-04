<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:template>
    <jsp:attribute name="title">About me</jsp:attribute>

    <jsp:body>
        <header class="intro-header">
            <img class="header-image" src="<c:url value="/resources/img/bio-header.jpg"/>" height="300px">
        </header>

        <div class="titles">
            <h1>Обо мне</h1>
            <h2 class="subheading">и об этом блоге.</h2>
        </div>
        <!-- Post Content -->
        <div class="container">
            <div class="row">
                    <div class="main-content">
                        <span>Всем привет! В первую очередь, рад приветствовать всех на
                            своем личном блоге, который я решил назвать вдохновляюще - Дыхание свободы.
                            Меня зовут Борис и на данный момент мне 25 лет. В свое время я закончил
                            бакалавриат химического факультета Уральского Федерального Университета в Екатеринбурге.
                            Скажу сразу: нет, я не сварю вам метамфетамин или ЛСД. В данный момент
                            я активно постигаю программирование и компьютерные технологии.
                            <%--Hello! At first I want to welcome you to my blog named "Get Inspiration"! My name is Boris and I'm 25 years old.--%>
                            <%--I have bachelors degree of chemistry from Ural State University at Yekaterinburg city (now Ural Federal University)--%>
                            <%--now I'm studying computer technologies and informatics. Live in St.Petersburg.--%>
                            <br>
                            <br>
                            Также, у меня есть различные хобби: люблю ездить на велосипеде, путешествовать,
                            бывать в новых местах, встречать знакомиться с различными людьми и делиться
                            с ними своими идеями. Может быть вы уйдете после прочтения того,
                            что написано здесь, может быть и нет, главная цель для меня, поделиться с
                            этим миром своими мыслями и планами, а также я открыл эту площадку для тестирования
                            своих разработок.
                            <%--Also, I have similar hobbies: travelling, riding on bike, something adventures.--%>
                            <%--I like to discover the world and I have some interesting (at least I think so) thoughts.--%>
                            <%--Maybe you will go away after reading it maybe not, my main hope is you will get some inspiration here,--%>
                            <%--just a little bit or maybe more :)--%>
                            <br>
                            <br>
                            Этот блог - это коллекция прогрессивного мышления, идей, прекрасных мест, и стиля жизни.
                            Большая часть изображений, опубликованных сделана моим братом -
                            <a href="https://vk.com/id10052664">Дмитрий Азанов</a>.
                            Буду рад если кому-то этот блог покажется интересным, либо же просто поднимет настроение :)
                            <%--This blog is collection progressive mind, wonderful places and lifestyle.--%>
                            <%--Are you ready for it?! It doesn't matter because we are start!--%>
                        </span>
                    </div>
                </div>
        </div>
        <hr>
    </jsp:body>

</page:template>