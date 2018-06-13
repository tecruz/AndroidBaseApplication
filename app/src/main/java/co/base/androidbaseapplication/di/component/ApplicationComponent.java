package co.base.androidbaseapplication.di.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import co.base.androidbaseapplication.events.EventEmitter;
import co.base.androidbaseapplication.services.SyncService;
import co.base.androidbaseapplication.ui.navigation.Navigator;
import co.base.androidbaseapplication.util.PreferencesUtil;
import co.base.androidbaseapplication.di.ApplicationContext;
import co.base.androidbaseapplication.di.module.ApplicationModule;
import co.base.domain.executor.PostExecutionThread;
import co.base.domain.executor.ThreadExecutor;
import co.base.domain.repository.CountryRepository;
import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent
{
    void inject (SyncService syncService);

    @ApplicationContext
    Context context ();

    Application application ();

    CountryRepository countryRepository ();

    Navigator navigator ();

    PreferencesUtil preferencesHelper ();

    EventEmitter eventEmitterHelper ();

    ThreadExecutor threadExecutor ();

    PostExecutionThread postExecutionThread ();
}
