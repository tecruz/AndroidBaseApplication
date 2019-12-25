package co.base.data.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class NetworkUtilTest {

    @Test
    public void testHttpExceptionStatusCode ()
    {
        assertTrue(NetworkUtil.isHttpStatusCode(new HttpException(Response.error(404, ResponseBody.create(null, ""))), 404));
        assertFalse(NetworkUtil.isHttpStatusCode(new HttpException(Response.success(ResponseBody.create(null, ""))), 404));
        assertFalse(NetworkUtil.isHttpStatusCode(new HttpException(Response.error(404, ResponseBody.create(null, ""))), 200));
    }
}
