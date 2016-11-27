var token = null;
var header = null;
$(document).ready(function () {
    $(function () {
        $("#actualDate").datepicker({
            dateFormat: 'dd-MM-yy',
            altField: "#regBirthday",
            altFormat: "yy-mm-dd"
        });
    });
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
                required: false,
                minlength: 6,
                maxlength: 50
            },
            regPassConfirm: {
                required: false,
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

    token = $("meta[name='_csrf']").attr("content");
    header = $("meta[name='_csrf_header']").attr("content");

    $("#upload-file-input").on("change", uploadImage);
    var saveUserButton = $("#saveUser");
    saveUserButton.on('click', function(e) {
        e.preventDefault();
    });
    saveUserButton.on('click', saveUser);

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
        console.log(file);
        // Add the file to the request.
        formData.append('uploadImages', file);
    }
    var postId = document.getElementById('createdPostId').textContent;
    console.log(postId);
    formData.append('directory', postId);
    // this is container for response from back-end
    var imgContainer = $('#imgContainer');
    $.ajax({
        url: "/uploadImage",
        type: 'POST',
        data: formData,
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
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
            console.log(data);
            if (data === []) {
                $('#imgContainer').append('<p>Something going wrong! Try another</p>').css('color', '#D91842');
            } else {
                for (var i = 0; i < data.length; i++) {
                    if (~data[i].indexOf("failed")) {
                        $('#imgContainer').append('<p>Error from server:' + data[i] + '</p>').css('color', '#D91842');
                    } else {
                        $('#imgContainer').append('<p>' + data[i] + '</p>').css('color', '#56F545');
                    }
                }
            }
        },
        error: function (data) {
            console.log(data);
            // Handle upload error
            $('#imgContainer').append('<p>Error in AJAX:' + data[i] + '</p>').css('color', '#D91842');
        }
    });
} // function uploadImage
//progress handling function
function progressHandlingFunction(e) {
    if (e.lengthComputable) {
        $('progress').attr({value: e.loaded, max: e.total});
    }
}
function saveUser() {
    var userModel = {
        id: "",
        firstName: $("#regFirst").val(),
        lastName: $("#regLast").val(),
        nickName: $("#regNick").val(),
        birthday: $("#regBirthday").val(),
        email: $("#regEmail").val(),
        password: $("#regPass").val(),
        enabled: ""
    };
    $.ajax({
        type: 'POST',
        url: '/user/save',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(userModel),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            alert(data);
            alert(data.response);
            $('#results').append('<p>' + data.response + '</p>').css('color', '#56F545');
        },
        error: function (xhr, status, errorThrown) {
            $('#results').append('update failed with status: ' + status + ". "
                + errorThrown).css('color', '#D91842');
        }
    });
} // function editUser