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
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

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

    /**
     * Retrieve a list of countries from the datastore (network or cache).
     *
     * @param isSync True to force data from network, false otherwise.
     * @return List of countries from the correspondent {@link CountryDataStore}
     */
    @Override
    public Observable<List<Country>> countries (boolean isSync)
    {

        final CountryDataStore countryDataStore;

        countryDataStore = mCountryDataStoreFactory.create( isSync );

        return countryDataStore.countryEntityList( ).concatMap( new Function<List<CountryEntity>,
                Observable<List<Country>>>( )
        {
            @Override
            public Observable<List<Country>> apply (@NonNull List<CountryEntity> countries)
                    throws Exception
            {
                List<Country> countryList = CountryItemMapper.transform( countries );
                return Observable.just( countryList );
            }

        } );
    }
}
