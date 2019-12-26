package co.base.androidbaseapplication.ui.countrydetails;

import javax.inject.Inject;

import co.base.androidbaseapplication.ui.base.BasePresenter;

public class CountryDetailPresenter extends BasePresenter<CountryDetailMvpView>
{

    @Inject
    public CountryDetailPresenter ()
    {

    }

    @Override
    public void attachView (CountryDetailMvpView mvpView)
    {
        super.attachView( mvpView );
    }

    @Override
    public void detachView ()
    {
        super.detachView( );
    }

    public void showLoading ()
    {
        checkViewAttached( );
        getMvpView( ).showLoadingView( );
    }

    public void hideLoading ()
    {
        checkViewAttached( );
        getMvpView( ).hideLoadingView( );
    }
}
