Rx.Observable.fromEvent($('table'), 'click')
    .map(function (evt) {
        var col = $(evt.target).index();
        var row = $(evt.target).parent().index();
        return {type: 'COORDINATE',x: row, y: col};
    })
    .subscribeOnNext(function (message) {
        ws.send(JSON.stringify(message));
    });

var userName;

Rx.Observable.fromEvent($('button'), 'click')
    .subscribeOnNext(function (event) {
        name = $('#username').val();
        var message = {
            type: 'SIGNUP',
            name: name
        };
        ws.send(JSON.stringify(message));
        $('.panel').remove();
        $('#overlay').remove();
    });

// Jquery methods to add rows and columns
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

function addAll() {
    addColumnBefore();
    addColumnAfter();
    addRow();
}