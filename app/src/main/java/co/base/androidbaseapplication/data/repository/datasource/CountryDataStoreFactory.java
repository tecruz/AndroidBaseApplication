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
    private final Context mContext;
    private final CountryCache mCountryCache;
    private final RestApi mRestApi;

    @Inject
    public CountryDataStoreFactory (@ApplicationContext Context context,
                                    CountryCache countryCache, RestApiImpl restApi)
    {

        mContext = context;
        mCountryCache = countryCache;
        mRestApi = restApi;
    }

    /**
     * Create {@link CountryDataStore}.
     */
    public CountryDataStore create (Policies policy)
    {
        CountryDataStore countryDataStore;

        switch ( policy.getPolicy() ) {
            case Policies.DATABASE:
                countryDataStore = checkExpiredDataStore();
                break;
            case Policies.NETWORK:
                countryDataStore = createCloudDataStore( );
                break;
            default:
                countryDataStore = checkExpiredDataStore ();
                break;
        }


        return countryDataStore;
    }

    /**
     * Create {@link CountryDataStore} to retrieve data from the Cloud.
     */
    private CountryDataStore createCloudDataStore ()
    {
        return new CloudCountryDataStore( mRestApi, mCountryCache );
    }

    /**
     * Create {@link CountryDataStore} to retrieve data from the database.
     */
    private CountryDataStore createDatabaseDataStore ()
    {
        return new DatabaseCountryDataStore( mCountryCache );
    }

    /**
     * Create {@link CountryDataStore} to retrieve data according to the rules:
     *
     * - If network connection isn't available retrieve data from local database
     *
     * - If network connection is available and local data isn't expired retrieve data
     * from local database else retrieve data from network
     */
    private CountryDataStore checkExpiredDataStore () {

        CountryDataStore countryDataStore;
        if ( !NetworkUtil.isNetworkConnected( mContext ) )
        {
            countryDataStore = createDatabaseDataStore( );
        } else
        {
            if ( !mCountryCache.isExpired( ) && mCountryCache.isCached( ) )
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
