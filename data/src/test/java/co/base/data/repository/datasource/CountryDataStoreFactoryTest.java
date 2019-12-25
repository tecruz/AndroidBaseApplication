package co.base.data.repository.datasource;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import co.base.data.cache.CountryCache;
import co.base.data.net.RestApiImpl;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountryDataStoreFactoryTest {

    private CountryDataStoreFactory countryDataStoreFactory;

    @Mock
    private CountryCache countryCache;

    @Mock
    private Application application;

    @Mock
    private Context context;

    @Mock
    private ConnectivityManager connectivityManager;

    @Mock
    private NetworkInfo networkInfo;

    @Mock
    private RestApiImpl restApi;

    @Before
    public void setUp() {
        when(application.getApplicationContext()).thenReturn(context);
        when(networkInfo.isConnectedOrConnecting()).thenReturn(true);
        when(connectivityManager.getActiveNetworkInfo()).thenReturn(networkInfo);
        when(context.getSystemService( Context.CONNECTIVITY_SERVICE )).thenReturn(connectivityManager);
        countryDataStoreFactory = new CountryDataStoreFactory(application, countryCache, restApi);
    }

    @Test
    public void testCreateDatabaseDataStoreNoNetwork() {
        when(networkInfo.isConnectedOrConnecting()).thenReturn(false);
        given(countryCache.isCached()).willReturn(true);
        given(countryCache.isExpired()).willReturn(false);

        CountryDataStore countryDataStore = countryDataStoreFactory.create();

        assertThat(countryDataStore, is(notNullValue()));
        assertThat(countryDataStore, is(instanceOf(DatabaseCountryDataStore.class)));

        verify(countryCache, never()).isCached();
        verify(countryCache, never()).isExpired();
    }

    @Test
    public void testCreateDatabaseDataStore() {
        given(countryCache.isCached()).willReturn(true);
        given(countryCache.isExpired()).willReturn(false);

        CountryDataStore countryDataStore = countryDataStoreFactory.create();

        assertThat(countryDataStore, is(notNullValue()));
        assertThat(countryDataStore, is(instanceOf(DatabaseCountryDataStore.class)));

        verify(countryCache).isCached();
        verify(countryCache).isExpired();
    }

    @Test
    public void testCreateCloudDataStore() {
        given(countryCache.isExpired()).willReturn(true);
        given(countryCache.isCached()).willReturn(false);

        CountryDataStore countryDataStore = countryDataStoreFactory.create();

        assertThat(countryDataStore, is(notNullValue()));
        assertThat(countryDataStore, is(instanceOf(CloudCountryDataStore.class)));

        verify(countryCache).isExpired();
    }
}
