Rx.Observable.fromEvent($('table'), 'click')
    .map(function (evt) {
        var col = $(evt.target).index();
        var row = $(evt.target).parent().index();
        return {type: 'COORDINATE',x: row, y: col};
    })
    .subscribeOnNext(function (message) {
        ws.send(JSON.stringify(message));
    });

var name;

Rx.Observable.fromEvent($('button'), 'click')
    .subscribeOnNext(function (event) {
        name = $('#name').val();
        var message = {
            type: 'SIGNUP',
            name: name
        };
        ws.send(JSON.stringify(message));
        $('.panel').remove();
        $('#overlay').remove();
    });

$('#draggable').draggable({
    handle: "ul"
});