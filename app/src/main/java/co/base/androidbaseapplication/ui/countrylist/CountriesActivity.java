package co.base.androidbaseapplication.ui.countrylist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import co.base.androidbaseapplication.Config;
import co.base.androidbaseapplication.R;
import co.base.androidbaseapplication.services.SyncService;
import co.base.androidbaseapplication.model.entities.Country;
import co.base.androidbaseapplication.ui.base.BaseActivity;
import co.base.androidbaseapplication.ui.countrydetails.CountryDetailActivity;
import co.base.androidbaseapplication.util.DialogFactory;
import co.base.androidbaseapplication.util.PreferencesUtil;

public class CountriesActivity extends BaseActivity implements CountriesMvpView {

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "co.base.androidbaseapplication.ui.main.CountriesActivity.EXTRA_TRIGGER_SYNC_FLAG";

    @Inject CountriesPresenter mCountriesPresenter;
    @Inject CountriesAdapter mCountriesAdapter;
    @Inject PreferencesUtil mPreferencesUtil;

    @BindView(R.id.countries_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.rl_retry)
    RelativeLayout mRetryView;
    @BindView(R.id.bt_retry)
    Button mRetryBtn;
    @BindView(R.id.rl_progress)
    RelativeLayout mProgressView;


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
        setupDrawer();

        mCountriesAdapter.setOnItemClickListener(mOnItemClickListener);

        mRecyclerView.setAdapter(mCountriesAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCountriesPresenter.attachView(this);

        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = mPreferencesUtil.getLastSyncTimestamp();

        boolean expired = ((currentTime - lastUpdateTime) > Config.EXPIRATION_TIME);

        if (expired) {
            hideRetry();
            showLoading();
            startService(SyncService.getStartIntent(this));
        } else {
            mCountriesPresenter.loadCountries();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCountriesPresenter.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCountriesPresenter.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCountriesPresenter.detachView();

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

    @Override
    public void showRetry() {
        mRetryView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        mRetryView.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        mProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressView.setVisibility(View.GONE);
    }

    private CountriesAdapter.OnItemClickListener mOnItemClickListener =
            new CountriesAdapter.OnItemClickListener() {
                @Override
                public void onCountryItemClicked(Country country) {
                    if (CountriesActivity.this.mCountriesPresenter != null && country != null) {
                        CountriesActivity.this.mCountriesPresenter.onCountryClicked(country);
                    }
                }
            };

    @OnClick(R.id.bt_retry) void onButtonRetryClick() {
        startService(SyncService.getStartIntent(this));
    }
}
