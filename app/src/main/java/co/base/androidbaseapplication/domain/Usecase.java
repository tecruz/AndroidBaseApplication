package co.base.androidbaseapplication.domain;

import co.base.androidbaseapplication.data.Policies;
import io.reactivex.Observable;

public abstract class Usecase<T>
{
    protected Policies policy = new Policies( Policies.DATABASE );

    public abstract Observable<T> buildObservable ();

    public Observable<T> execute ()
    {
        return buildObservable( );
    }

    public Usecase<T> withPolicy (Policies policy)
    {
        this.policy = policy;
        return this;
    }
}
