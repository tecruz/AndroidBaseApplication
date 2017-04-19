package co.base.androidbaseapplication.data.exception;

public class ErrorResponse
{
    private int mStatusCode;
    private String mErrorMessage;

    public int getStatusCode ()
    {
        return mStatusCode;
    }

    public void setStatusCode (int statusCode)
    {
        this.mStatusCode = statusCode;
    }

    public String getErrorMessage ()
    {
        return mErrorMessage;
    }

    public void setErrorMessage (String errorMessage)
    {
        this.mErrorMessage = errorMessage;
    }
}
