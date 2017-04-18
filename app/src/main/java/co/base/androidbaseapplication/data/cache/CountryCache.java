package co.base.androidbaseapplication.data.cache;

import java.util.List;

import co.base.androidbaseapplication.data.entity.CountryEntity;
import rx.Observable;

/**
 * An interface representing a country Cache.
 */
public interface CountryCache
{
    /**
     * Gets an {@link rx.Observable} which will emit a collection{@link CountryEntity}.
     */
    Observable<List<CountryEntity>> getCountries ();

    /**
     * Puts and element into the cache.
     *
     * @param countryEntity Element to insert in the cache.
     */
    void put (List<CountryEntity> countryEntity);

    /**
     * Checks if a {@link List<CountryEntity>} exists in the cache.
     *
     * @return true if the element is cached, otherwise false.
     */
    boolean isCached ();

    /**
     * Checks if the cache is expired.
     *
     * @return true, the cache is expired, otherwise false.
     */
    boolean isExpired ();

    /**
     * Evict all elements of the cache.
     */
    void evictAll ();
}