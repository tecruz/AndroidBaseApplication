package co.base.androidbaseapplication.model.local;

import android.content.Context;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.base.androidbaseapplication.injection.ApplicationContext;
import co.base.androidbaseapplication.model.entities.Country;
import co.base.androidbaseapplication.model.repository.CountryRepository;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Observable;

@Singleton
public class CountryDataStore implements CountryRepository {

    private final RealmConfiguration mRealmConfiguration;

    @Inject
    public CountryDataStore(@ApplicationContext Context context) {
        // Create a RealmConfiguration which is to locate Realm file in package's "files" directory.
        mRealmConfiguration = new RealmConfiguration.Builder(context).build();
    }

    @Override
    public Observable<List<Country>> getCountries() {
        Realm mDb = Realm.getInstance(mRealmConfiguration);
        return Observable.just(
                mDb.copyFromRealm(mDb.where(Country.class).findAll())).distinct();
    }

    public void setCountries(final Collection<Country> newCountries) {
        // Get a Realm instance for this thread
        Realm mDb = Realm.getInstance(mRealmConfiguration);
        // Persist your data easily
        mDb.beginTransaction();
        mDb.copyToRealm(newCountries);
        mDb.commitTransaction();
    }
}
