package co.base.androidbaseapplication.data.net;

import android.content.Context;

import java.io.IOException;

import co.base.androidbaseapplication.data.exception.NoInternetConnectionException;
import co.base.androidbaseapplication.util.NetworkUtil;
import okhttp3.Interceptor;
import okhttp3.Response;

public class NetInterceptor implements Interceptor
{
    private final String authToken;
    private Context context;

    public NetInterceptor (Context context, String authToken)
    {
        this.authToken = authToken;
        this.context = context;
    }

    @Override
    public Response intercept (Chain chain) throws IOException
    {
        //TO DO Custom http request with token
        if ( !NetworkUtil.isNetworkConnected( context ) )
        {
            throw new NoInternetConnectionException( );
        }
        return chain.proceed( chain.request( ) );
    }
}
