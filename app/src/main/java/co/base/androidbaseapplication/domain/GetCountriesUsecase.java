package co.base.androidbaseapplication.domain;

import java.util.List;

import javax.inject.Inject;

import co.base.androidbaseapplication.domain.repository.CountryRepository;
import co.base.androidbaseapplication.ui.entity.Country;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GetCountriesUsecase extends Usecase<List<Country>>
{
    private final CountryRepository mRepository;
    private boolean mIsSync;

    @Inject
    public GetCountriesUsecase (CountryRepository countryRepository)
    {
        mRepository = countryRepository;
    }

    @Override
    public Observable<List<Country>> buildObservable ()
    {
        return mRepository.countries( mIsSync )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribeOn( Schedulers.io( ) );
    }

    public GetCountriesUsecase setIsSync (boolean isSync)
    {
        mIsSync = isSync;
        return this;
    }
}
