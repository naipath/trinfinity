package com.airhacks;

import rx.Observable;

public class RandomQuoteService {

    public static final String quoteService = "http://api.theysaidso.com/qod.json";

    public Observable<String> getQuote() {
        return Observable.create(subscriber -> subscriber.onNext("Some quote"));
    }

    public Observable<String> getQuoteOfTheDay() {
        return Observable.create(subscriber -> subscriber.onNext("Some quote"));
    }
}
