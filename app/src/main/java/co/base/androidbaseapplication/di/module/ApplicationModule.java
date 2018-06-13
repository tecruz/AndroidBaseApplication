package co.base.androidbaseapplication.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import co.base.androidbaseapplication.di.ApplicationContext;
import co.base.androidbaseapplication.di.UIThread;
import co.base.data.JobExecutor;
import co.base.data.cache.CountryCache;
import co.base.data.cache.CountryCacheImpl;
import co.base.data.repository.CountryDataRepository;
import co.base.domain.executor.PostExecutionThread;
import co.base.domain.executor.ThreadExecutor;
import co.base.domain.repository.CountryRepository;
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

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread (UIThread uiThread)
    {
        return uiThread;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor (JobExecutor jobExecutor)
    {
        return jobExecutor;
    }
}
