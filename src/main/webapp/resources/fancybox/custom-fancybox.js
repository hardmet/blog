/**
 * this script using for customization of fancybox
 */
$(document).ready(function () {
    $(".fancybox-thumb").fancybox({
        prevEffect: 'none',
        nextEffect: 'none',
        helpers: {
            title: {
                type: 'outside'
            },
            thumbs: {
                width: 100,
                height: 100
            },
            overlay: {
                locked: true,
                showEarly: false
            }
        }
    });
});