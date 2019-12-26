package co.base.data.exception;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import co.base.data.entity.CountryEntity;
import co.base.data.net.RestApi;
import co.base.data.realm.RealmDouble;
import io.reactivex.Observable;
import io.realm.RealmList;
import io.realm.RealmObject;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

import static co.base.data.exception.RetrofitException.HTTP;
import static co.base.data.exception.RetrofitException.NETWORK;
import static co.base.data.exception.RetrofitException.NO_INTERNET_CONNECTION;
import static junit.framework.TestCase.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RxErrorHandlingCallAdapterFactoryTest {
    @Rule
    public final MockWebServer server = new MockWebServer();

    private io.reactivex.observers.TestObserver<List<CountryEntity>> testSubscriber;

    @Before
    public void setUp(){
        testSubscriber = io.reactivex.observers.TestObserver.create();
    }

    @Test
    public void testNetworkError() {
        NetworkBehavior behavior = NetworkBehavior.create();
        behavior.setFailurePercent(100);

        Retrofit retrofit = new Retrofit.Builder( )
                .baseUrl( server.url("/") )
                .addCallAdapterFactory( RxErrorHandlingCallAdapterFactory.create( ) )
                .build( );

        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior).build();

        final BehaviorDelegate<RestApi> delegate = mockRetrofit.create(RestApi.class);

        RestApiMock mockService = new RestApiMock(delegate);


        mockService.getCountries().subscribe(testSubscriber);
        testSubscriber.assertNoValues();
        testSubscriber.assertError(RetrofitException.class);
        assertEquals(NETWORK, ((RetrofitException)testSubscriber.errors().get(0)).getKind());
    }

    @Test
    public void testNo200HttpCode() throws IOException {
        Retrofit retrofit = new Retrofit.Builder( )
                .baseUrl( server.url("/") )
                .addConverterFactory(GsonConverterFactory.create( createCustomGsonConverter() ))
                .addCallAdapterFactory( RxErrorHandlingCallAdapterFactory.create( ) )
                .build( );

        MockResponse mockResponse = new MockResponse();
        mockResponse.setResponseCode(404);
        mockResponse.setBody("{\"statusCode\": \"404\", \"errorMessage\": \"Not found\"}");
        server.enqueue(mockResponse);

        RestApi restApi = retrofit.create(RestApi.class);
        restApi.getCountries().subscribe(testSubscriber);

        testSubscriber.assertNoValues();
        testSubscriber.assertError(RetrofitException.class);
        RetrofitException exception = ((RetrofitException)testSubscriber.errors().get(0));
        assertEquals(HTTP, exception.getKind());
        assertEquals("404 Client Error", exception.getMessage());

        ErrorResponse errorResponse = ((RetrofitException)testSubscriber.errors().get(0)).getErrorBodyAs(ErrorResponse.class);
        assertEquals(404, errorResponse.getStatusCode());
        assertEquals("Not found", errorResponse.getErrorMessage());
    }

    @Test
    public void testNoInternetConnectedException() {
        Retrofit retrofit = new Retrofit.Builder( )
                .baseUrl( server.url("/") )
                .addConverterFactory(GsonConverterFactory.create( createCustomGsonConverter() ))
                .addCallAdapterFactory( RxErrorHandlingCallAdapterFactory.create( ) )
                .client(new OkHttpClient.Builder().addInterceptor(chain -> {
                    throw new NoInternetConnectionException();
                }).build())
                .build( );

        RestApi restApi = retrofit.create(RestApi.class);

        restApi.getCountries().subscribe(testSubscriber);
        testSubscriber.assertNoValues();
        testSubscriber.assertError(RetrofitException.class);
        assertEquals(NO_INTERNET_CONNECTION, ((RetrofitException)testSubscriber.errors().get(0)).getKind());
    }

    private class RestApiMock implements RestApi {
        private final BehaviorDelegate<RestApi> delegate;

        public RestApiMock(BehaviorDelegate<RestApi> delegate) {
            this.delegate = delegate;
        }

        public Observable<List<CountryEntity>> getCountries() {
            return delegate.returningResponse("test").getCountries();
        }
    }

    private Gson createCustomGsonConverter(){
        Type token = new TypeToken<RealmList<RealmDouble>>( ) {}.getType( );
        return new GsonBuilder( )
                .setExclusionStrategies( new ExclusionStrategy( )
                {
                    @Override
                    public boolean shouldSkipField (FieldAttributes f)
                    {
                        return f.getDeclaringClass( ).equals( RealmObject.class );
                    }

                    @Override
                    public boolean shouldSkipClass (Class<?> clazz)
                    {
                        return false;
                    }
                } )
                .registerTypeAdapter( token, new TypeAdapter<RealmList<RealmDouble>>( )
                {

                    @Override
                    public void write (JsonWriter out, RealmList<RealmDouble> value)
                            throws IOException
                    {
                        // Ignore
                    }

                    @Override
                    public RealmList<RealmDouble> read (JsonReader in) throws IOException
                    {
                        RealmList<RealmDouble> list = new RealmList<>( );
                        in.beginArray( );
                        while ( in.hasNext( ) )
                        {
                            list.add( new RealmDouble( in.nextDouble( ) ) );
                        }
                        in.endArray( );
                        return list;
                    }
                } )
                .create( );
    }
}
