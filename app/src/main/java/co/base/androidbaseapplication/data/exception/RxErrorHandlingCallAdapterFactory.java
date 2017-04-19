package co.base.androidbaseapplication.data.exception;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.functions.Func1;

public class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory
{
    private final RxJavaCallAdapterFactory mOriginal;

    private RxErrorHandlingCallAdapterFactory ()
    {
        mOriginal = RxJavaCallAdapterFactory.create( );
    }

    public static CallAdapter.Factory create ()
    {
        return new RxErrorHandlingCallAdapterFactory( );
    }

    @Override
    public CallAdapter<?> get (Type returnType, Annotation[] annotations, Retrofit retrofit)
    {
        return new RxCallAdapterWrapper( retrofit,
                mOriginal.get( returnType, annotations, retrofit ) );
    }

    private static class RxCallAdapterWrapper implements CallAdapter<Observable<?>>
    {
        private final Retrofit mRetrofit;
        private final CallAdapter<?> mWrapped;

        public RxCallAdapterWrapper (Retrofit retrofit, CallAdapter<?> wrapped)
        {
            this.mRetrofit = retrofit;
            this.mWrapped = wrapped;
        }

        @Override
        public Type responseType ()
        {
            return mWrapped.responseType( );
        }

        @SuppressWarnings("unchecked")
        @Override
        public <R> Observable<?> adapt (Call<R> call)
        {
            return (( Observable ) mWrapped.adapt( call )).onErrorResumeNext( new Func1<Throwable,
                    Observable>( )
            {
                @Override
                public Observable call (Throwable throwable)
                {
                    return Observable.error( asRetrofitException( throwable ) );
                }
            } );
        }

        private RetrofitException asRetrofitException (Throwable throwable)
        {
            // We had non-200 http error
            if ( throwable instanceof HttpException )
            {
                HttpException httpException = ( HttpException ) throwable;
                Response response = httpException.response( );
                return RetrofitException.httpError( response.raw( ).request( ).url( ).toString( ),
                        response, mRetrofit );
            }
            // A network error happened
            if ( throwable instanceof IOException )
            {
                return RetrofitException.networkError( ( IOException ) throwable );
            }

            if ( throwable instanceof NoInternetConnectionException )
            {
                return RetrofitException.
                        noInternetConnection( ( NoInternetConnectionException ) throwable );
            }

            // We don't know what happened. We need to simply convert to an unknown error

            return RetrofitException.unexpectedError( throwable );
        }
    }
}
