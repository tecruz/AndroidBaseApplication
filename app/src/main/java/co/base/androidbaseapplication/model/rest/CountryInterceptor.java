package co.base.androidbaseapplication.model.rest;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class CountryInterceptor implements Interceptor {

    private final String mAuthToken;

    public CountryInterceptor(String authToken) {
        mAuthToken = authToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        //TO DO Custom http request with token
        return null;
    }
}
