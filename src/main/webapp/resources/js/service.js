$(document).ready(function () {

    $("#signin").validate({

        rules: {

            inputEmail: {
                required: true,
                minlength: 7,
                maxlength: 30
            },

            inputPassword: {
                required: true,
                minlength: 6,
                maxlength: 50
            }
        }

    });

    $(".post-preview > a").hover(
        function () {
            $(this).parent().css('background', '#FFF7CC');
        },
        function () {
            $(this).parent().css('background', '#EFEDFA');
        }
    );

});

var S = {

    initUploadImagePanel : function (area, form) {
        var imageInput = form.find(".js-file-name");

        function initFormData() {
            var formData = E.uploadMultipleImages(form, imageInput);
            var postId = area.find('.js-post-form').attr("data-id");
            formData.append('id', postId);
            return formData;
        }

        var resultRequest = form.find(".js-result-block p");

        var button = form.find(".js-submit");
        button.click(function () {
            var formData = initFormData();
            S.sendImageAjax("/uploadImage", formData, resultRequest);
        });
        form.on('submit', function (e) {
            e.preventDefault();
        });
    },

    progressHandlingFunction : function (e) {
        $('.js-progress').attr({value: e.loaded, max: e.total});
    },

    sendImageAjax : function (url, object, resultBlock) {

        function xhr() {
            var myXhr = $.ajaxSettings.xhr();
            if (myXhr.upload) {
                myXhr.upload.addEventListener("progress", S.progressHandlingFunction, false);
            }
            return myXhr;
        }
        function success(data) {
            if (data === []) {
                resultBlock.text("Something going wrong! Try another").css('color', '#D91842');
            } else {
                for (var i = 0; i < data.length; i++) {
                    if (~data[i].indexOf("failed")) {
                        resultBlock.text("Error from server: " + data[i]).css('color', '#D91842');
                    } else {
                        resultBlock.text(data[i]).css('color', '#56F545');
                    }
                }
            }
        }
        function error(data) {
            for (var i = 0; i < data.length; i++) {
                resultBlock.text("Error in AJAX: " + data[i]).css('color', '#D91842');
            }
        }

        S.sendAbstractAjax(url, "POST", object, null, xhr, success, error);
    },

    sendAbstractAjax : function (url, type, object, headers, xhr, success, error) {

        if (xhr != null) {
            $.ajax({
                url: url,
                type: type,
                data: object,
                xhr: xhr,
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                cache: false,
                success: success,
                error: error
            });
        } else if (headers != null) {
            $.ajax({
                url: url,
                type: type,
                data: object,
                headers: headers,
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                cache: false,
                success: success,
                error: error
            });
        } else {
            $.ajax({
                url: url,
                type: type,
                data: object,
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                cache: false,
                success: success,
                error: error
            });
        }
    }
};