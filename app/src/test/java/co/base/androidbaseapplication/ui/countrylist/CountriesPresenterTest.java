package co.base.androidbaseapplication.ui.countrylist;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import co.base.androidbaseapplication.domain.GetCountriesUsecase;
import co.base.androidbaseapplication.ui.entity.Country;
import io.reactivex.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
public class CountriesPresenterTest
{
    private CountriesPresenter countriesListPresenter;

    @Mock
    private Context context;

    @Mock
    private CountriesMvpView countriesView;

    @Mock
    private GetCountriesUsecase countriesUsecase;

    @Before
    public void setUp ()
    {
        countriesListPresenter = new CountriesPresenter( countriesUsecase, context );
        countriesListPresenter.attachView( countriesView );
    }

    @Test
    public void testCountriesPresenterInitialize ()
    {
        given( countriesUsecase.execute( ) ).willReturn( Observable.<List<Country>>empty( ) );

        countriesListPresenter.loadCountries( );

        verify( countriesView ).hideEmptyLabel( );
        verify( countriesView ).showLoading( );
        verify( countriesUsecase ).execute( );
    }
}
