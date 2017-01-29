/**
 * Created by boris_azanov on 25.12.16.
 */
$(function () {
    $(".js-actual-date").datepicker({
        dateFormat: 'dd-MM-yy',
        altField: ".js-reg-birthday",
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