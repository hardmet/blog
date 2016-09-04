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
            $( this ).parent().css( 'background','#FFF7CC' );
        },
        function(){
            $( this ).parent().css( 'background','#EFEDFA' );
        }
    );

});