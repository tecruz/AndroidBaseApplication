package co.base.androidbaseapplication.domain;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import co.base.androidbaseapplication.model.entities.Country;
import co.base.androidbaseapplication.model.repository.CountryRepository;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SyncCountriesUsecase extends Usecase<List<Country>> {

    private final CountryRepository mRepository;

    @Inject
    public SyncCountriesUsecase(@Named("remote_repository")
                                CountryRepository repository) {
        mRepository = repository;
    }

    @Override
    public Observable<List<Country>> buildObservable() {
        return mRepository.getCountries()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
