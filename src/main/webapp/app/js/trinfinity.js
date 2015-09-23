var observable = Rx.Observable.fromEvent($('table'), 'click');
observable.subscribeOnNext(function(evt){
    $(evt.target).css( 'background', 'red');
    ws.send('HoiPipeloi!');
});
