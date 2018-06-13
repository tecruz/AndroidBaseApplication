package co.base.domain.repository;

import java.util.List;

import co.base.domain.Country;
import io.reactivex.Observable;

/**
 * Interface that represents a Repository for getting {@link Country} related data.
 */
public interface CountryRepository
{
    /**
     * Get an {@link Observable} which will emit a List of {@link Country}.
     */
    Observable<List<Country>> countries ();
}
