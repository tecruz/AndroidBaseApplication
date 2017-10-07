package co.base.androidbaseapplication.ui.countrydetails;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import co.base.androidbaseapplication.R;
import co.base.androidbaseapplication.injection.component.ActivityComponent;
import co.base.androidbaseapplication.ui.base.BaseFragment;

public class CountryDetailFragment extends BaseFragment
{

    private static final String INSTANCE_EXTRA_PARAM_COUNTRY_NAME
            = "STATE_PARAM_COUNTRY_NAME";

    private String mCountryName;

    private OnCountryDetailsFragmentInteractionListener mListener;

    @BindView(R.id.webView)
    WebView mCountryDetailsView;

    public CountryDetailFragment ()
    {
    }

    public static CountryDetailFragment newInstance (String countryName)
    {
        CountryDetailFragment countryDetailFragment = new CountryDetailFragment( );
        Bundle args = new Bundle( );
        args.putString( INSTANCE_EXTRA_PARAM_COUNTRY_NAME, countryName );
        countryDetailFragment.setArguments( args );
        return countryDetailFragment;
    }

    @Override
    protected void injectDependencies (ActivityComponent component)
    {
        component.inject( this );
    }

    @Override
    protected int getFragmentLayout ()
    {
        return R.layout.fragment_country_detail;
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated( view, savedInstanceState );
        this.mCountryName = getArguments( ).getString( INSTANCE_EXTRA_PARAM_COUNTRY_NAME );
        mCountryDetailsView.setWebViewClient( new WebViewClient( ) );
        mCountryDetailsView.getSettings( ).setJavaScriptEnabled( true );
        mListener.showLoading( );

        mCountryDetailsView.loadUrl( getString( R.string.COUNTRY_INFO_URL,
                mCountryName.toLowerCase( ).replace( " ", "-" ) ) );

        mListener.hideLoading( );
    }

    @Override
    public void onAttach (Context context)
    {
        super.onAttach( context );
        if ( context instanceof CountryDetailFragment.OnCountryDetailsFragmentInteractionListener )
        {
            mListener =
                    ( CountryDetailFragment.OnCountryDetailsFragmentInteractionListener ) context;
        } else
        {
            throw new RuntimeException( context.toString( )
                    + " must implement OnCountryDetailsFragmentInteractionListener" );
        }
    }

    @Override
    public void onDetach ()
    {
        super.onDetach( );
        mListener = null;
    }

    public interface OnCountryDetailsFragmentInteractionListener
    {
        void hideLoading ();

        void showLoading ();
    }
}
