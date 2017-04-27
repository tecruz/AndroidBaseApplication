package co.base.androidbaseapplication.data.exception;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Custom Retrofit error types.
 */
public class RetrofitException extends RuntimeException
{
    public static RetrofitException httpError (String url, Response response, Retrofit retrofit)
    {
        String message = response.code( ) + " " + response.message( );
        return new RetrofitException( message, url, response, Kind.HTTP, null, retrofit );
    }

    public static RetrofitException networkError (IOException exception)
    {
        return new RetrofitException( exception.getMessage( ), null, null,
                Kind.NETWORK, exception, null );
    }

    public static RetrofitException noInternetConnection (NoInternetConnectionException exception)
    {
        return new RetrofitException( exception.getMessage( ), null, null,
                Kind.NO_INTERNET_CONNECTION, exception, null );
    }

    public static RetrofitException unexpectedError (Throwable exception)
    {
        return new RetrofitException( exception.getMessage( ), null, null,
                Kind.UNEXPECTED, exception, null );
    }

    /**
     * Identifies the event kind which triggered a {@link RetrofitException}.
     */
    public enum Kind
    {
        /**
         * An {@link IOException} occurred while communicating to the server.
         */
        NETWORK,
        /**
         * No internet connection
         **/
        NO_INTERNET_CONNECTION,
        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }

    private final String mUrl;
    private final Response mResponse;
    private final Kind mKind;
    private final Retrofit mRetrofit;

    RetrofitException (String message, String url, Response response, Kind kind,
                       Throwable exception, Retrofit retrofit)
    {
        super( message, exception );
        this.mUrl = url;
        this.mResponse = response;
        this.mKind = kind;
        this.mRetrofit = retrofit;
    }

    /**
     * The request URL which produced the error.
     */
    public String getUrl ()
    {
        return mUrl;
    }

    /**
     * Response object containing status code, headers, body, etc.
     */
    public Response getResponse ()
    {
        return mResponse;
    }

    /**
     * The event kind which triggered this error.
     */
    public Kind getKind ()
    {
        return mKind;
    }

    /**
     * The Retrofit this request was executed on
     */
    public Retrofit getRetrofit ()
    {
        return mRetrofit;
    }

    /**
     * HTTP response body converted to specified {@code type}. {@code null} if there is no
     * response.
     *
     * @throws IOException if unable to convert the body to the specified {@code type}.
     */
    public <T> T getErrorBodyAs (Class<T> type) throws IOException
    {
        if ( mResponse == null || mResponse.errorBody( ) == null )
        {
            return null;
        }
        Converter<ResponseBody, T> converter = mRetrofit.responseBodyConverter( type,
                new Annotation[0] );
        return converter.convert( mResponse.errorBody( ) );
    }
}
