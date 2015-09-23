var ws = new WebSocket("ws://172.27.2.169:8080/trinfinity/actions");
//var ws = new WebSocket("ws://localhost:8080/trinfinity/actions");

Rx.Observable.fromEvent(ws, 'message').subscribeOnNext(function (evt) {
    $('#' + evt.data).css("background", "red");
});