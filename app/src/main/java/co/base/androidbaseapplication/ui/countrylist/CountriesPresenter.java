package co.base.androidbaseapplication.ui.countrylist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import java.util.List;

import javax.inject.Inject;

import co.base.androidbaseapplication.events.Events;
import co.base.androidbaseapplication.mapper.CountryItemMapper;
import co.base.androidbaseapplication.ui.base.BasePresenter;
import co.base.domain.Country;
import co.base.domain.GetCountriesUsecase;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public class CountriesPresenter extends BasePresenter<CountriesMvpView>
{

    private final GetCountriesUsecase countriesUsecase;
    private final CompositeDisposable disposables;
    private Context context;
    private boolean hasCountries;

    @Inject
    public CountriesPresenter (GetCountriesUsecase countriesUsecase,
                               Context context)
    {
        this.countriesUsecase = countriesUsecase;
        this.context = context;
        disposables = new CompositeDisposable( );
    }

    @Override
    public void attachView (CountriesMvpView mvpView)
    {
        super.attachView( mvpView );
    }

    @Override
    public void detachView ()
    {
        super.detachView( );
        disposables.clear( );
    }

    @Override
    public void pause ()
    {
        super.pause( );
        LocalBroadcastManager.getInstance( context ).unregisterReceiver( mMessageReceiver );
    }

    @Override
    public void resume ()
    {
        super.resume( );
        IntentFilter filterEvents = new IntentFilter( );
        filterEvents.addAction( new Events( Events.SYNC_COMPLETED ).getDescription( ) );
        filterEvents.addAction( new Events( Events.SYNC_ERROR ).getDescription( ) );
        LocalBroadcastManager.getInstance( context )
                .registerReceiver( mMessageReceiver, filterEvents );
    }

    public void loadCountries ()
    {
        checkViewAttached( );
        getMvpView( ).hideEmptyLabel( );
        getMvpView( ).showLoading( );
        disposables.add( countriesUsecase.execute( )
                .subscribeWith( new DisposableObserver<List<Country>>( )
                {
                    @Override
                    public void onComplete ()
                    {
                        getMvpView( ).hideLoading( );
                    }

                    @Override
                    public void onError (Throwable e)
                    {
                        Timber.e( e, "There was an error loading the countries." );
                        getMvpView( ).showError( );
                        getMvpView( ).showEmptyLabel( );
                        getMvpView( ).hideLoading( );
                    }

                    @Override
                    public void onNext (List<Country> countries)
                    {
                        if ( !countries.isEmpty( ) )
                        {
                            hasCountries = true;
                            getMvpView( ).showCountries( CountryItemMapper.transform( countries ) );
                        } else
                        {
                            getMvpView( ).showCountriesEmpty( );
                        }
                    }
                } ) );
    }

    public void onCountryClicked (co.base.androidbaseapplication.ui.entity.Country country)
    {
        getMvpView( ).countryItemClicked( country );
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver( )
    {
        @Override
        public void onReceive (Context context, Intent intent)
        {
            if ( intent.getAction( ).
                    equals( new Events( Events.SYNC_COMPLETED ).getDescription( ) ) )
            {
                loadCountries( );
            } else if ( intent.getAction( ).
                    equals( new Events( Events.SYNC_ERROR ).getDescription( ) ) )
            {
                getMvpView( ).hideLoading( );
                if ( !hasCountries )
                {
                    getMvpView( ).showEmptyLabel( );
                    getMvpView( ).showError( );
                }
            } else
            {
                getMvpView( ).showLoading( );
                getMvpView( ).hideEmptyLabel( );
            }

        }
    };

}
