package co.base.androidbaseapplication.data.cache;

import android.content.Context;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.base.androidbaseapplication.data.entity.CountryEntity;
import co.base.androidbaseapplication.injection.ApplicationContext;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Observable;

/**
 * Helper class to do operations on Realm database.
 */
@Singleton
public class DatabaseManager
{

    private final RealmConfiguration mRealmConfiguration;

    @Inject
    public DatabaseManager (@ApplicationContext Context context)
    {
        // Create a RealmConfiguration which is to locate Realm file in package's "files" directory.
        mRealmConfiguration = new RealmConfiguration.Builder( context ).build( );
    }

    public Observable<List<CountryEntity>> getCountries ()
    {
        Realm mDb = Realm.getInstance( mRealmConfiguration );
        List<CountryEntity> countryEntityList =
                mDb.copyFromRealm( mDb.where( CountryEntity.class ).findAll( ) );
        mDb.close( );
        return Observable.just( countryEntityList );
    }

    public void setCountries (final Collection<CountryEntity> newCountries)
    {
        // Get a Realm instance for this thread
        Realm mDb = Realm.getInstance( mRealmConfiguration );
        // Persist your data easily
        mDb.beginTransaction( );
        mDb.delete( CountryEntity.class );
        mDb.copyToRealm( newCountries );
        mDb.commitTransaction( );
        mDb.close( );
    }

    public boolean exists ()
    {
        return !Realm.getInstance( mRealmConfiguration ).isEmpty( );
    }

    public void deleteDatabase ()
    {
        Realm.getInstance( mRealmConfiguration ).close( );
        Realm.deleteRealm( mRealmConfiguration );
    }
}
