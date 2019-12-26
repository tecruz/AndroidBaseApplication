package co.base.androidbaseapplication.ui.countrylist.countrydetailslist;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import co.base.androidbaseapplication.ui.countrydetails.CountryDetailMvpView;
import co.base.androidbaseapplication.ui.countrydetails.CountryDetailPresenter;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class CountriesDetailPresenterTest
{
    @InjectMocks
    private CountryDetailPresenter countryDetailPresenter;

    @Mock
    private CountryDetailMvpView countryDetailMvpView;


    @Before
    public void setUp ()
    {
        countryDetailPresenter.attachView(countryDetailMvpView);
    }

    @Test
    public void testAttachView ()
    {
        verify(countryDetailMvpView, never()).showLoadingView();
        verify(countryDetailMvpView, never()).hideLoadingView();
    }

    @Test
    public void testDetachView ()
    {
        countryDetailPresenter.detachView();

        verifyNoMoreInteractions(countryDetailMvpView);
    }

    @Test
    public void testLoading(){
        countryDetailPresenter.showLoading();

        verify(countryDetailMvpView).showLoadingView();

        countryDetailPresenter.hideLoading();

        verify(countryDetailMvpView).hideLoadingView();
    }
}
