package co.base.data.repository.datasource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import co.base.data.cache.CountryCache;
import co.base.data.entity.CountryEntity;
import co.base.data.net.RestApi;
import io.reactivex.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CloudCountryDataStoreTest {

    private CloudCountryDataStore cloudCountryDataStore;

    @Mock
    private RestApi restApi;

    @Mock
    private CountryCache countryCache;

    @Before
    public void setUp() {
        cloudCountryDataStore = new CloudCountryDataStore(restApi, countryCache);
    }

    @Test
    public void testGetCountryEntityListFromApi() {
        CountryEntity fakeCountryEntity = new CountryEntity();
        Observable<List<CountryEntity>> fakeObservableList = Observable.just(Collections.singletonList(fakeCountryEntity));

        given(restApi.getCountries()).willReturn(fakeObservableList);
        cloudCountryDataStore.countryEntityList();
        verify(restApi).getCountries();
    }
}
