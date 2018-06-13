package co.base.data.repository.datasource;

import java.util.List;

import co.base.data.cache.CountryCache;
import co.base.data.entity.CountryEntity;
import io.reactivex.Observable;

public class DatabaseCountryDataStore implements CountryDataStore
{
    private final CountryCache countryCache;

    /**
     * Construct a {@link CountryDataStore} based on Realm database.
     *
     * @param countryCache A {@link CountryCache} to cache data retrieved from the api.
     */
    DatabaseCountryDataStore (CountryCache countryCache)
    {
        this.countryCache = countryCache;
    }

    @Override
    public Observable<List<CountryEntity>> countryEntityList ()
    {
        return countryCache.getCountries( );
    }
}
