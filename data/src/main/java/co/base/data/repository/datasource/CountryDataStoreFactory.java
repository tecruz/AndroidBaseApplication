package co.base.data.repository.datasource;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.base.data.cache.CountryCache;
import co.base.data.net.RestApi;
import co.base.data.net.RestApiImpl;
import co.base.data.util.NetworkUtil;


/**
 * Factory that creates different implementations of {@link CountryDataStore}.
 */
@Singleton
public class CountryDataStoreFactory
{
    private final Context context;
    private final CountryCache countryCache;
    private final RestApi restApi;

    @Inject
    public CountryDataStoreFactory (Application context, CountryCache countryCache,
                                    RestApiImpl restApi)
    {

        this.context = context.getApplicationContext( );
        this.countryCache = countryCache;
        this.restApi = restApi;
    }

    /**
     * Create {@link CountryDataStore}.
     */
    public CountryDataStore create ()
    {
        return checkExpiredDataStore( );
    }

    /**
     * Create {@link CountryDataStore} to retrieve data from the Cloud.
     */
    private CountryDataStore createCloudDataStore ()
    {
        return new CloudCountryDataStore( restApi, countryCache );
    }

    /**
     * Create {@link CountryDataStore} to retrieve data from the database.
     */
    private CountryDataStore createDatabaseDataStore ()
    {
        return new DatabaseCountryDataStore( countryCache );
    }

    /**
     * Create {@link CountryDataStore} to retrieve data according to the rules:
     * <p>
     * - If network connection isn't available retrieve data from local database
     * <p>
     * - If network connection is available and local data isn't expired retrieve data
     * from local database else retrieve data from network
     */
    private CountryDataStore checkExpiredDataStore ()
    {

        CountryDataStore countryDataStore;
        if ( !NetworkUtil.isNetworkConnected( context ) )
        {
            countryDataStore = createDatabaseDataStore( );
        } else
        {
            if ( !countryCache.isExpired( ) && countryCache.isCached( ) )
            {
                countryDataStore = createDatabaseDataStore( );
            } else
            {
                countryDataStore = createCloudDataStore( );
            }
        }

        return countryDataStore;
    }
}
