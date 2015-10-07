var ws = new WebSocket('ws://localhost:8080/trinfinity/actions');
//var ws = new WebSocket('ws://192.168.1.199:8080/trinfinity/actions');

var someObservable = Rx.Observable.fromEvent(ws, 'message')
    .map(function (evt) {
        return JSON.parse(evt.data);
    });

var handleCoordinate = someObservable.filter(function (data) {
    return data.type === 'coordinate';
});

var gameEnding = someObservable.filter(function (data) {
    return data.type === 'ending';
});

var resetGame = someObservable.filter(function (data) {
    return data.type === 'reset';
});

gameEnding.subscribe(function (data) {
    alert('Game has ended');
    resetTiles();
});

handleCoordinate.subscribe(function (data) {
    console.log(data);
    $('#' + data.coordinate).css('background', data.hexColor);
});

resetGame.subscribe(function (data) {
    resetTiles();
});

var resetTiles = function () {
    $('td').css('background', 'white');
};