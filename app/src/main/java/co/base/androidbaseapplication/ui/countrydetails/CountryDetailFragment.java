package co.base.androidbaseapplication.ui.countrydetails;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import co.base.androidbaseapplication.R;
import co.base.androidbaseapplication.ui.base.BaseFragment;

public class CountryDetailFragment extends BaseFragment
{

    private static final String INSTANCE_EXTRA_PARAM_COUNTRY_NAME
            = "STATE_PARAM_COUNTRY_NAME";

    private OnCountryDetailsFragmentInteractionListener listener;

    @BindView(R.id.webView)
    WebView countryDetailsView;

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
    protected int getFragmentLayout ()
    {
        return R.layout.fragment_country_detail;
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated( view, savedInstanceState );
        String countryName = getArguments( ).getString( INSTANCE_EXTRA_PARAM_COUNTRY_NAME );
        countryDetailsView.setWebViewClient( new WebViewClient( ) );
        countryDetailsView.getSettings( ).setJavaScriptEnabled( true );
        listener.showLoading( );

        countryDetailsView.loadUrl( getString( R.string.COUNTRY_INFO_URL,
                countryName.toLowerCase( ).replace( " ", "-" ) ) );

        listener.hideLoading( );
    }

    @Override
    public void onAttach (Context context)
    {
        super.onAttach( context );
        if ( context instanceof CountryDetailFragment.OnCountryDetailsFragmentInteractionListener )
        {
            listener =
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
        listener = null;
    }

    public interface OnCountryDetailsFragmentInteractionListener
    {
        void hideLoading ();

        void showLoading ();
    }
}
