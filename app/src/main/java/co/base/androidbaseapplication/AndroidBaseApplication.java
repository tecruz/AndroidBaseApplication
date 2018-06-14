package co.base.androidbaseapplication;

import android.app.Activity;
import android.app.Application;
import android.app.Service;

import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import co.base.androidbaseapplication.di.DaggerApplicationComponent;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;
import timber.log.Timber;

public class AndroidBaseApplication extends Application implements HasActivityInjector, HasServiceInjector
{

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Inject
    DispatchingAndroidInjector<Service> serviceDispatchingAndroidInjector;

    @Override
    public void onCreate ()
    {
        super.onCreate( );

        if ( BuildConfig.DEBUG )
        {
            Timber.plant( new Timber.DebugTree( ) );
            LeakCanary.install( this );
        }

        DaggerApplicationComponent
                .builder( )
                .application( this )
                .build( )
                .inject( this );

    }

    @Override
    public AndroidInjector<Activity> activityInjector ()
    {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector ()
    {
        return serviceDispatchingAndroidInjector;
    }
}
