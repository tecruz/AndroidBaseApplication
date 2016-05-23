package co.base.androidbaseapplication.ui.countrylist;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import co.base.androidbaseapplication.R;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CountriesActivityTest {

    @Rule
    public ActivityTestRule<CountriesActivity> mActivityRule = new ActivityTestRule<>(
            CountriesActivity.class);

    @Test
    public void testRecyclerViewIsShown () {
        Espresso.onView(withId(R.id.countries_recycler_view))
                .check(matches(isDisplayed()));

    }
}
