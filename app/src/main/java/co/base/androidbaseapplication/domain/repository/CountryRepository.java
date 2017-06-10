package co.base.androidbaseapplication.domain.repository;

import java.util.List;

import co.base.androidbaseapplication.data.Policies;
import co.base.androidbaseapplication.ui.entity.Country;
import io.reactivex.Observable;

/**
 * Interface that represents a Repository for getting {@link Country} related data.
 */
public interface CountryRepository
{
    /**
     * @param policy The policy used by the repository {@link Policies}.
     *
     * Get an {@link Observable} which will emit a List of {@link Country}.
     */
    Observable<List<Country>> countries (Policies policy);
}
