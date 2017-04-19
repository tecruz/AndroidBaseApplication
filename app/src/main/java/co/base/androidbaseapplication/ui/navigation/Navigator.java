package co.base.androidbaseapplication.ui.navigation;

import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.base.androidbaseapplication.ui.countrydetails.CountryDetailActivity;
import co.base.androidbaseapplication.ui.countrylist.CountriesActivity;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator
{

    @Inject
    public Navigator ()
    {
        //empty
    }

    /**
     * Goes to the countries list screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToCountryList (Context context)
    {
        if ( context != null )
        {
            Intent intentToLaunch = CountriesActivity.getCallingIntent( context );
            context.startActivity( intentToLaunch );
        }
    }

    /**
     * Goes to the country details screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToCountryDetails (Context context, String countryCode)
    {
        if ( context != null )
        {
            Intent intentToLaunch = CountryDetailActivity.getCallingIntent( context, countryCode );
            context.startActivity( intentToLaunch );
        }
    }
}