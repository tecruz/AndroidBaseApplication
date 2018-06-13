package co.base.data.net;

import java.util.List;

import co.base.data.entity.CountryEntity;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi
{
    @GET("all")
    Observable<List<CountryEntity>> getCountries ();
}
