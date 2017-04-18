package co.base.androidbaseapplication;

import android.app.Application;
import android.content.Context;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.squareup.leakcanary.LeakCanary;

import co.base.androidbaseapplication.injection.component.ApplicationComponent;
import co.base.androidbaseapplication.injection.component.DaggerApplicationComponent;
import co.base.androidbaseapplication.injection.module.ApplicationModule;
import co.base.androidbaseapplication.services.SyncService;
import timber.log.Timber;

public class AndroidBaseApplication extends Application
{

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate ()
    {
        super.onCreate( );

        if ( BuildConfig.DEBUG )
        {
            Timber.plant( new Timber.DebugTree( ) );
            LeakCanary.install( this );
        }

    }

    public static AndroidBaseApplication get (Context context)
    {
        return ( AndroidBaseApplication ) context.getApplicationContext( );
    }

    public ApplicationComponent getComponent ()
    {
        if ( mApplicationComponent == null )
        {
            mApplicationComponent = DaggerApplicationComponent.builder( )
                    .applicationModule( new ApplicationModule( this ) )
                    .build( );
        }
        return mApplicationComponent;
    }
}
