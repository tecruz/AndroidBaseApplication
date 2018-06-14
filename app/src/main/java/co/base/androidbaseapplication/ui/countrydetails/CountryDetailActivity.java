package co.base.androidbaseapplication.ui.countrydetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.BindView;
import co.base.androidbaseapplication.R;
import co.base.androidbaseapplication.ui.base.BaseActivity;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class CountryDetailActivity extends BaseActivity implements CountryDetailMvpView,
        CountryDetailFragment.OnCountryDetailsFragmentInteractionListener, HasSupportFragmentInjector
{
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    private static final String INSTANCE_EXTRA_PARAM_COUNTRY_CODE
            = "STATE_PARAM_COUNTRY_CODE";

    public static Intent getCallingIntent (Context context, String countryCode)
    {
        Intent callingIntent = new Intent( context, CountryDetailActivity.class );
        callingIntent.putExtra( INSTANCE_EXTRA_PARAM_COUNTRY_CODE, countryCode );
        return callingIntent;
    }

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Inject
    CountryDetailPresenter countryDetailPresenter;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
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

        countryDetailPresenter.attachView( this );
    }

    @Override
    protected void onResume ()
    {
        super.onResume( );
        countryDetailPresenter.resume( );
    }

    @Override
    protected void onPause ()
    {
        super.onPause( );
        countryDetailPresenter.pause( );
    }

    @Override
    protected void onDestroy ()
    {
        super.onDestroy( );
        countryDetailPresenter.detachView( );

    }

    /*****
     * MVP View methods implementation
     *****/

    @Override
    public void showLoadingView ()
    {
        progressBar.setVisibility( View.VISIBLE );
    }

    @Override
    public void hideLoadingView ()
    {
        progressBar.setVisibility( View.GONE );
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

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector ()
    {
        return fragmentDispatchingAndroidInjector;
    }
}
