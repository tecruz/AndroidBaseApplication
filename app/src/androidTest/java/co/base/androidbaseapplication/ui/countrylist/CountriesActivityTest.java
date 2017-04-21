package co.base.androidbaseapplication.ui.countrylist;

import android.graphics.drawable.ColorDrawable;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import co.base.androidbaseapplication.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class CountriesActivityTest
{

    @Rule
    public ActivityTestRule<CountriesActivity> activityTestRule = new ActivityTestRule<>(
            CountriesActivity.class );

    @Test
    public void testToolbarDesign ()
    {
        //check toolbar is displayed
        onView( withId( R.id.my_toolbar ) ).check( matches( isDisplayed( ) ) );

        //check if toolbar has app name
        onView( withText( R.string.app_name ) )
                .check( matches( withParent( withId( R.id.my_toolbar ) ) ) );

        //check toolbar background primary color
        onView( withId( R.id.my_toolbar ) ).check( matches( withToolbarBackGroundColor( ) ) );
    }

    @Test
    public void testRecyclerViewIsShown ()
    {
        onView( withId( R.id.countries_recycler_view ) ).check( matches( isDisplayed( ) ) );

    }

    private Matcher<? super View> withToolbarBackGroundColor ()
    {
        return new BoundedMatcher<View, View>( View.class )
        {
            @Override
            public boolean matchesSafely (View view)
            {
                final ColorDrawable backgroundColor = ( ColorDrawable ) view.getBackground( );

                return ContextCompat
                        .getColor( activityTestRule.getActivity( ), R.color.colorPrimary ) ==
                        backgroundColor.getColor( );
            }

            @Override
            public void describeTo (Description description)
            {
            }
        };
    }
}
