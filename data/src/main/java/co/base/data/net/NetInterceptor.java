package co.base.data.net;

import android.content.Context;

import java.io.IOException;

import co.base.data.exception.NoInternetConnectionException;
import co.base.data.util.NetworkUtil;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Checks if there is network connection on all requests.
 */
public class NetInterceptor implements Interceptor
{
    private Context context;

    public NetInterceptor (Context context)
    {
        this.context = context;
    }

    @Override
    public Response intercept (Chain chain) throws IOException
    {
        if ( !NetworkUtil.isNetworkConnected( context ) )
        {
            throw new NoInternetConnectionException( );
        }
        return chain.proceed( chain.request( ) );
    }
}
