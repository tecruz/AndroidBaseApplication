package co.base.data.exception;

import android.support.annotation.IntDef;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Custom Retrofit error types.
 */
public class RetrofitException extends RuntimeException
{
    private final String url;
    private final Response response;
    private final int kind;
    private final Retrofit retrofit;

    /**
     * An {@link IOException} occurred while communicating to the server.
     */
    public static final int NETWORK = 0;
    /**
     * No internet connection
     **/
    public static final int NO_INTERNET_CONNECTION = 1;
    /**
     * A non-200 HTTP status code was received from the server.
     */
    public static final int HTTP = 2;
    /**
     * An internal error occurred while attempting to execute a request. It is best practice to
     * re-throw this exception so your application crashes.
     */
    public static final int UNEXPECTED = 3;

    /**
     * Identifies the event kind which triggered a {@link RetrofitException}.
     */
    @IntDef({NETWORK, NO_INTERNET_CONNECTION, HTTP, UNEXPECTED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Kind
    {
    }

    RetrofitException (String message, String url, Response response, @Kind int kind,
                       Throwable exception, Retrofit retrofit)
    {
        super( message, exception );
        this.url = url;
        this.response = response;
        this.kind = kind;
        this.retrofit = retrofit;
    }

    /**
     * The request URL which produced the error.
     */
    public String getUrl ()
    {
        return url;
    }

    /**
     * Response object containing status code, headers, body, etc.
     */
    public Response getResponse ()
    {
        return response;
    }

    /**
     * The event kind which triggered this error.
     */
    @Kind
    public int getKind ()
    {
        return kind;
    }

    /**
     * The Retrofit this request was executed on
     */
    public Retrofit getRetrofit ()
    {
        return retrofit;
    }

    /**
     * HTTP response body converted to specified {@code type}. {@code null} if there is no
     * response.
     *
     * @throws IOException if unable to convert the body to the specified {@code type}.
     */
    public <T> T getErrorBodyAs (Class<T> type) throws IOException
    {
        if ( response == null || response.errorBody( ) == null )
        {
            return null;
        }
        Converter<ResponseBody, T> converter = retrofit.responseBodyConverter( type,
                new Annotation[0] );
        return converter.convert( response.errorBody( ) );
    }

    public static RetrofitException httpError (String url, Response response, Retrofit retrofit)
    {
        String message = response.code( ) + " " + response.message( );
        return new RetrofitException( message, url, response, HTTP, null, retrofit );
    }

    public static RetrofitException networkError (IOException exception)
    {
        return new RetrofitException( exception.getMessage( ), null, null,
                NETWORK, exception, null );
    }

    public static RetrofitException noInternetConnection (NoInternetConnectionException exception)
    {
        return new RetrofitException( exception.getMessage( ), null, null,
                NO_INTERNET_CONNECTION, exception, null );
    }

    public static RetrofitException unexpectedError (Throwable exception)
    {
        return new RetrofitException( exception.getMessage( ), null, null,
                UNEXPECTED, exception, null );
    }
}
