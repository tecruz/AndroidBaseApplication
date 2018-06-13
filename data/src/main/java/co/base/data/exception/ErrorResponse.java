package co.base.data.exception;

public class ErrorResponse
{
    private int statusCode;
    private String errorMessage;

    public int getStatusCode ()
    {
        return statusCode;
    }

    public void setStatusCode (int statusCode)
    {
        this.statusCode = statusCode;
    }

    public String getErrorMessage ()
    {
        return errorMessage;
    }

    public void setErrorMessage (String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
}
