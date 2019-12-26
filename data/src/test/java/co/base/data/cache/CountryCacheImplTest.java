package co.base.data.cache;

import android.app.Application;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import co.base.data.entity.CountryEntity;
import co.base.data.util.PreferencesUtil;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountryCacheImplTest {
    private CountryCacheImpl countryCacheImpl;

    @Mock
    private DatabaseManager databaseManager;

    @Mock
    private Application application;

    @Mock
    private Context context;

    @Mock
    private PreferencesUtil preferencesUtil;

    @Before
    public void setUp(){
        when(application.getApplicationContext()).thenReturn(context);
        countryCacheImpl = new CountryCacheImpl(application, databaseManager, preferencesUtil);
    }

    @Test
    public void testGetCountriesCacheHappyCase() {
        countryCacheImpl.getCountries();

        verify(databaseManager).getCountries();
    }

    @Test
    public void testGetCountriesCached() {
        countryCacheImpl.isCached();

        verify(databaseManager).exists();
    }

    @Test
    public void testPutCountriesCache() {
        CountryEntity countryEntity = new CountryEntity();
        List<CountryEntity> countryEntityList = Collections.singletonList(countryEntity);
        countryCacheImpl.put(Collections.singletonList(countryEntity));

        verify(databaseManager).setCountries(eq(countryEntityList));
    }

    @Test
    public void testGetCountriesExpired() {
        countryCacheImpl.isExpired();

        verify(databaseManager).deleteDatabase();
    }

    @Test
    public void testGetCountriesNotExpired() {
        when(preferencesUtil.getLastSyncTimestamp( )).thenReturn(System.currentTimeMillis());
        countryCacheImpl.isExpired();

        verify(databaseManager, never()).deleteDatabase();
    }

    @Test
    public void testGetCountriesEvict() {
        countryCacheImpl.evictAll();

        verify(databaseManager).deleteDatabase();
    }
}
