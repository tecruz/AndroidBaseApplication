package co.base.androidbaseapplication.ui.countrydetails;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import co.base.androidbaseapplication.R;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.model.Atoms.getCurrentUrl;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)
public class CountryDetailActivityTest
{
    @Rule
    public ActivityTestRule<CountryDetailActivity>
            mActivityRule =
            new ActivityTestRule<CountryDetailActivity>( CountryDetailActivity.class )
    {
        @Override
        protected Intent getActivityIntent ()
        {
            Context targetContext = InstrumentationRegistry.getInstrumentation( )
                    .getTargetContext( );
            Intent result = new Intent( targetContext, CountryDetailActivity.class );
            result.putExtra( "STATE_PARAM_COUNTRY_CODE", "pt" );
            return result;
        }
    };

    @Test
    public void testWebViewIsShown ()
    {
        Espresso.onView( withId( R.id.webView ) )
                .check( matches( isDisplayed( ) ) );

        onWebView( withId( R.id.webView ) ).withTimeout( 1, TimeUnit.MINUTES )
                .check( webMatches( getCurrentUrl( ), containsString( "country.io/portugal" ) ) );

    }
}
