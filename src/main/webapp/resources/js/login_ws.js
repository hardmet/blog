var stompClient = null;
var idCurrentSession = null;
$(document).ready(function () {
    connect();
    idCurrentSession = $("#session")[0].innerHTML;
});

function setConnected(connected) {
    $("#signUpButton").prop("disabled", !connected);
}

function connect() {
    var socket = new SockJS('/blog-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        console.log('current session is ' + idCurrentSession);
        stompClient.subscribe('/service/registration/result/' + idCurrentSession, function (response) {
            console.log(response.body);
            resultOfRegistration(response.body);
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

function sendUser() {
    stompClient.send("/app/check/" + idCurrentSession, {}, JSON.stringify({
        'email': $("#regEmail").val(),
        'password': $("#regPass").val(),
        'nickName': $("#regNick").val(),
        'firstName': $("#regFirst").val(),
        'lastName': $("#regLast").val(),
        'birthday': $("#regBirthday").val()
    }));
}

function resultOfRegistration(status) {
    if (status == "\"OK\"") {
        $("#signUp").prepend("" +
            "<h3 id='resultRegistration'> " + 'Registration finished! You can sign in using form above!' + '&uarr;' +
            "</h3>");
        $("#resultRegistration").css({'color': 'green', "display": "none"}).animate({opacity: "show"}, "slow");
    } else if (status == "\"CONFLICT\"") {
        $("#signUp").prepend("" +
            "<h3 id='resultRegistration'> " + 'User with this email or nick name is already exists! ' +
            'Try another email or sign in!' + '&uarr;' +
            "</h3>");
        $("#resultRegistration").css({'color': 'red', "display": "none"}).animate({opacity: "show"}, "slow");
    } else {
        $("#signUp").prepend("" +
            "<h3 id='resultRegistration'> " + 'Something going wrong try again later or use contact form to contact administration' +
            "</h3>");
        $("#resultRegistration").css({'color': 'red', "display": "none"}).animate({opacity: "show"}, "slow");
    }
}

$(function () {
    $("#signUp").on('submit', function (e) {
        e.preventDefault();
    });
    $("#signUpButton").click(function () {
        sendUser();
    });
});