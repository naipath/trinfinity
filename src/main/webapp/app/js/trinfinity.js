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
        var message = {
            type: 'SIGNUP',
            username: $('#username').val()
        };
        ws.send(JSON.stringify(message));
        $('#signup').remove();
    });