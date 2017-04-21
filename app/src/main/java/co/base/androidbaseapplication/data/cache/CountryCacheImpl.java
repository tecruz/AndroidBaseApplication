package co.base.androidbaseapplication.data.cache;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.base.androidbaseapplication.data.entity.CountryEntity;
import co.base.androidbaseapplication.injection.ApplicationContext;
import co.base.androidbaseapplication.util.PreferencesUtil;
import io.reactivex.Observable;

import static co.base.androidbaseapplication.Config.EXPIRATION_TIME;

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
    public CountryCacheImpl (@ApplicationContext Context context, DatabaseManager databaseManager,
                             PreferencesUtil preferencesUtil)
    {
        if ( context == null || databaseManager == null )
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

    @Override
    public void put (List<CountryEntity> countryEntityList)
    {
        databaseManager.setCountries( countryEntityList );
        long syncTimeStamp = System.currentTimeMillis( );
        preferencesUtil.setLastSyncTimestamp( syncTimeStamp );
    }

    @Override
    public boolean isCached ()
    {
        return databaseManager.exists( );
    }

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

    @Override
    public void evictAll ()
    {
        databaseManager.deleteDatabase( );
    }
}