package co.base.androidbaseapplication.di;

import android.app.Application;

import javax.inject.Singleton;

import co.base.androidbaseapplication.AndroidBaseApplication;
import co.base.androidbaseapplication.di.module.ApplicationModule;
import co.base.androidbaseapplication.di.module.BuildersModule;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ApplicationModule.class,
        BuildersModule.class})
public interface ApplicationComponent
{
    @Component.Builder
    interface Builder
    {
        @BindsInstance
        ApplicationComponent.Builder application (Application application);

        ApplicationComponent build ();
    }

    void inject (AndroidBaseApplication instance);
}
