package co.base.androidbaseapplication.di.module;

import android.app.Activity;
import android.content.Context;

import co.base.androidbaseapplication.di.ActivityContext;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule
{
    private Activity activity;

    public ActivityModule (Activity activity)
    {
        this.activity = activity;
    }

    @Provides
    Activity provideActivity ()
    {
        return activity;
    }

    @Provides
    @ActivityContext
    Context providesContext ()
    {
        return activity;
    }
}
