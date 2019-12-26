package co.base.data.util;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NetworkUtilTest {

    @Mock
    private Application application;

    @Mock
    private Context context;

    @Mock
    private ConnectivityManager connectivityManager;

    @Mock
    private NetworkInfo networkInfo;

    @Test
    public void testHttpExceptionStatusCode ()
    {
        assertTrue(NetworkUtil.isHttpStatusCode(new HttpException(Response.error(404, ResponseBody.create(null, ""))), 404));
        assertFalse(NetworkUtil.isHttpStatusCode(new HttpException(Response.success(ResponseBody.create(null, ""))), 404));
        assertFalse(NetworkUtil.isHttpStatusCode(new HttpException(Response.error(404, ResponseBody.create(null, ""))), 200));
    }

    @Test
    public void testNetworkConnection(){
        when(application.getApplicationContext()).thenReturn(context);
        when(networkInfo.isConnectedOrConnecting()).thenReturn(true);
        when(connectivityManager.getActiveNetworkInfo()).thenReturn(networkInfo);
        when(context.getSystemService( Context.CONNECTIVITY_SERVICE )).thenReturn(connectivityManager);
        when(networkInfo.isConnectedOrConnecting()).thenReturn(false);

        assertFalse(NetworkUtil.isNetworkConnected(context));

        when(networkInfo.isConnectedOrConnecting()).thenReturn(true);

        assertTrue(NetworkUtil.isNetworkConnected(context));
    }
}
