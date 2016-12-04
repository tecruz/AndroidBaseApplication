package co.base.androidbaseapplication.data.cache;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.base.androidbaseapplication.data.entity.CountryEntity;
import co.base.androidbaseapplication.injection.ApplicationContext;
import co.base.androidbaseapplication.util.PreferencesUtil;
import rx.Observable;

import static co.base.androidbaseapplication.Config.EXPIRATION_TIME;

/**
 * {@link CountryCache} implementation.
 */
@Singleton
public class CountryCacheImpl implements CountryCache {


    private final Context mContext;
    private final DatabaseManager mDatabaseManager;
    @Inject
    PreferencesUtil mPreferencesUtil;

    /**
     * Constructor of the class {@link CountryCacheImpl}.
     *
     * @param context Application context
     * @param databaseManager {@link DatabaseManager} for saving serialized objects to the database.
     */
    @Inject
    public CountryCacheImpl(@ApplicationContext Context context, DatabaseManager databaseManager,
                            PreferencesUtil preferencesUtil) {
        if (context == null || databaseManager == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        mContext = context;
        mDatabaseManager = databaseManager;
        mPreferencesUtil = preferencesUtil;
    }

    @Override
    public Observable<List<CountryEntity>> getCountries() {
        return mDatabaseManager.getCountries();
    }

    @Override
    public void put(List<CountryEntity> countryEntityList) {
        mDatabaseManager.setCountries(countryEntityList);
        long syncTimeStamp = System.currentTimeMillis();
        mPreferencesUtil.setLastSyncTimestamp(syncTimeStamp);
    }

    @Override
    public boolean isCached() {
        return mDatabaseManager.exists();
    }

    @Override public boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = mPreferencesUtil.getLastSyncTimestamp();

        boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

        if (expired) {
            this.evictAll();
        }

        return expired;
    }

    @Override public void evictAll() {
        mDatabaseManager.deleteDatabase();
    }
}