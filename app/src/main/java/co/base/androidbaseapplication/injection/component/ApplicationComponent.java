package co.base.androidbaseapplication.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import co.base.androidbaseapplication.events.EventPosterHelper;
import co.base.androidbaseapplication.model.repository.CountryRepository;
import co.base.androidbaseapplication.services.SyncService;
import co.base.androidbaseapplication.util.PreferencesUtil;
import co.base.androidbaseapplication.injection.ApplicationContext;
import co.base.androidbaseapplication.injection.module.ApplicationModule;
import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext Context context();
    Application application();
    @Named("remote_repository") CountryRepository restDataRepository();
    @Named("local_repository") CountryRepository localDataRepository();
    PreferencesUtil preferencesHelper();
    EventPosterHelper eventPosterHelper();

}
