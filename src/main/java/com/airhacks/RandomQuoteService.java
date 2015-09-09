package com.airhacks;

import rx.Observable;

public class RandomQuoteService {

    public Observable<String> getQuote() {
        return Observable.create(subscriber -> subscriber.onNext("Some quote"));
    }
}
