package co.base.androidbaseapplication;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

import co.base.androidbaseapplication.di.component.ApplicationComponent;
import co.base.androidbaseapplication.di.component.DaggerApplicationComponent;
import co.base.androidbaseapplication.di.module.ApplicationModule;
import timber.log.Timber;

public class AndroidBaseApplication extends Application
{

    ApplicationComponent applicationComponent;

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
        if ( applicationComponent == null )
        {
            applicationComponent = DaggerApplicationComponent.builder( )
                    .applicationModule( new ApplicationModule( this ) )
                    .build( );
        }
        return applicationComponent;
    }
}
