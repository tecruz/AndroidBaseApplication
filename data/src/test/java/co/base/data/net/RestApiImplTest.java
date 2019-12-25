package co.base.data.net;

import android.app.Application;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import co.base.data.R;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RestApiImplTest {

    private RestApiImpl restApiImpl;

    @Mock
    private Application application;

    @Mock
    private Context context;

    @Before
    public void setUp(){
        when(context.getString( R.string.API_BASE_URL ) ).thenReturn("https://restcountries.eu/rest/v2/");
        when(application.getApplicationContext()).thenReturn(context);
        restApiImpl = new RestApiImpl(application);
    }

    @Test
    public void testGetCountriesHappyCase() {
        restApiImpl.getCountries();

        assertNotNull(restApiImpl.getRestApi());
    }
}
