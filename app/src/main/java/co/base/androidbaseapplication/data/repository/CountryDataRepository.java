package co.base.androidbaseapplication.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.base.androidbaseapplication.data.entity.CountryEntity;
import co.base.androidbaseapplication.data.repository.datasource.CountryDataStore;
import co.base.androidbaseapplication.data.repository.datasource.CountryDataStoreFactory;
import co.base.androidbaseapplication.domain.repository.CountryRepository;
import co.base.androidbaseapplication.mapper.CountryItemMapper;
import co.base.androidbaseapplication.ui.entity.Country;
import rx.Observable;
import rx.functions.Func1;

/**
 * {@link CountryRepository} for retrieving user data.
 */
@Singleton
public class CountryDataRepository implements CountryRepository
{

    private CountryDataStoreFactory mCountryDataStoreFactory;

    /**
     * Constructs a {@link CountryRepository}.
     *
     * @param dataStoreFactory A factory to construct different data source implementations.
     */
    @Inject
    public CountryDataRepository (CountryDataStoreFactory dataStoreFactory)
    {
        mCountryDataStoreFactory = dataStoreFactory;
    }

    @Override
    public Observable<List<Country>> countries (boolean isSync)
    {

        final CountryDataStore countryDataStore;

        countryDataStore = mCountryDataStoreFactory.create( isSync );

        return countryDataStore.countryEntityList( ).concatMap( new Func1<List<CountryEntity>,
                Observable<List<Country>>>( )
        {
            @Override
            public Observable<List<Country>> call (List<CountryEntity> countries)
            {
                List<Country> countryList = CountryItemMapper.transform( countries );
                return Observable.just( countryList );
            }
        } );
    }
}
