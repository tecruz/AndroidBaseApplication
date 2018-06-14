package co.base.androidbaseapplication.ui.countrylist;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import co.base.androidbaseapplication.mapper.CountryItemMapper;
import co.base.domain.Country;
import co.base.domain.GetCountriesUsecase;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountriesPresenterTest
{
    @InjectMocks
    private CountriesPresenter countriesListPresenter;

    @Mock
    private CountriesMvpView countriesView;

    @Mock
    private GetCountriesUsecase countriesUsecase;

    @Before
    public void setUp ()
    {
        countriesListPresenter.attachView( countriesView );
    }

    @Test
    public void testLoadCountriesEmpty ()
    {
        when( countriesUsecase.execute( ) ).thenReturn( Observable.create(
                new ObservableOnSubscribe<List<Country>>( )
                {
                    @Override
                    public void subscribe (@NonNull ObservableEmitter<List<Country>> subscriber)
                            throws Exception
                    {
                        subscriber.onNext( new ArrayList<Country>( ) );
                        subscriber.onComplete( );
                    }
                } ) );

        countriesListPresenter.loadCountries( );

        verify( countriesView ).hideEmptyLabel( );
        verify( countriesView ).showLoading( );
        verify( countriesUsecase ).execute( );

        verify( countriesView ).showCountriesEmpty( );
        verify( countriesView ).hideLoading( );
    }

    @Test
    public void testLoadCountriesWithCountries ()
    {
        Country testCountry = new Country( );
        testCountry.setCountryCode( "tt" );
        testCountry.setCountryName( "test" );

        final ArrayList<Country> testCountriesList = new ArrayList<>( );
        testCountriesList.add( testCountry );

        when( countriesUsecase.execute( ) ).thenReturn( Observable.create(
                new ObservableOnSubscribe<List<Country>>( )
                {
                    @Override
                    public void subscribe (@NonNull ObservableEmitter<List<Country>> subscriber)
                            throws Exception
                    {
                        subscriber.onNext( testCountriesList );
                        subscriber.onComplete( );
                    }
                } ) );

        countriesListPresenter.loadCountries( );

        verify( countriesView ).hideEmptyLabel( );
        verify( countriesView ).showLoading( );
        verify( countriesUsecase ).execute( );

        verify( countriesView ).hideLoading( );
    }

    @Test
    public void testLoadCountriesError ()
    {
        when( countriesUsecase.execute( ) ).thenReturn( Observable.create(
                new ObservableOnSubscribe<List<Country>>( )
                {
                    @Override
                    public void subscribe (@NonNull ObservableEmitter<List<Country>> subscriber)
                            throws Exception
                    {
                        subscriber.onError( new Throwable( ) );
                    }
                } ) );

        countriesListPresenter.loadCountries( );

        verify( countriesView ).hideEmptyLabel( );
        verify( countriesView ).showLoading( );
        verify( countriesUsecase ).execute( );

        verify( countriesView ).showError( );
        verify( countriesView ).showEmptyLabel( );
        verify( countriesView ).hideLoading( );
    }
}
