package com.airhacks;

import rx.Observable;

public class BordLogicService {

    public Observable<String> processMove(String json) {
        return Observable.create(subscriber -> subscriber.onNext(json));
    }
}
