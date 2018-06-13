package co.base.domain;

import co.base.domain.executor.PostExecutionThread;
import co.base.domain.executor.ThreadExecutor;
import io.reactivex.Observable;

public abstract class Usecase<T>
{
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    public Usecase (ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread)
    {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    public abstract Observable<T> buildObservable ();

    public Observable<T> execute ()
    {
        return buildObservable( ).observeOn( postExecutionThread.getScheduler( ) )
                .subscribeOn( threadExecutor.getScheduler( ) );
    }
}
