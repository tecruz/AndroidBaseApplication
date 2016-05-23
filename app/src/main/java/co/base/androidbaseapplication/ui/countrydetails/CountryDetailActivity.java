package co.base.androidbaseapplication.ui.countrydetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import co.base.androidbaseapplication.R;
import co.base.androidbaseapplication.ui.base.BaseActivity;

public class CountryDetailActivity extends BaseActivity  {

    private static final String INSTANCE_EXTRA_PARAM_COUNTRY_CODE
            = "STATE_PARAM_COUNTRY_CODE";

    public static Intent getCallingIntent(Context context, String mCountryCode) {
        Intent callingIntent = new Intent(context, CountryDetailActivity.class);
        callingIntent.putExtra(INSTANCE_EXTRA_PARAM_COUNTRY_CODE, mCountryCode);
        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);
        setupToolBar();

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);


        addFragment(R.id.fragmentContainer,
                CountryDetailFragment.newInstance(getIntent()
                        .getStringExtra(INSTANCE_EXTRA_PARAM_COUNTRY_CODE)), false);
    }

}
