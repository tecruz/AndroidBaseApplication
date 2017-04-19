package co.base.androidbaseapplication.data.repository.datasource;

import java.util.List;

import co.base.androidbaseapplication.data.cache.CountryCache;
import co.base.androidbaseapplication.data.entity.CountryEntity;
import co.base.androidbaseapplication.data.net.RestApi;
import rx.Observable;
import rx.functions.Action1;

/**
 * {@link CountryDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudCountryDataStore implements CountryDataStore
{

    private final RestApi mRestApi;
    private final CountryCache mCountryCache;

    /**
     * Construct a {@link CountryDataStore} based on connections to the api (Cloud).
     *
     * @param restApi      The {@link RestApi} implementation to use.
     * @param countryCache A {@link CountryCache} to cache data retrieved from the api.
     */
    CloudCountryDataStore (RestApi restApi, CountryCache countryCache)
    {
        mRestApi = restApi;
        mCountryCache = countryCache;
    }

    @Override
    public Observable<List<CountryEntity>> countryEntityList ()
    {
        return mRestApi.getCountries( ).doOnNext( new Action1<List<CountryEntity>>( )
        {
            @Override
            public void call (List<CountryEntity> countryEntities)
            {
                mCountryCache.put( countryEntities );
            }
        } );
    }
}