package co.base.androidbaseapplication.ui.countrylist;

import java.util.List;

import co.base.androidbaseapplication.ui.entity.Country;
import co.base.androidbaseapplication.ui.base.MvpView;

public interface CountriesMvpView extends MvpView {

    void showCountries(List<Country> countries);

    void showCountriesEmpty();

    void showError();

    void countryItemClicked(Country country);

    void showRetry();

    void hideRetry();

    void showLoading();

    void hideLoading();

}
