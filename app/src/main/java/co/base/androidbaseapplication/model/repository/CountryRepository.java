package co.base.androidbaseapplication.model.repository;

import java.util.List;

import co.base.androidbaseapplication.model.entities.Country;
import rx.Observable;

public interface CountryRepository {

    Observable<List<Country>> getCountries();
}
