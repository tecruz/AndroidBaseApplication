package co.base.androidbaseapplication.ui.countrylist;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.base.androidbaseapplication.R;
import co.base.androidbaseapplication.injection.ApplicationContext;
import co.base.androidbaseapplication.model.entities.Country;
import co.base.androidbaseapplication.ui.custom.ImageLoadProgressBar;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountryViewHolder> {

    Context mApplicationContext;

    private List<Country> mCountries;
    private int mThemeColor;

    public interface OnItemClickListener {
        void onCountryItemClicked(Country country);
    }

    private OnItemClickListener mOnItemClickListener;

    @Inject
    public CountriesAdapter(@ApplicationContext Context applicationContext) {
        mCountries = new ArrayList<>();
        mApplicationContext = applicationContext;
        mThemeColor = ContextCompat.getColor(mApplicationContext, R.color.colorPrimary);
    }

    public void setCountries(List<Country> countries) {
        validateCountriesCollection(countries);
        mCountries = countries;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private void validateCountriesCollection(List<Country> countriesCollection) {
        if (countriesCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_country, parent, false);
        return new CountryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CountryViewHolder holder, int position) {
        final Country country = mCountries.get(position);
        holder.nameTextView.setText(country.getmCountryName());
        Uri uri = Uri.parse(mApplicationContext.getString(R.string.IMAGE_BASE_URL)
                + country.getmCountryCode() + ".png");
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setRoundAsCircle(true);
        roundingParams.setBorder(mThemeColor, 10.0f);
        holder.flagView.getHierarchy().setRoundingParams(roundingParams);
        holder.flagView.getHierarchy()
                .setProgressBarImage(new ImageLoadProgressBar(mApplicationContext));
        holder.flagView.setImageURI(uri);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CountriesAdapter.this.mOnItemClickListener != null) {
                    CountriesAdapter.this.mOnItemClickListener.onCountryItemClicked(country);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mCountries.size();
    }

    class CountryViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.view_flag) SimpleDraweeView flagView;
        @Bind(R.id.text_name) TextView nameTextView;

        public CountryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
