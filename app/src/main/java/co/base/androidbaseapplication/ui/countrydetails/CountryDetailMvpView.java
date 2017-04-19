package co.base.androidbaseapplication.ui.countrydetails;

import co.base.androidbaseapplication.ui.base.MvpView;

public interface CountryDetailMvpView extends MvpView
{

    void showLoadingView ();

    void hideLoadingView ();
}
