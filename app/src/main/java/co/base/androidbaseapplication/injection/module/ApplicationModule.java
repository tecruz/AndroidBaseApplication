package co.base.androidbaseapplication.injection.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import co.base.androidbaseapplication.injection.ApplicationContext;
import co.base.androidbaseapplication.model.local.CountryDataStore;
import co.base.androidbaseapplication.model.repository.CountryRepository;
import co.base.androidbaseapplication.model.rest.RestDataSource;
import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    @Named("remote_repository")
    CountryRepository provideRemoteDataRepository(RestDataSource restDataSource) {
        return restDataSource;
    }

    @Provides
    @Singleton
    @Named("local_repository")
    CountryRepository provideLocalDataRepository(CountryDataStore localDataSource) {
        return localDataSource;
    }
}
