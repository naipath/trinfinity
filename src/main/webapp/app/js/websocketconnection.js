var ws = new WebSocket("ws://172.27.2.169:8080/trinfinity/actions");
//var ws = new WebSocket("ws://localhost:8080/trinfinity/actions");

ws.onopen = function () {
};

ws.onmessage = function (evt) {
    console.log(evt);
};

ws.onclose = function () {};