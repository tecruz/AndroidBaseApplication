package co.base.androidbaseapplication.domain.repository;

import java.util.List;

import co.base.androidbaseapplication.ui.entity.Country;
import rx.Observable;

/**
 * Interface that represents a Repository for getting {@link Country} related data.
 */
public interface CountryRepository {
    /**
     * Get an {@link rx.Observable} which will emit a List of {@link Country}.
     */
    Observable<List<Country>> countries(boolean isSync);
}