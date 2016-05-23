package co.base.androidbaseapplication.ui.countrylist;

import java.util.List;

import javax.inject.Inject;

import co.base.androidbaseapplication.domain.GetCountriesUsecase;
import co.base.androidbaseapplication.model.entities.Country;
import co.base.androidbaseapplication.ui.base.BasePresenter;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

public class CountriesPresenter extends BasePresenter<CountriesMvpView> {

    private final GetCountriesUsecase mCountriesUsecase;
    private Subscription mSubscription;

    @Inject
    public CountriesPresenter(GetCountriesUsecase countriesUsecase) {
        mCountriesUsecase = countriesUsecase;
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

    public void loadCountries() {
        checkViewAttached();
        mSubscription = mCountriesUsecase.execute()
                .subscribe(new Subscriber<List<Country>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the countries.");
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<Country> countries) {
                        if (countries.isEmpty()) {
                            getMvpView().showCountriesEmpty();
                        } else {
                            getMvpView().showCountries(countries);
                        }
                    }
                });
    }

    public void onCountryClicked(Country country) {
        getMvpView().countryItemClicked(country);
    }

}
