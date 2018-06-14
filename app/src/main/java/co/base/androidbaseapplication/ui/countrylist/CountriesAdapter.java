package co.base.androidbaseapplication.ui.countrylist;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.base.androidbaseapplication.R;
import co.base.androidbaseapplication.ui.entity.Country;
import co.base.androidbaseapplication.util.GlideApp;
import co.base.androidbaseapplication.util.svg.SvgSoftwareLayerSetter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>
{

    private Context context;
    private List<Country> countryList;
    private RequestBuilder<PictureDrawable> requestBuilder;

    public interface OnItemClickListener
    {
        void onCountryItemClicked (Country country);
    }

    private OnItemClickListener mOnItemClickListener;

    public CountriesAdapter (Context context)
    {
        countryList = new ArrayList<>( );
        this.context = context;
        requestBuilder = GlideApp.with( this.context )
                .as( PictureDrawable.class )
                .placeholder( R.mipmap.ic_launcher )
                .error( android.R.drawable.stat_notify_error )
                .transition( withCrossFade( ) )
                .listener( new SvgSoftwareLayerSetter( ) );
    }

    public void setCountries (List<Country> countries)
    {
        validateCountriesCollection( countries );
        countryList = countries;
        this.notifyDataSetChanged( );
    }

    public void setOnItemClickListener (OnItemClickListener onItemClickListener)
    {
        this.mOnItemClickListener = onItemClickListener;
    }

    private void validateCountriesCollection (List<Country> countriesCollection)
    {
        if ( countriesCollection == null )
        {
            throw new IllegalArgumentException( "The list cannot be null" );
        }
    }

    @Override
    public CountryViewHolder onCreateViewHolder (ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from( parent.getContext( ) )
                .inflate( R.layout.item_country, parent, false );
        return new CountryViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder (final CountryViewHolder holder, int position)
    {
        final Country country = countryList.get( position );
        holder.nameTextView.setText( country.getCountryName( ) );
        Uri uri = Uri.parse( context.getString( R.string.IMAGE_BASE_URL,
                country.getFlagCountryCode( ).toLowerCase( ) ) );

        requestBuilder.load( uri ).into( holder.flagView );

        holder.itemView.setOnClickListener( new View.OnClickListener( )
        {
            @Override
            public void onClick (View v)
            {
                if ( CountriesAdapter.this.mOnItemClickListener != null )
                {
                    CountriesAdapter.this.mOnItemClickListener.onCountryItemClicked( country );
                }
            }
        } );
    }


    @Override
    public int getItemCount ()
    {
        return countryList.size( );
    }

    class CountryViewHolder extends RecyclerView.ViewHolder
    {

        @BindView(R.id.view_flag)
        ImageView flagView;
        @BindView(R.id.text_name)
        TextView nameTextView;

        public CountryViewHolder (View itemView)
        {
            super( itemView );
            ButterKnife.bind( this, itemView );
        }
    }
}
