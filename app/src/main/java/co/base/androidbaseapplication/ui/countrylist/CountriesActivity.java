package co.base.androidbaseapplication.ui.countrylist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import co.base.androidbaseapplication.Config;
import co.base.androidbaseapplication.R;
import co.base.androidbaseapplication.events.Events;
import co.base.androidbaseapplication.services.SyncService;
import co.base.androidbaseapplication.model.entities.Country;
import co.base.androidbaseapplication.ui.base.BaseActivity;
import co.base.androidbaseapplication.ui.countrydetails.CountryDetailActivity;
import co.base.androidbaseapplication.util.DialogFactory;
import co.base.androidbaseapplication.util.PreferencesUtil;

public class CountriesActivity extends BaseActivity implements CountriesMvpView {

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "co.base.androidbaseapplication.ui.main.CountriesActivity.EXTRA_TRIGGER_SYNC_FLAG";

    @Inject
    CountriesPresenter mCountriesPresenter;
    @Inject CountriesAdapter mCountriesAdapter;
    @Inject PreferencesUtil mPreferencesUtil;

    @Bind(R.id.countries_recycler_view)
    RecyclerView mRecyclerView;


    /**
     * Return an Intent to start this Activity.
     * triggerDataSyncOnCreate allows disabling the background sync service onCreate. Should
     * only be set to false during testing.
     */
    public static Intent getStartIntent(Context context, boolean triggerDataSyncOnCreate) {
        Intent intent = new Intent(context, CountriesActivity.class);
        intent.putExtra(EXTRA_TRIGGER_SYNC_FLAG, triggerDataSyncOnCreate);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
        setupToolBar();

        mCountriesAdapter.setOnItemClickListener(mOnItemClickListener);

        mRecyclerView.setAdapter(mCountriesAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCountriesPresenter.attachView(this);
        mCountriesPresenter.loadCountries();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(Events.SYNC_COMPLETED.toString()));

        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = mPreferencesUtil.getLastSyncTimestamp();

        boolean expired = ((currentTime - lastUpdateTime) > Config.EXPIRATION_TIME);

        if (expired) {
            startService(SyncService.getStartIntent(this));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCountriesPresenter.detachView();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    /*****
     * MVP View methods implementation
     *****/

    @Override
    public void showCountries(List<Country> countries) {
        mCountriesAdapter.setCountries(countries);
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_countries))
                .show();
    }

    @Override
    public void countryItemClicked(Country country) {
        Intent intentToLaunch = CountryDetailActivity.getCallingIntent(getApplicationContext(),
                country.getmCountryCode());
        startActivity(intentToLaunch);
    }

    @Override
    public void showCountriesEmpty() {
        mCountriesAdapter.setCountries(Collections.<Country>emptyList());
        mCountriesAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.empty_countries, Toast.LENGTH_LONG).show();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mCountriesPresenter.loadCountries();
        }
    };

    private CountriesAdapter.OnItemClickListener mOnItemClickListener =
            new CountriesAdapter.OnItemClickListener() {
                @Override
                public void onCountryItemClicked(Country country) {
                    if (CountriesActivity.this.mCountriesPresenter != null && country != null) {
                        CountriesActivity.this.mCountriesPresenter.onCountryClicked(country);
                    }
                }
            };
}
