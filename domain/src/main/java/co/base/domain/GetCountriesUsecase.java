package co.base.domain;

import java.util.List;

import javax.inject.Inject;

import co.base.domain.executor.PostExecutionThread;
import co.base.domain.executor.ThreadExecutor;
import co.base.domain.repository.CountryRepository;
import io.reactivex.Observable;

public class GetCountriesUsecase extends Usecase<List<Country>>
{
    private final CountryRepository countryRepository;

    @Inject
    public GetCountriesUsecase (CountryRepository countryRepository, ThreadExecutor threadExecutor,
                                PostExecutionThread postExecutionThread)
    {
        super( threadExecutor, postExecutionThread );
        this.countryRepository = countryRepository;
    }

    @Override
    public Observable<List<Country>> buildObservable ()
    {
        return countryRepository.countries( );
    }
}
