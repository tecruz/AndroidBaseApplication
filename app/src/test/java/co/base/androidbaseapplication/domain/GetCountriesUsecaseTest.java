package co.base.androidbaseapplication.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;
import java.util.concurrent.Callable;

import co.base.androidbaseapplication.domain.repository.CountryRepository;
import co.base.androidbaseapplication.ui.entity.Country;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class GetCountriesUsecaseTest
{
    @InjectMocks
    private GetCountriesUsecase getCountriesList;

    @Mock
    private CountryRepository mockCountryRepository;

    @Before
    public void setUp ()
    {

        RxAndroidPlugins.setInitMainThreadSchedulerHandler( new Function<Callable<Scheduler>,
                Scheduler>( )
        {
            @Override
            public Scheduler apply (@NonNull Callable<Scheduler> schedulerCallable) throws Exception
            {
                return Schedulers.trampoline( );
            }
        } );

        when( mockCountryRepository.countries( getCountriesList.policy ) )
                .thenReturn( Observable.<List<Country>>empty( ) );

    }

    @Test
    public void testGetCountryListUseCaseObservableHappyCase ()
    {
        getCountriesList.execute( );

        verify( mockCountryRepository ).countries( getCountriesList.policy );
        verifyNoMoreInteractions( mockCountryRepository );
    }

    @After
    public void tearDown ()
    {
        RxAndroidPlugins.reset( );
    }
}
