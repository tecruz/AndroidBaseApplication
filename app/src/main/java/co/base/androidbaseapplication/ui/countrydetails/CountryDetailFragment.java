package co.base.androidbaseapplication.ui.countrydetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import co.base.androidbaseapplication.R;
import co.base.androidbaseapplication.ui.base.BaseActivity;

public class CountryDetailFragment extends Fragment implements CountryDetailMvpView {

    private static final String INSTANCE_EXTRA_PARAM_COUNTRY_CODE
            = "STATE_PARAM_COUNTRY_CODE";

    private String mCountryCode;

    @BindView(R.id.webView)
    WebView mCountryDetailsView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    private Unbinder mUnbinder;

    @Inject
    CountryDetailPresenter mCountryDetailPresenter;

    public CountryDetailFragment() {
        setRetainInstance(true);
    }

    public static CountryDetailFragment newInstance(String countryCode) {
        CountryDetailFragment countryDetailFragment = new CountryDetailFragment();
        Bundle args = new Bundle();
        args.putString(INSTANCE_EXTRA_PARAM_COUNTRY_CODE, countryCode);
        countryDetailFragment.setArguments(args);
        return countryDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View fragmentView =
                inflater.inflate(R.layout.fragment_country_detail, container, false);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
        mUnbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCountryDetailPresenter.attachView(this);
        this.mCountryCode = getArguments().getString(INSTANCE_EXTRA_PARAM_COUNTRY_CODE);
        mCountryDetailsView.getSettings().setJavaScriptEnabled(true);

        mCountryDetailsView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                if (getActivity() != null) {
                    getActivity().setProgress(progress * 1000);
                    if (progress == 100) {
                        mCountryDetailPresenter.hideLoading();
                    }
                }
            }
        });

        mCountryDetailPresenter.showLoading();

        mCountryDetailsView.loadUrl("http://www.geognos.com/geo/en/cc/"
                + this.mCountryCode.toLowerCase() + ".html");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mCountryDetailPresenter.detachView();
    }

    @Override
    public void showLoadingView() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mProgressBar.setVisibility(View.GONE);
    }
}
