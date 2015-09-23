var observable = Rx.Observable.fromEvent($('table'), 'click');
observable.subscribeOnNext(function(evt){
    ws.send(evt.target.id);
});