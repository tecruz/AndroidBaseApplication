package co.base.domain.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import co.base.domain.Country;
import co.base.domain.GetCountriesUsecase;
import co.base.domain.executor.PostExecutionThread;
import co.base.domain.executor.ThreadExecutor;
import co.base.domain.repository.CountryRepository;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetCountriesUsecaseTest
{
    @InjectMocks
    private GetCountriesUsecase getCountriesList;

    @Mock
    private CountryRepository mockCountryRepository;

    @Mock
    private ThreadExecutor mockThreadExecutor;

    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Before
    public void setUp ()
    {
        when( mockThreadExecutor.getScheduler( ) ).thenReturn( Schedulers.trampoline( ) );
        when( mockPostExecutionThread.getScheduler( ) ).thenReturn( Schedulers.trampoline( ) );
        when( mockCountryRepository.countries( ) )
                .thenReturn( Observable.<List<Country>>empty( ) );

    }

    @Test
    public void testGetCountryListUseCaseObservableHappyCase ()
    {
        getCountriesList.execute( );

        Mockito.verify( mockCountryRepository ).countries( );
        Mockito.verifyNoMoreInteractions( mockCountryRepository );
    }
}
