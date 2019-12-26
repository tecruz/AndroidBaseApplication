package co.base.data.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import co.base.data.entity.CountryEntity;
import co.base.data.repository.datasource.CountryDataStore;
import co.base.data.repository.datasource.CountryDataStoreFactory;
import io.reactivex.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CountryDataRepositoryTest {

    private CountryDataRepository countryDataRepository;

    @Mock
    private CountryDataStoreFactory countryDataStoreFactory;

    @Mock
    private CountryDataStore countryDataStore;

    @Before
    public void setUp(){
        countryDataRepository = new CountryDataRepository(countryDataStoreFactory);
        given(countryDataStoreFactory.create()).willReturn(countryDataStore);
    }

    @Test
    public void testGetCountriesHappyCase() {
        List<CountryEntity> countryEntityList = new ArrayList<>();
        countryEntityList.add(new CountryEntity());
        given(countryDataStore.countryEntityList()).willReturn(Observable.just(countryEntityList));

        countryDataRepository.countries();

        verify(countryDataStoreFactory).create();
        verify(countryDataStore).countryEntityList();
    }
}
