package co.base.androidbaseapplication.ui.countrylist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import java.util.List;

import javax.inject.Inject;

import co.base.androidbaseapplication.domain.GetCountriesUsecase;
import co.base.androidbaseapplication.events.Events;
import co.base.androidbaseapplication.injection.ApplicationContext;
import co.base.androidbaseapplication.ui.entity.Country;
import co.base.androidbaseapplication.ui.base.BasePresenter;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

public class CountriesPresenter extends BasePresenter<CountriesMvpView> {

    private final GetCountriesUsecase mCountriesUsecase;
    private Subscription mSubscription;
    private Context mContext;
    private boolean mHasCountries;

    @Inject
    public CountriesPresenter(GetCountriesUsecase countriesUsecase,
                              @ApplicationContext Context context) {
        mCountriesUsecase = countriesUsecase;
        mContext = context;
    }

    @Override
    public void attachView(CountriesMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    @Override
    public void pause() {
        super.pause();
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mMessageReceiver);
    }

    @Override
    public void resume() {
        super.resume();
        IntentFilter filterEvents = new IntentFilter();
        filterEvents.addAction(Events.SYNC_COMPLETED.toString());
        filterEvents.addAction(Events.SYNC_ERROR.toString());
        filterEvents.addAction(Events.SYNC_STARTED.toString());
        LocalBroadcastManager.getInstance(mContext)
                .registerReceiver(mMessageReceiver, filterEvents);
    }

    public void loadCountries() {
        checkViewAttached();
        getMvpView().hideRetry();
        mSubscription = mCountriesUsecase.setIsSync(false).execute()
                .subscribe(new Subscriber<List<Country>>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the countries.");
                        getMvpView().showError();
                        getMvpView().showRetry();
                        getMvpView().hideLoading();
                    }

                    @Override
                    public void onNext(List<Country> countries) {
                        if (!countries.isEmpty()) {
                            mHasCountries = true;
                            getMvpView().showCountries(countries);
                        }
                    }
                });
    }

    public void onCountryClicked(Country country) {
        getMvpView().countryItemClicked(country);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Events.SYNC_COMPLETED.toString())) {
                loadCountries();
            } else if (intent.getAction().equals(Events.SYNC_ERROR.toString())) {
                getMvpView().hideLoading();
                if (!mHasCountries) {
                    getMvpView().showRetry();
                    getMvpView().showError();
                }
            } else {
                getMvpView().hideRetry();
                getMvpView().showLoading();
            }

        }
    };

}
