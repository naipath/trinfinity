var ws = new WebSocket('ws://localhost:8080/trinfinity/actions');
//var ws = new WebSocket('ws://192.168.1.199:8080/trinfinity/actions');

var someObservable = Rx.Observable.fromEvent(ws, 'message')
    .map(function (evt) {
        return JSON.parse(evt.data);
    });

var handleCoordinate = someObservable.filter(function (data) {
    return data.type === 'COORDINATE';
});

var newTurn = someObservable.filter(function (data) {
    return data.type === 'NEWTURN';
});

var gameEnding = someObservable.filter(function (data) {
    return data.type === 'ENDING';
});

var resetGame = someObservable.filter(function (data) {
    return data.type === 'RESET';
});

var expandGame = someObservable.filter(function (data) {
    return data.type === 'EXPAND';
});

gameEnding.subscribe(function (data) {
    if (data.name === name) {
        alert('Game has ended, YOU have won.');
    } else {
        alert('Game has ended, human ' + data.name + ' has won.');
    }
    resetTiles();
});

newTurn.subscribe(function (data) {
    if (data.name === name) {
        alert('Your Turn!');
    } else {
        alert( data.name + '\'s turn.');
    }
});

handleCoordinate.subscribe(function (data) {
    console.log(data);
    $('tr:eq(' + data.x + ') td:eq(' + data.y + ')').css('background', data.hexColor);
});

resetGame.subscribe(function (data) {
    resetTiles();
});

expandGame.subscribe(function (data) {
    addColumnAfter();
    addRow();
});

var resetTiles = function () {
    $('td').css('background', 'white');
};

function addRow() {
    var clone = $('table').find('tr:last').clone().css('display', 'table-row');
    $('tr:last').before(clone);
}

function addColumnAfter() {
    $('table').find('tr').each(function(){
        $(this).find('td:last').eq(0).after('<td></td>');
    });
}
function addColumnBefore() {
    $('table').find('tr').each(function(){
        $(this).find('td:first').eq(0).before('<td></td>');
    });
}
