package co.base.domain.executor;

import java.util.concurrent.Executor;

import io.reactivex.Scheduler;

/**
 * Executor implementation can be based on different frameworks or techniques of asynchronous
 * execution, but every implementation will execute the
 * {@link co.base.domain.Usecase} out of the UI thread.
 */
public interface ThreadExecutor
{
    Scheduler getScheduler ();
}
