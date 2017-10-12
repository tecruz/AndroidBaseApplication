package co.base.androidbaseapplication.data.repository.datasource;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.base.androidbaseapplication.data.Policies;
import co.base.androidbaseapplication.data.cache.CountryCache;
import co.base.androidbaseapplication.data.net.RestApi;
import co.base.androidbaseapplication.data.net.RestApiImpl;
import co.base.androidbaseapplication.injection.ApplicationContext;
import co.base.androidbaseapplication.util.NetworkUtil;

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
    public CountryDataStoreFactory (@ApplicationContext Context context,
                                    CountryCache countryCache, RestApiImpl restApi)
    {

        this.context = context;
        this.countryCache = countryCache;
        this.restApi = restApi;
    }

    /**
     * Create {@link CountryDataStore}.
     */
    public CountryDataStore create (Policies policy)
    {
        CountryDataStore countryDataStore;

        switch ( policy.getPolicy( ) )
        {
            case Policies.DATABASE:
                countryDataStore = checkExpiredDataStore( );
                break;
            case Policies.NETWORK:
                countryDataStore = createCloudDataStore( );
                break;
            default:
                countryDataStore = checkExpiredDataStore( );
                break;
        }


        return countryDataStore;
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
