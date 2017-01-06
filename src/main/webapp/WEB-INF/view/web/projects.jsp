<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:basePage>
    <jsp:attribute name="title">Проекты</jsp:attribute>
    <jsp:body>
        <!-- Page Header -->
        <!-- Set your background image for this header on the line below. -->
        <header class="intro-header">
            <img class="header-image"
                 src="${pageContext.request.contextPath}/resources/img/projects-header.jpg" alt="">
        </header>

        <div class="titles">
            <h1>Здесь можно отслеживать мои проекты</h1>
            <h2 class="subheading">Раздел будет переодически пополняться</h2>
        </div>
        <!-- Post Content -->
        <article>
            <div class="container">
                <div class="row">
                    <div class="main-content">
                        <p>Основных проектов, которыми я сейчас занимаюсь на данный момент два.</p>
                        <ul>
                            <li><b>Blog</b> - это данный сайт, на котором вы сейчас находитесь.</li>
                            <li><b>QuestaHome</b> - это андройд приложение, которое я сейчас разрабатываю.</li>
                        </ul>
                        <p>Ниже я попытаюсь коротоко и ясно описать: что, куда, как и зачем я это делаю.</p>
                        <hr>
                        <h3 class="section-heading">Blog</h3>
                        <p>Данный сайт задумывался изначально как площадка для экспериментов, некий своеобразный
                            полигон, для обкатки и тестирования моих веб-приложений. В последствии я решил, что можно
                            начать с написания небольшого блога.</p>
                        <p>Здесь я периодически выкладываю свои мысли,
                            впечатления, возможно буду писать небольшие гайды по различным технологиям. В свое время
                            я понял, что писать небольшие тексты, пусть и без особого смысла - это неплохая практика
                            развития навыков выражения своих мыслей в письменном виде. Поэтому данный проект реализует
                            очень важные и полезные для меня вещи. Проект будет жить, пока я буду видеть нужду в нем.
                        </p>
                        <hr>
                        <h3 class="section-heading">QuestaHome</h3>
                        <p>Идея разработки андройд-приложения пришла ко мне после прохождения интенсива для начинающих
                            по андройд разработке. Тогда я понял, что это довольно интересно и у меня есть все,
                            чтобы создать свое приложение.</p>
                        <p>Данное приложение представляет собой игру жанра
                            квест, в которой игроки
                        </p>
                        <div class="gallery">
                            <div style="margin: 0 auto; width: 542px;">
                                <a class="fancybox-thumb" rel="fancybox-thumb"
                                   href="${pageContext.request.contextPath}/resources/img/questaHome_login_scr.jpg"
                                   title="Экран входа в приложение">
                                    <img class="img-responsive"
                                         src="${pageContext.request.contextPath}/resources/img/questaHome_login_scr.jpg"
                                         alt="">
                                </a>
                                <a class="fancybox-thumb" rel="fancybox-thumb"
                                   href="${pageContext.request.contextPath}/resources/img/questaHome_main_menu.jpg"
                                   title="Экран главного меню">
                                    <img class="img-responsive"
                                         src="${pageContext.request.contextPath}/resources/img/questaHome_main_menu.jpg"
                                         alt="">
                                </a>
                                <a class="fancybox-thumb" rel="fancybox-thumb"
                                   href="${pageContext.request.contextPath}/resources/img/questaHome_userprofile.jpg"
                                   title="Панель профиля юзера">
                                    <img class="img-responsive"
                                         src="${pageContext.request.contextPath}/resources/img/questaHome_userprofile.jpg"
                                         alt="">
                                </a>
                            </div>
                        </div>
                        <span class="caption text-muted">Вот так выглядит приложение сейчас</span>
                        <p>Суть игры заключается в создании и прохождении квестов. Квест представляет собой
                            последовательность точек на карте, в которые игрок перемещается. В самих точках, игрок
                            получает некоторое задание (разгадать загадку, головоломку), и при успешном решении вводит
                            ключ (пароль) - к следующей точке.
                        </p>
                        <p>При успешном завершении квеста игрок получает очки, которые
                            может потратить для получения подсказок при выполнении заданий в других квестах.
                            Данное приложение должно поддерживать интерфейс добавления нового квеста любым игроком.</p>
                        <p>
                            Возможно, данная идея уже реализованна, но это не суть, основная моя цель в этом приложении
                            -
                            прокачать навыки андройд разработки и просто сделать своими руками первую игру, в которую
                            банально можно играть с друзьями, гуляя, и исследуя город.</p>
                    </div>
                </div>
            </div>
        </article>
    </jsp:body>

</page:basePage>