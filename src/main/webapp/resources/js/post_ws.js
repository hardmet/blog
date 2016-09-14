var stompClient = null;
var postId = null;
var currentUser = null;
$(document).ready(function () {
    connect();

    $(".comment-content .delete-content").click(function () {
        var commentId = $(this).parents(".comment-content")[0].id.substring(8);
        deleteComment(commentId);
        $(this).parents(".comment-content").animate({opacity: "hide"}, "slow");
    });

    postId = $("#postId")[0].innerHTML;
    currentUser = $("#currentSessionUser")[0].innerHTML;
});

function setConnected(connected) {
    $("#send").prop("disabled", !connected);
    if (connected) {
        $("#comment_text_area").show();
    }
    else {
        $("#comment_text_area").hide();
    }
}

function connect() {
    var socket = new SockJS('/blog-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/service/add/'+postId, function (response) {
            var comment = JSON.parse(response.body);
            addComment(comment.id, comment.author, comment.date, comment.text, comment.post);
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendComment() {
    stompClient.send("/app/commentToPost/"+ postId, {}, JSON.stringify({
        'author': currentUser,
        'text': $("#comment_text_area").val(),
        'post': postId }));
}

function addComment(id, author, date, text, post) {
    if (post == postId) {
        var postDate = new Date(date);
        var dateOptions = {year: 'numeric', month: 'long', day: 'numeric'};
        var commentId = "comment_" + id;
        $("#comments").append("" +
            "<div class='comment-content' id='" + commentId + "'> " +
                "<span class='comment-head'><a href='#'>" + author + "</a> " +
                        "<c:if test='${not isUniqUer}'>"+
                            "<script>"+
                                "$('.delete-content').css('display','none'); " +
                            "</script>" +
                        "</c:if>"+
                    "<span class='fa fa-close delete-content' title='удалить комментарий'></span>"+
                    "<span class='comment-time'> " +
                        postDate.toLocaleDateString('en-US', dateOptions) +
                    "</span> " +
                "</span> " +
                "<span>" + text + "</span> " +
            "</div>");
        var comment = $("#"+commentId);
        comment.css("display","none").animate({opacity: "show"}, "slow");
    }
}

function deleteComment(commentId) {
    stompClient.send("/app/commentToDelete/"+ commentId, {}, JSON.stringify({
        'id': commentId }));
    $(this).parents(".comment-content").animate({opacity: "hide"}, "slow");
    var subscription = stompClient.subscribe('/comment/delete/' + commentId, function (result) {
        if (result) {
            subscription.unsubscribe();
        } else {
            alert ('comment didn\'t deleted something going wrong!' );
        }
    });
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#send").click(function () {
        var textComment = $("#comment_text_area");
        if (textComment.val() != '') {
            sendComment();
            textComment.val('');
        }
    });
});