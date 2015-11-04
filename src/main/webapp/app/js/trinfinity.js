Rx.Observable.fromEvent($('table'), 'click')
    .map(function (evt) {
        return evt.target.id
    })
    .subscribeOnNext(function (id) {
        var message = {
            type: 'COORDINATE',
            coordinate: id
        };
        ws.send(JSON.stringify(message));
    });

Rx.Observable.fromEvent($('button'), 'click')
    .subscribeOnNext(function (event) {
        userName = $('#username').val();
        var message = {
            type: 'SIGNUP',
            username: userName
        };
        ws.send(JSON.stringify(message));
        $('.panel').remove();
        $('#overlay').remove();
    });


var userName;