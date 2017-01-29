var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
(function(send) {

    XMLHttpRequest.prototype.send = function(data) {

        this.setRequestHeader(header, token);

        send.call(this, data);
    };

})(XMLHttpRequest.prototype.send);