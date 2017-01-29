var W = {
    stompClient : null,
    socket : null,

    initCommentService : function () {

        var area = $('.js-post-content');
        var postId = area.find('.js-post').attr('data-post-id');
        var currentUser = $(".js-commentator-email").attr('data-user-id');

        function setConnected(connected) {
            area.find('.js-send-comment').prop("disabled", !connected);
            var commentTextArea = area.find('.js-comment-text');
            if (connected) {
                commentTextArea.show();
            } else {
                commentTextArea.hide();
            }
        }

        function serverListener (response) {
            if (response != null) {
                var comment = JSON.parse(response.body);
                addComment(comment.id, comment.author.nickName, comment.published, comment.text, comment.post.id);
            } else {
                //TODO: handle errors
            }
        }

        W.connect('/service/add/' + postId, serverListener, setConnected);

        function makeCommentRemovable() {
            $(".comment-content .delete-content").click(function () {
                var parents = $(this).parents(".comment-content");
                var commentId = parents.find('.js-comment-id').val();
                deleteComment(commentId, parents);
            });
        }

        makeCommentRemovable();

        function sendComment() {
            var form = area.find(".js-comment-form");
            var comment = {
                'author': {
                    'email': form.attr("data-author")
                },
                'text': form.find(".js-comment-text-area").val(),
                'post': { 'id': postId }
            };
            W.sendAction("commentToPost/" + postId, comment);
        }

        function addComment(id, author, date, text, post) {
            if (post == postId) {

                var comment = $('.js-comment:hidden').clone();

                comment.find('.js-comment-id').val(id);

                var linkToAuthor = comment.find('.js-comment-a');
                linkToAuthor.text(author);
                linkToAuthor.attr("href", "/author/" + id);

                var deleteButton = comment.find('.js-comment-delete');
                if (!deleteButton.hasClass('delete-content')) {
                    deleteButton.addClass('delete-content');
                }

                var published = new Date(date);
                var dateOptions = {year: 'numeric', month: 'long', day: 'numeric'};
                comment.find('.js-comment-date').text(published.toLocaleDateString('en-US', dateOptions));

                comment.find('.js-comment-text').text(text);

                $("#comments").append(comment);
                comment.fadeIn('slow');

                $(".comment-content .delete-content").click(function () {
                    var parents = $(this).parents(".comment-content");
                    var commentId = parents.find('.js-comment-id').val();
                    deleteComment(commentId, parents);
                });
            }
        }

        function deleteComment(commentId, parents) {
            W.stompClient.send("/app/commentToDelete/" + commentId, {}, JSON.stringify({
                'id': commentId
            }));
            var subscription = W.stompClient.subscribe('/service/comment/delete/' + commentId, function (result) {
                if (result) {
                    parents.fadeOut('slow');
                    parents.remove();
                    subscription.unsubscribe();
                } else {
                    alert('comment didn\'t deleted something going wrong!');
                }
            });
        }

        function addCommentOnClick() {
            area.find('.js-send-comment').click(function () {
                var textComment = area.find('.js-comment-text-area');
                if (textComment.val() != '') {
                    sendComment();
                    textComment.val('');
                }
            });
            area.find('.js-comment-form').on('submit', function (e) {
                e.preventDefault();
            });
        }

        addCommentOnClick(sendComment);
    },

    initLoginService : function () {

        var area = $('.js-login-page');
        var idCurrentSession = area.find('.js-session').val();

        $(".js-actual-date").datepicker({
            dateFormat: 'dd-MM-yy',
            altField: ".js-reg-birthday",
            altFormat: "yy-mm-dd"
        });

        function resultOfRegistration(status, message) {
            var resultInfo = area.find('.js-result-registration');
            var color = '';
            if (status == "OK") {
                color = 'green';
            } else {
                color = 'red';
            }
            resultInfo.css({'color': color, "display": "none"});

            resultInfo.html(message);
            resultInfo.fadeIn('slow');
        }
        
        function serverListener (response) {
            resultOfRegistration(JSON.parse(response.body).status, JSON.parse(response.body).message);
        }

        function setConnected(connected) {
            area.find(".js-sign-up-button").prop("disabled", !connected);
        }

        W.connect('/service/registration/result/' + idCurrentSession, serverListener, setConnected);
        window.onbeforeunload = W.disconnect();

        function validation() {
            area.find(".js-sign-up-form").validate({

                rules: {

                    regEmail: {
                        required: true,
                        minlength: 7,
                        maxlength: 30
                    },

                    regPass: {
                        required: true,
                        minlength: 6,
                        maxlength: 50
                    },
                    regNick: {
                        required: true,
                        minlength: 5,
                        maxlength: 50
                    }
                }

            });
            return area.find(".js-sign-up-form").valid();
        }

        function sendUser() {
            var form = area.find('.js-sign-up-form');
            var newProfile = {
                'email': form.find('.js-reg-email').val(),
                'password': form.find('.js-reg-pass').val(),
                'nickName': form.find('.js-reg-nick').val(),
                'firstName': form.find('.js-reg-first').val(),
                'lastName': form.find('.js-reg-last').val(),
                'birthday': form.find('.js-reg-birthday').val(),
                'enabled' : true
            };
            if (validation()) {
                W.sendAction("check/" + idCurrentSession, newProfile);
            }
        }

        function addLoginOnClick () {
            area.find('.js-sign-up-button').click(function () {
                sendUser();
            });
            area.find('.js-sign-up-form').on('submit', function (e) {
                e.preventDefault();
            });
        }
        
        addLoginOnClick();

    },

    sendAction : function (url, object) {
        W.stompClient.send("/app/" + url, {}, JSON.stringify(object));
    },

    connect : function (url, listener, handler) {

        W.socket = new SockJS('/blog-websocket');
        W.stompClient = Stomp.over(W.socket);
        W.stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            W.stompClient.subscribe(url, listener);
            handler(true);
        }, function(message) {
            if (message) {
                W.disconnect();
            }

        });
    },

    disconnect : function (service) {
        if (W.stompClient != null && W.socket.readyState) {
            W.stompClient.disconnect();
        }
        if (service) {
            service();
        }
        console.log("Disconnected");
    }
};