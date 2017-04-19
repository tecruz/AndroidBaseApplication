package co.base.androidbaseapplication.ui.countrydetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.BindView;
import co.base.androidbaseapplication.R;
import co.base.androidbaseapplication.ui.base.BaseActivity;

public class CountryDetailActivity extends BaseActivity implements CountryDetailMvpView,
        CountryDetailFragment.OnCountryDetailsFragmentInteractionListener
{

    private static final String INSTANCE_EXTRA_PARAM_COUNTRY_CODE
            = "STATE_PARAM_COUNTRY_CODE";

    public static Intent getCallingIntent (Context context, String countryCode)
    {
        Intent callingIntent = new Intent( context, CountryDetailActivity.class );
        callingIntent.putExtra( INSTANCE_EXTRA_PARAM_COUNTRY_CODE, countryCode );
        return callingIntent;
    }

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @Inject
    CountryDetailPresenter mCountryDetailPresenter;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        getActivityComponent( ).inject( this );
        setContentView( R.layout.activity_country_detail );
        setupToolBar( );

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar( );

        // Enable the Up button
        if ( ab != null )
            ab.setDisplayHomeAsUpEnabled( true );

        replaceFragment( R.id.fragmentContainer,
                CountryDetailFragment.newInstance( getIntent( )
                        .getStringExtra( INSTANCE_EXTRA_PARAM_COUNTRY_CODE ) ), false );

        mCountryDetailPresenter.attachView( this );
    }

    @Override
    protected void onResume ()
    {
        super.onResume( );
        mCountryDetailPresenter.resume( );
    }

    @Override
    protected void onPause ()
    {
        super.onPause( );
        mCountryDetailPresenter.pause( );
    }

    @Override
    protected void onDestroy ()
    {
        super.onDestroy( );
        mCountryDetailPresenter.detachView( );

    }

    /*****
     * MVP View methods implementation
     *****/

    @Override
    public void showLoadingView ()
    {
        mProgressBar.setVisibility( View.VISIBLE );
    }

    @Override
    public void hideLoadingView ()
    {
        mProgressBar.setVisibility( View.GONE );
    }

    @Override
    public void hideLoading ()
    {
        hideLoadingView( );
    }

    @Override
    public void showLoading ()
    {
        showLoadingView( );
    }
}
