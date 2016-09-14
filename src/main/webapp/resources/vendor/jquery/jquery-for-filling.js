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
    $("#signup").validate({

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
    $("#editForm").validate({

        rules: {

            regPass: {
                required: true,
                minlength: 6,
                maxlength: 50
            },
            regPassConfirm: {
                required: true,
                minlength: 6,
                maxlength: 50,
                equalTo: "#regPass"
            },
            regNick: {
                required: true,
                minlength: 5,
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

    $("#upload-file-input").on("change", uploadImage);

});

function uploadImage() {
    var fileSelect = document.getElementById('upload-file-input');
    var files = fileSelect.files;
    // Create a new FormData object.
    var formData = new FormData();

    // Loop through each of the selected files.
    for (var i = 0; i < files.length; i++) {
        var file = files[i];

        // Check the file type.
        if (!file.type.match('image/*')) {
            continue;
        }

        // Add the file to the request.
        formData.append('uploadImages', file);
    }
    var postId = document.getElementById('createdPostId').textContent;
    formData.append('directory',postId);
    // this is container for response from back-end
    var imgContainer = $('#imgContainer');
    $.ajax({
        url: "/uploadImage",
        type: "POST",
        data: formData,
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        xhr: function () {  // Custom XMLHttpRequest
            var myXhr = $.ajaxSettings.xhr();
            if (myXhr.upload) { // Check if upload property exists
                // For handling the progress of the upload
                myXhr.upload.addEventListener('progress', progressHandlingFunction, false);
            }
            return myXhr;
        },
        success: function (data) {
            // Handle upload success
            if (data === []) {
                $('#imgContainer').append('<p>Something going wrong! Try another</p>').css('color', '#EFEDFA');
            } else {
                for (var i = 0; i < data.length; i++) {
                    if (~data[i].indexOf("failed")) {
                        $('#imgContainer').append('<p>' + data[i] + '</p>').css('color', '#EFEDFA');
                    } else {
                        $('#imgContainer').append('<p>' + data[i] + '</p>').css('color', '#56F545');
                    }
                }
            }
        },
        error: function (data) {
            // Handle upload error
            $('#imgContainer').append('<p>' + data[i] + '</p>').css('color', '#EFEDFA');
        }
    });
} // function uploadImage