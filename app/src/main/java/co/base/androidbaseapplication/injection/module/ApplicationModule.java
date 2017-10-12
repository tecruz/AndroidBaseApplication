package co.base.androidbaseapplication.injection.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import co.base.androidbaseapplication.data.cache.CountryCache;
import co.base.androidbaseapplication.data.cache.CountryCacheImpl;
import co.base.androidbaseapplication.data.repository.CountryDataRepository;
import co.base.androidbaseapplication.domain.repository.CountryRepository;
import co.base.androidbaseapplication.injection.ApplicationContext;
import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule
{
    protected final Application application;

    public ApplicationModule (Application application)
    {
        this.application = application;
    }

    @Provides
    Application provideApplication ()
    {
        return application;
    }

    @Provides
    @ApplicationContext
    Context provideContext ()
    {
        return application;
    }

    @Provides
    @Singleton
    CountryCache provideCountryCache (CountryCacheImpl countryCache)
    {
        return countryCache;
    }

    @Provides
    @Singleton
    CountryRepository provideCountryRepository (CountryDataRepository countryDataRepository)
    {
        return countryDataRepository;
    }
}
