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

var enterStream = Rx.Observable.fromEvent($('#name'), 'keyup') .filter(function(data){ return data.keyCode == 13});
var clickStream = Rx.Observable.fromEvent($('button'), 'click');

Rx.Observable.merge(enterStream, clickStream)
    .filter(function (data) {return $('#name').val();})
    .subscribeOnNext(function (event) {
        name = $('#name').val();
        ws.send(JSON.stringify({
            type: 'SIGNUP',
            name: name
        }));
        $('.panel').remove();
        $('#overlay').remove();
    });

$('#draggable').draggable({
    handle: "ul"
});