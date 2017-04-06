package co.base.androidbaseapplication.data.net;

import android.content.Context;

import java.io.IOException;

import co.base.androidbaseapplication.data.exception.NoInternetConnectionException;
import co.base.androidbaseapplication.util.NetworkUtil;
import okhttp3.Interceptor;
import okhttp3.Response;

public class NetInterceptor implements Interceptor {

    private final String mAuthToken;
    private Context mContext;

    public NetInterceptor(Context context, String authToken) {
        mAuthToken = authToken;
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        //TO DO Custom http request with token
        if (!NetworkUtil.isNetworkConnected(mContext)) {
            throw new NoInternetConnectionException();
        }
        return chain.proceed(chain.request());
    }
}