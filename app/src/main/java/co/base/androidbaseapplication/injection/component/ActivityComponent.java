package co.base.androidbaseapplication.injection.component;

import co.base.androidbaseapplication.injection.PerActivity;
import co.base.androidbaseapplication.injection.module.ActivityModule;
import co.base.androidbaseapplication.ui.countrydetails.CountryDetailFragment;
import co.base.androidbaseapplication.ui.countrylist.CountriesActivity;
import dagger.Component;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(CountriesActivity countriesActivity);
    void inject(CountryDetailFragment countryDetailFragment);
}
