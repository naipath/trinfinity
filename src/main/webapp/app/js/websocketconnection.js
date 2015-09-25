var ws = new WebSocket("ws://localhost:8080/trinfinity/actions");

Rx.Observable.fromEvent(ws, 'message')
    .map(function (evt) {
        return JSON.parse(evt.data);
    })
    .subscribeOnNext(function (data) {
        $('#' + data.coordinate).css("background", data.hexcolor);
    });