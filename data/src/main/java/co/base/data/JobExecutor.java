package co.base.data;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.base.domain.executor.ThreadExecutor;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Decorated {@link java.util.concurrent.ThreadPoolExecutor}
 */
@Singleton
public class JobExecutor implements ThreadExecutor
{
    @Inject
    JobExecutor ()
    { }

    @Override
    public Scheduler getScheduler ()
    {
        return Schedulers.io( );
    }
}
