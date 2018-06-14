package co.base.data.cache;

import android.app.Application;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.base.data.entity.CountryEntity;
import co.base.data.util.PreferencesUtil;
import io.reactivex.Observable;

import static co.base.data.Config.EXPIRATION_TIME;

/**
 * {@link CountryCache} implementation.
 */
@Singleton
public class CountryCacheImpl implements CountryCache
{
    private final DatabaseManager databaseManager;

    @Inject
    PreferencesUtil preferencesUtil;

    /**
     * Constructor of the class {@link CountryCacheImpl}.
     *
     * @param context         Application context
     * @param databaseManager {@link DatabaseManager} for saving serialized objects to the
     *                        database.
     * @param preferencesUtil {@link PreferencesUtil} to save/update timestamp of the last data sync
     */
    @Inject
    public CountryCacheImpl (Application context, DatabaseManager databaseManager,
                             PreferencesUtil preferencesUtil)
    {
        if ( context.getApplicationContext( ) == null || databaseManager == null )
        {
            throw new IllegalArgumentException( "Invalid null parameter" );
        }

        this.databaseManager = databaseManager;
        this.preferencesUtil = preferencesUtil;
    }

    /**
     * Get a list of countries from the database
     */
    @Override
    public Observable<List<CountryEntity>> getCountries ()
    {
        return databaseManager.getCountries( );
    }

    /**
     * Save a list of countries to the database
     */
    @Override
    public void put (List<CountryEntity> countryEntityList)
    {
        databaseManager.setCountries( countryEntityList );
        long syncTimeStamp = System.currentTimeMillis( );
        preferencesUtil.setLastSyncTimestamp( syncTimeStamp );
    }

    /**
     * Checks if a list of countries is cached
     */
    @Override
    public boolean isCached ()
    {
        return databaseManager.exists( );
    }

    /**
     * Check if the country list is expired
     */
    @Override
    public boolean isExpired ()
    {
        long currentTime = System.currentTimeMillis( );
        long lastUpdateTime = preferencesUtil.getLastSyncTimestamp( );

        boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

        if ( expired )
        {
            this.evictAll( );
        }

        return expired;
    }

    /**
     * Delete database
     */
    @Override
    public void evictAll ()
    {
        databaseManager.deleteDatabase( );
    }
}