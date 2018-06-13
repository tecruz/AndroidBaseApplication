package co.base.data.repository.datasource;

import java.util.List;

import co.base.data.entity.CountryEntity;
import io.reactivex.Observable;


/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface CountryDataStore
{
    /**
     * Get an {@link Observable} which will emit a List of {@link CountryEntity}.
     */
    Observable<List<CountryEntity>> countryEntityList ();
}
