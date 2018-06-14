package co.base.androidbaseapplication.di.module;

import co.base.androidbaseapplication.services.SyncService;
import co.base.androidbaseapplication.ui.countrydetails.CountryDetailActivity;
import co.base.androidbaseapplication.ui.countrydetails.CountryDetailFragment;
import co.base.androidbaseapplication.ui.countrylist.CountriesActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Binds all sub-components within the app.
 */
@Module
public abstract class BuildersModule
{

    @ContributesAndroidInjector
    abstract CountriesActivity countriesActivity ();

    @ContributesAndroidInjector
    abstract CountryDetailFragment countryDetailFragment ();

    @ContributesAndroidInjector
    abstract CountryDetailActivity countryDetailActivity ();

    @ContributesAndroidInjector
    abstract SyncService syncService ();
}
