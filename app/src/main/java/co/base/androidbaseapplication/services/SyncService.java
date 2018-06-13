package co.base.androidbaseapplication.services;

import android.content.Context;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.util.List;

import javax.inject.Inject;

import co.base.androidbaseapplication.AndroidBaseApplication;
import co.base.androidbaseapplication.events.EventEmitter;
import co.base.androidbaseapplication.events.Events;
import co.base.androidbaseapplication.util.ErrorMessageFactory;
import co.base.androidbaseapplication.util.PreferencesUtil;

import co.base.data.exception.RetrofitException;
import co.base.domain.Country;
import co.base.domain.GetCountriesUsecase;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public class SyncService extends JobService
{
    @Inject
    GetCountriesUsecase getCountriesUsecase;

    @Inject
    PreferencesUtil preferencesUtil;

    @Inject
    EventEmitter eventEmitter;

    private CompositeDisposable disposable = new CompositeDisposable( );

    public static void startService (Context context)
    {
        FirebaseJobDispatcher dispatcher =
                new FirebaseJobDispatcher( new GooglePlayDriver( context ) );

        Job myJob = dispatcher.newJobBuilder( )
                // the JobService that will be called
                .setService( SyncService.class )
                // uniquely identifies the job
                .setTag( "sync-service-tag" )
                // one-off job
                .setRecurring( false )
                // persist past a device reboot
                .setLifetime( Lifetime.FOREVER )
                // don't overwrite an existing job with the same tag
                .setReplaceCurrent( true )
                // retry with exponential backoff
                .setRetryStrategy( RetryStrategy.DEFAULT_EXPONENTIAL )
                // constraints that need to be satisfied for the job to run
                .setConstraints(
                        Constraint.ON_ANY_NETWORK
                )
                //start job immediately
                .setTrigger( Trigger.executionWindow( 0, 0 ) )
                .build( );

        dispatcher.mustSchedule( myJob );
    }

    @Override
    public void onCreate ()
    {
        super.onCreate( );
        AndroidBaseApplication.get( this ).getComponent( ).inject( this );
    }

    @Override
    public boolean onStartJob (final JobParameters job)
    {
        Timber.i( "Starting sync..." );

        if ( disposable != null )
            disposable.clear( );

        disposable.add( getCountriesUsecase.execute( )
                .subscribeWith( new DisposableObserver<List<Country>>( )
                {
                    @Override
                    public void onComplete ()
                    {
                        Timber.i( "Synced successfully!" );
                        long syncTimeStamp = System.currentTimeMillis( );
                        preferencesUtil.setLastSyncTimestamp( syncTimeStamp );
                        eventEmitter.postEvent( new Events( Events.SYNC_COMPLETED ) );
                        jobFinished( job, false );
                    }

                    @Override
                    public void onError (Throwable e)
                    {
                        /*try {
                            error.getErrorBodyAs(ErrorResponse.class);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }*/
                        RetrofitException error = ( RetrofitException ) e;
                        Timber.i( ErrorMessageFactory.create( getApplicationContext( ),
                                error.getKind( ) ) );
                        eventEmitter.postEvent( new Events( Events.SYNC_ERROR ) );
                        jobFinished( job, false );
                    }

                    @Override
                    public void onNext (List<Country> countries)
                    {
                    }
                } ) );


        return true;
    }

    @Override
    public boolean onStopJob (JobParameters job)
    {
        return false;
    }

    @Override
    public void onDestroy ()
    {
        if ( disposable != null ) disposable.clear( );
        super.onDestroy( );
    }
}