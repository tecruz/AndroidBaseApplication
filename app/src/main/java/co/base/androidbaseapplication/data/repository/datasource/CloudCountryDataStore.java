package co.base.androidbaseapplication.data.repository.datasource;

import java.util.List;

import co.base.androidbaseapplication.data.cache.CountryCache;
import co.base.androidbaseapplication.data.entity.CountryEntity;
import co.base.androidbaseapplication.data.net.RestApi;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

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
        return mRestApi.getCountries( ).doOnNext( new Consumer<List<CountryEntity>>( )
        {
            @Override
            public void accept (@NonNull List<CountryEntity> countryEntities) throws Exception
            {
                mCountryCache.put( countryEntities );
            }
        } );
    }
}