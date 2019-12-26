package co.base.data.repository.datasource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import co.base.data.cache.CountryCache;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseCountryDataStoreTest {

    private DatabaseCountryDataStore databaseCountryDataStore;

    @Mock
    private CountryCache countryCache;

    @Before
    public void setUp() {
        databaseCountryDataStore = new DatabaseCountryDataStore(countryCache);
    }

    @Test
    public void testGetCountryEntityFromCache() {
        databaseCountryDataStore.countryEntityList();
        verify(countryCache).getCountries();
    }
}
