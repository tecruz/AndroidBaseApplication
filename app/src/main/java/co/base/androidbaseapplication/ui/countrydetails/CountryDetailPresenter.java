package co.base.androidbaseapplication.ui.countrydetails;

import javax.inject.Inject;

import co.base.androidbaseapplication.ui.base.BasePresenter;
import io.reactivex.disposables.CompositeDisposable;

public class CountryDetailPresenter extends BasePresenter<CountryDetailMvpView>
{

    private CompositeDisposable disposables;

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
        if ( disposables != null ) disposables.clear( );
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
