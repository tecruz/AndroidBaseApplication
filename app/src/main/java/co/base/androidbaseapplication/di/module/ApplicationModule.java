package co.base.androidbaseapplication.di.module;

import android.app.Application;
import android.content.Context;

import co.base.androidbaseapplication.UIThread;
import co.base.data.JobExecutor;
import co.base.data.cache.CountryCache;
import co.base.data.cache.CountryCacheImpl;
import co.base.data.repository.CountryDataRepository;
import co.base.domain.executor.PostExecutionThread;
import co.base.domain.executor.ThreadExecutor;
import co.base.domain.repository.CountryRepository;
import dagger.Binds;
import dagger.Module;

/**
 * Provide application-level dependencies.
 */
@Module
public abstract class ApplicationModule
{
    @Binds
    abstract Context provideContext (Application context);

    @Binds
    abstract CountryCache provideCountryCache (CountryCacheImpl countryCache);

    @Binds
    abstract CountryRepository provideCountryRepository (CountryDataRepository countryDataRepository);

    @Binds
    abstract PostExecutionThread providePostExecutionThread (UIThread uiThread);

    @Binds
    abstract ThreadExecutor provideThreadExecutor (JobExecutor jobExecutor);
}
