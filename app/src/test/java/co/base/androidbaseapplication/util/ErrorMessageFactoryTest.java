package co.base.androidbaseapplication.util;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import co.base.androidbaseapplication.R;
import co.base.data.exception.RetrofitException;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ErrorMessageFactoryTest {

    @Mock
    private Context context;

    @Test
    public void testNoInternetConnectionMessage ()
    {
        ErrorMessageFactory.create(context, RetrofitException.NO_INTERNET_CONNECTION);

        verify(context).getString(R.string.exception_message_no_connection );
    }

    @Test
    public void testUnexpectedErrorMessage ()
    {
        ErrorMessageFactory.create(context, RetrofitException.UNEXPECTED);

        verify(context).getString(R.string.unexpected_error );
    }
}
