package co.base.androidbaseapplication.data.net;

import android.content.Context;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.base.androidbaseapplication.BuildConfig;
import co.base.androidbaseapplication.R;
import co.base.androidbaseapplication.data.entity.CountryEntity;
import co.base.androidbaseapplication.data.exception.RxErrorHandlingCallAdapterFactory;
import co.base.androidbaseapplication.data.realm.RealmDouble;
import co.base.androidbaseapplication.injection.ApplicationContext;

import io.reactivex.Observable;
import io.realm.RealmList;
import io.realm.RealmObject;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * {@link RestApi} implementation for retrieving data from the network.
 */
@Singleton
public class RestApiImpl implements RestApi
{
    private final RestApi restApi;

    /**
     * Constructor of the class
     *
     * @param context Application context
     */
    @Inject
    public RestApiImpl (@ApplicationContext Context context)
    {

        OkHttpClient client;

        if ( BuildConfig.DEBUG )
        {

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor( );
            loggingInterceptor.setLevel( HttpLoggingInterceptor.Level.BODY );

            client = new OkHttpClient.Builder( ).addInterceptor( new NetInterceptor( context, "" ) )
                    .addInterceptor( loggingInterceptor ).build( );

        } else
        {
            client = new OkHttpClient.Builder( )
                    .addInterceptor( new NetInterceptor( context, "" ) ).build( );
        }

        Type token = new TypeToken<RealmList<RealmDouble>>( )
        {
        }
                .getType( );

        Gson gson = new GsonBuilder( )
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

        Retrofit retrofit = new Retrofit.Builder( )
                .baseUrl( context.getString( R.string.API_BASE_URL ) )
                .addConverterFactory( GsonConverterFactory.create( gson ) )
                .addCallAdapterFactory( RxErrorHandlingCallAdapterFactory.create( ) )
                .client( client )
                .build( );

        restApi = retrofit.create( RestApi.class );
    }

    /**
     * Retrieve a list of countries from the network.
     *
     * @return The list of countries
     */
    @Override
    public Observable<List<CountryEntity>> getCountries ()
    {
        return restApi.getCountries( );
    }
}
