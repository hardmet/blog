var E = {
    stompClient : null,

    initEditorPost : function () {

        var area = $('.js-post-content');

        var imageForm = area.find('.js-main-image');
        S.initUploadImagePanel(area, imageForm);

        imageForm = area.find('.js-content-images');
        S.initUploadImagePanel(area, imageForm);

        var image = area.find(".js-remove-image");
        image.click(function () {
            var imageId = image.attr("data-id");
            E.removeImage($(this), "postContent", imageId);
        });

        var mainImage = area.find(".js-remove-main-image");
        mainImage.click(function () {
            var postId = area.find(".js-post-form").attr("data-id");
            E.removeImage($(this), "postMain", postId);
        });

        var initPublished = function () {
            $(".js-actual-published").datepicker({
                dateFormat: 'yy-mm-dd',
                altField: ".js-published",
                altFormat: "yy-mm-dd"
            });
        };
        initPublished();
    },

    initEditorProfile : function () {
        var area = $(".js-author-content");

        var initAuthorForm = function (form) {
            var startNick = form.find(".js-nick").val();
            var pass = form.find(".js-password");
            var confirmation = form.find(".js-confirm");

            var validation = function() {
                var validateMes = form.find(".js-pass-validation");
                var passwordValue = pass.val();

                if (passwordValue.length < 6) {
                    validateMes.text("Пароль должен состоять не менее чем из 6 знаков!");
                    validateMes.fadeIn('slow');
                    pass.addClass("error-field");
                    confirmation.removeClass("error-field");
                    return false;
                } else if (passwordValue.length > 50) {
                    validateMes.text("Пароль должен состоять не более чем из 50 знаков!");
                    validateMes.fadeIn('slow');
                    pass.addClass("error-field");
                    confirmation.removeClass("error-field");
                    return false;
                }
                if (passwordValue != confirmation.val()) {
                    validateMes.text("Пароли должны совпадать!");
                    validateMes.fadeIn('slow');
                    pass.addClass("error-field");
                    confirmation.addClass("error-field");
                    return false;
                }
                validateMes.fadeOut('slow');
                pass.removeClass("error-field");
                confirmation.removeClass("error-field");
                return true;
            };

            var confirmationBlock = form.find(".js-password-confirmation");

            form.find(".js-change-pass").on("change paste keyup", function () {
               if (pass.val().length > 0) {
                   confirmationBlock.fadeIn("slow");
                   validation();
               } else {
                   confirmationBlock.fadeOut("slow");
                   pass.removeClass("error-field");
                   confirmation.removeClass("error-field");
                   form.find(".js-pass-validation").fadeOut("slow");
               }
            });

            var nickValidation = function() {
                var nickName = form.find(".js-nick").val();
                var nickValidation = form.find(".js-nick-validation");
                var isValid = true;
                if (nickName.length < 2) {
                    nickValidation.text("Ник должен состоять не менее чем из 2 знаков!");
                    nickValidation.fadeIn("slow");
                    form.find(".js-nick").addClass("error-field");
                    return false;
                }

                var success = function (response) {
                    isValid = !response;
                    if (!isValid) {
                        nickValidation.text("Данный ник уже занят!");
                        nickValidation.fadeIn("slow");
                        form.find(".js-nick").addClass("error-field");
                        return false;
                    } else {
                        nickValidation.fadeOut("slow");
                        form.find(".js-nick").removeClass("error-field");
                        return true;
                    }
                };
                var error = function () {
                    isValid = false;
                };

                if (startNick != nickName) {
                    var formData = new FormData();
                    formData.append("nickName", nickName);
                    S.sendAbstractAjax("/author/ajax/check/nick", "POST", formData, null, null, success, error);
                }

                if (isValid) {
                    form.find(".js-nick").removeClass("error-field");
                    nickValidation.fadeOut("slow");
                    return true;
                }
            };

            form.find(".js-nick").on("paste keyup", function () {
                nickValidation();
            });

            var resultSpan = area.find('.js-results');
            var message = area.find(".js-message");

            var showError = function (text) {
                resultSpan.fadeIn('slow');
                message.text(text).css('color', '#D91842');
            };

            var success = function (messageResponse) {
                if (messageResponse.status == "OK") {
                    resultSpan.fadeIn('slow');
                    message.text(messageResponse.message).css('color', 'rgb(41, 13, 0)');

                    setTimeout(function() {
                        resultSpan.fadeOut('slow');
                    }, 5000);
                } else {
                    showError(messageResponse.message);
                }
            };

            var error = function (xhr, status, errorThrown) {
                showError('update failed with status: ' + status + ". "
                    + errorThrown);
            };

            var saveUserButton = form.find(".js-saveAuthor");
            saveUserButton.click(function(e) {
                e.preventDefault();
            });
            saveUserButton.click(function() {
                var isValid = true;
                if (pass.val().length > 0) {
                    isValid = validation();
                }
                if (isValid) {
                    isValid = nickValidation();
                }
                if (isValid) {
                    var userModel = {
                        id: form.attr("data-id"),
                        firstName: form.find(".js-first-name").val(),
                        lastName: form.find(".js-last-name").val(),
                        nickName: form.find(".js-nick").val(),
                        birthday: form.find(".js-birthday").val(),
                        email: form.find(".js-email").val(),
                        password: form.find(".js-password").val(),
                        enabled: "true"
                    };
                    pass.val("");
                    confirmation.val("");
                    confirmationBlock.fadeOut("slow");
                    var headers = { 'Accept': 'application/json',
                              'Content-Type': 'application/json' };
                    S.sendAbstractAjax("/author/save", "POST", JSON.stringify(userModel), headers, null, success, error);
                }
            });
        };

        var initBirthdayPicker = function () {
            area.find(".js-actual-birthday").datepicker({
                dateFormat: 'dd-MM-yy',
                altField: ".js-birthday",
                altFormat: "yy-mm-dd"
            });
        };

        initBirthdayPicker();

        var form = area.find(".js-editAuthorForm");
        if (form.length) {
            initAuthorForm(form);
        }
    },

    uploadMultipleImages : function(imageForm, imageInput) {
        var inputFile = imageForm.find('.js-upload-image-input')[0];
        var files = inputFile.files;
        var fileNames = "";

        var formData = new FormData();
        formData.append('type', imageForm.find('.js-upload-image-input').attr("data-type"));

        for (var i = 0; i < files.length; i++) {
            var file = files[i];

            if (!file.type.match('image/*')) {
                continue;
            }
            formData.append('uploadImages', file);

            if (fileNames != "") {
                fileNames = fileNames + ", " + file.name;
            } else {
                fileNames = file.name;
            }
        }
        imageInput.val(fileNames);
        return formData;
    },

    removeImage : function (image, type, entityId) {
        var postBlock = image.parents('.js-image-block');
        var formData = new FormData();
        formData.append("type", type);
        formData.append("entityId", entityId);

        var success = function (result) {
            if (result) {
                postBlock.fadeOut('slow');
                postBlock.remove();
            } else {
                alert("something going wrong! remove rejected!");
            }
        };

        var error = function (xhr, status, errorThrown) {
            alert('update failed with status: ' + status + ". "
                + errorThrown);
        };
        S.sendAbstractAjax("/remove/image", "POST", formData, null, null, success, error);
    }
};