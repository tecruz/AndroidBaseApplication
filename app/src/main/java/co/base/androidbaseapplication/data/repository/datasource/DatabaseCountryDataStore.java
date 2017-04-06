package co.base.androidbaseapplication.data.repository.datasource;

import java.util.List;

import co.base.androidbaseapplication.data.cache.CountryCache;
import co.base.androidbaseapplication.data.entity.CountryEntity;
import rx.Observable;

public class DatabaseCountryDataStore implements CountryDataStore {

    private final CountryCache mCountryCache;

    /**
     * Construct a {@link CountryDataStore} based on Realm database.
     *
     * @param countryCache A {@link CountryCache} to cache data retrieved from the api.
     */
    DatabaseCountryDataStore(CountryCache countryCache) {
        mCountryCache = countryCache;
    }


    @Override
    public Observable<List<CountryEntity>> countryEntityList() {
        return mCountryCache.getCountries();
    }
}