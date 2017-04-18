package co.base.androidbaseapplication.data.net;

import java.util.List;

import co.base.androidbaseapplication.data.entity.CountryEntity;
import retrofit2.http.GET;
import rx.Observable;

public interface RestApi
{

    @GET("all")
    Observable<List<CountryEntity>> getCountries ();
}
