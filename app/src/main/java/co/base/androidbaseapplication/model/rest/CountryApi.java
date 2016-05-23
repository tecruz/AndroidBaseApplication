package co.base.androidbaseapplication.model.rest;

import java.util.List;

import co.base.androidbaseapplication.model.entities.rest.CountryItemResponse;
import retrofit2.http.GET;
import rx.Observable;

public interface CountryApi {

    @GET("region/europe")
    Observable<List<CountryItemResponse>> getCountries();
}
