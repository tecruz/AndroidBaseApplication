package co.base.androidbaseapplication.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import co.base.androidbaseapplication.domain.repository.CountryRepository;
import co.base.androidbaseapplication.events.EventEmitter;
import co.base.androidbaseapplication.services.SyncService;
import co.base.androidbaseapplication.ui.navigation.Navigator;
import co.base.androidbaseapplication.util.PreferencesUtil;
import co.base.androidbaseapplication.injection.ApplicationContext;
import co.base.androidbaseapplication.injection.module.ApplicationModule;
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
}
