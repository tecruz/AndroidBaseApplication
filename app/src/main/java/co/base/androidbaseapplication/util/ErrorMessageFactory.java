package co.base.androidbaseapplication.util;

import android.content.Context;

import co.base.androidbaseapplication.R;
import co.base.data.exception.RetrofitException;

import static co.base.data.exception.RetrofitException.NO_INTERNET_CONNECTION;

/**
 * Factory used to create error messages from an Exception as a condition.
 */
public class ErrorMessageFactory
{

    private ErrorMessageFactory ()
    {
        //empty
    }

    /**
     * Creates a String representing an error message.
     *
     * @param context       Context needed to retrieve string resources.
     * @param exceptionKind An exception used as a condition to retrieve the correct error message.
     *
     * @return {@link String} an error message.
     */
    public static String create (Context context, @RetrofitException.Kind int exceptionKind)
    {
        String message;

        if ( exceptionKind == NO_INTERNET_CONNECTION )
        {
            message = context.getString( R.string.exception_message_no_connection );
        } else
        {
            message = context.getString( R.string.unexpected_error );
        }

        return message;
    }
}