package co.base.androidbaseapplication.ui.countrylist;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import co.base.androidbaseapplication.R;
import co.base.androidbaseapplication.services.SyncService;
import co.base.androidbaseapplication.ui.countrydetails.CountryDetailFragment;
import co.base.androidbaseapplication.ui.entity.Country;
import co.base.androidbaseapplication.ui.base.BaseActivity;
import co.base.androidbaseapplication.util.DialogFactory;
import co.base.androidbaseapplication.util.NetworkUtil;
import co.base.androidbaseapplication.util.PreferencesUtil;
import co.base.androidbaseapplication.util.ViewUtil;

public class CountriesActivity extends BaseActivity implements CountriesMvpView,
        CountryDetailFragment.OnCountryDetailsFragmentInteractionListener
{

    @Inject
    CountriesPresenter countriesPresenter;

    @Inject
    CountriesAdapter countriesAdapter;

    @Inject
    PreferencesUtil preferencesUtil;

    @BindView(R.id.countries_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.rl_progress)
    ViewGroup progressView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.empty_countries_label)
    TextView emptyCountriesLabel;

    /**
     * Return an Intent to start this Activity.
     */
    public static Intent getCallingIntent (Context context)
    {
        Intent intent = new Intent( context, CountriesActivity.class );
        return intent;
    }

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        getActivityComponent( ).inject( this );
        setContentView( R.layout.activity_main );
        setupToolBar( );
        setupDrawer( );

        swipeRefreshLayout.setOnRefreshListener( onRefreshListener );

        countriesAdapter.setOnItemClickListener( mOnItemClickListener );

        recyclerView.setAdapter( countriesAdapter );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );

        countriesPresenter.attachView( this );

        countriesPresenter.loadCountries( );
    }

    @Override
    protected void onResume ()
    {
        super.onResume( );
        countriesPresenter.resume( );
    }

    @Override
    protected void onPause ()
    {
        super.onPause( );
        countriesPresenter.pause( );
    }

    @Override
    protected void onDestroy ()
    {
        super.onDestroy( );
        countriesPresenter.detachView( );

    }

    /*****
     * MVP View methods implementation
     *****/

    @Override
    public void showCountries (List<Country> countries)
    {
        countriesAdapter.setCountries( countries );
    }

    @Override
    public void showError ()
    {
        DialogFactory.createGenericErrorDialog( this,
                getString( R.string.error_loading_countries ) ).show( );
        swipeRefreshLayout.setRefreshing( false );
    }

    @Override
    public void countryItemClicked (Country country)
    {
        if ( getResources( ).getBoolean( R.bool.isTablet )
                && ViewUtil.screenOrientation( this ) == Configuration.ORIENTATION_LANDSCAPE )
        {
            replaceFragment( R.id.fragmentContainer,
                    CountryDetailFragment.newInstance( country.getCountryName( ) ), false );
        } else
        {
            navigator.navigateToCountryDetails( this, country.getCountryName( ) );
        }
    }

    @Override
    public void showCountriesEmpty ()
    {
        countriesAdapter.setCountries( Collections.<Country>emptyList( ) );
        countriesAdapter.notifyDataSetChanged( );
        emptyCountriesLabel.setVisibility( View.VISIBLE );
        swipeRefreshLayout.setRefreshing( false );
    }

    @Override
    public void showLoading ()
    {
        progressView.setVisibility( View.VISIBLE );
    }

    @Override
    public void hideLoading ()
    {
        progressView.setVisibility( View.GONE );
    }

    @Override
    public void hideEmptyLabel ()
    {
        emptyCountriesLabel.setVisibility( View.GONE );
        swipeRefreshLayout.setRefreshing( false );
    }

    @Override
    public void showEmptyLabel ()
    {
        emptyCountriesLabel.setVisibility( View.VISIBLE );
        swipeRefreshLayout.setRefreshing( false );
    }

    private CountriesAdapter.OnItemClickListener mOnItemClickListener = new CountriesAdapter
            .OnItemClickListener( )
    {
        @Override
        public void onCountryItemClicked (Country country)
        {
            if ( CountriesActivity.this.countriesPresenter != null && country != null )
            {
                CountriesActivity.this.countriesPresenter.onCountryClicked( country );
            }
        }
    };


    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout
            .OnRefreshListener( )
    {
        @Override
        public void onRefresh ()
        {
            SyncService.startService( CountriesActivity.this );
            if ( !NetworkUtil.isNetworkConnected( CountriesActivity.this ) )
            {
                swipeRefreshLayout.setRefreshing( false );
            }
        }
    };
}
