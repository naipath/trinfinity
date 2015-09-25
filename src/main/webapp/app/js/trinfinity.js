var observable = Rx.Observable.fromEvent($('table'), 'click');
observable
    .map(function (evt) {
        return evt.target.id
    })
    .subscribeOnNext(function (id) {
        ws.send(id);
    });