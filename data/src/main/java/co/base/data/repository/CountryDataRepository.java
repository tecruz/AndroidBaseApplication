package co.base.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.base.data.entity.CountryEntity;
import co.base.data.mapper.CountryItemMapper;
import co.base.data.repository.datasource.CountryDataStore;
import co.base.data.repository.datasource.CountryDataStoreFactory;
import co.base.domain.Country;
import co.base.domain.repository.CountryRepository;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * {@link CountryRepository} for retrieving user data.
 */
@Singleton
public class CountryDataRepository implements CountryRepository
{

    private CountryDataStoreFactory countryDataStoreFactory;

    /**
     * Constructs a {@link CountryRepository}.
     *
     * @param dataStoreFactory A factory to construct different data source implementations.
     */
    @Inject
    public CountryDataRepository (CountryDataStoreFactory dataStoreFactory)
    {
        countryDataStoreFactory = dataStoreFactory;
    }

    /**
     * Retrieve a list of countries from the datastore according to the policy(network or database).
     *
     * @return List of countries from the correspondent {@link CountryDataStore}
     */
    @Override
    public Observable<List<Country>> countries ()
    {

        final CountryDataStore countryDataStore;

        countryDataStore = countryDataStoreFactory.create( );

        return countryDataStore.countryEntityList( ).concatMap((Function<List<CountryEntity>, Observable<List<Country>>>) countries -> {
            List<Country> countryList = CountryItemMapper.transform( countries );
            return Observable.just( countryList );
        });
    }
}
