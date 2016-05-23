package co.base.androidbaseapplication.domain;

import rx.Observable;

public abstract class Usecase<T> {

    public abstract Observable<T> buildObservable();

    public Observable<T> execute () {
        return buildObservable();
    }
}
