package co.base.data.cache;

import android.app.Application;
import android.content.Context;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.base.data.entity.CountryEntity;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Helper class to do operations on Realm database.
 */
@Singleton
public class DatabaseManager
{

    private final RealmConfiguration realmConfiguration;

    /**
     * Constructor of the class {@link DatabaseManager}.
     *
     * @param context Application context
     */
    @Inject
    public DatabaseManager (Application context)
    {
        // Create a RealmConfiguration which is to locate Realm file in package's "files" directory.
        Realm.init( context.getApplicationContext( ) );
        realmConfiguration = new RealmConfiguration.Builder( ).build( );
    }

    /**
     * Retrieves a list of countries from the Realm database
     *
     * @return A list of countries saved on the database
     */
    public Observable<List<CountryEntity>> getCountries ()
    {
        Realm mDb = Realm.getInstance( realmConfiguration );
        List<CountryEntity> countryEntityList =
                mDb.copyFromRealm( mDb.where( CountryEntity.class ).findAll( ) );
        mDb.close( );
        return Observable.just( countryEntityList );
    }

    /**
     * Save a list of countries on the Realm database
     *
     * @param newCountries List of countries to save
     */
    public void setCountries (final Collection<CountryEntity> newCountries)
    {
        Realm db = null;
        try
        {
            db = Realm.getInstance( realmConfiguration );
            db.executeTransactionAsync( new Realm.Transaction( )
            {
                @Override
                public void execute (Realm realm)
                {
                    realm.delete( CountryEntity.class );
                    realm.copyToRealm( newCountries );
                }
            } );
        } finally
        {
            if ( db != null )
            {
                db.close( );
            }
        }
    }

    /**
     * Check if a Realm database exists
     *
     * @return True if Realm database instance exits, false otherwise
     */
    public boolean exists ()
    {
        return !Realm.getInstance( realmConfiguration ).isEmpty( );
    }

    /**
     * Deletes the Realm database instance
     */
    public void deleteDatabase ()
    {
        Realm.getInstance( realmConfiguration ).close( );
        Realm.deleteRealm( realmConfiguration );
    }
}
