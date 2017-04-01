package co.base.androidbaseapplication.ui.countrylist;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import co.base.androidbaseapplication.R;
import co.base.androidbaseapplication.services.SyncService;
import co.base.androidbaseapplication.ui.countrydetails.CountryDetailFragment;
import co.base.androidbaseapplication.ui.entity.Country;
import co.base.androidbaseapplication.ui.base.BaseActivity;
import co.base.androidbaseapplication.util.DialogFactory;
import co.base.androidbaseapplication.util.PreferencesUtil;
import co.base.androidbaseapplication.util.ViewUtil;

public class CountriesActivity extends BaseActivity implements CountriesMvpView,
        CountryDetailFragment.OnCountryDetailsFragmentInteractionListener {

    @Inject CountriesPresenter mCountriesPresenter;
    @Inject CountriesAdapter mCountriesAdapter;
    @Inject PreferencesUtil mPreferencesUtil;

    @BindView(R.id.countries_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.rl_retry)
    ViewGroup mRetryView;

    @BindView(R.id.bt_retry)
    Button mRetryBtn;

    @BindView(R.id.rl_progress)
    ViewGroup mProgressView;


    /**
     * Return an Intent to start this Activity.
     *
     */
    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, CountriesActivity.class);
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

        mCountriesPresenter.loadCountries();
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
        if (getResources().getBoolean(R.bool.isTablet)
                && ViewUtil.screenOrientation(this) == Configuration.ORIENTATION_LANDSCAPE) {
            replaceFragment(R.id.fragmentContainer,
                    CountryDetailFragment.newInstance(country.getCountryCode()), false);
        } else {
            mNavigator.navigateToCountryDetails(this, country.getCountryCode());
        }
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
