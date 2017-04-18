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

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.base.androidbaseapplication.R;
import co.base.androidbaseapplication.injection.ApplicationContext;
import co.base.androidbaseapplication.ui.entity.Country;
import co.base.androidbaseapplication.util.SvgDecoder;
import co.base.androidbaseapplication.util.SvgDrawableTranscoder;
import co.base.androidbaseapplication.util.SvgSoftwareLayerSetter;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>
{

    private Context context;
    private List<Country> countryList;
    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;

    public interface OnItemClickListener
    {
        void onCountryItemClicked (Country country);
    }

    private OnItemClickListener mOnItemClickListener;

    @Inject
    public CountriesAdapter (@ApplicationContext Context applicationContext)
    {
        countryList = new ArrayList<>( );
        context = applicationContext;
        requestBuilder = Glide.with( context )
                .using( Glide.buildStreamModelLoader( Uri.class, context ), InputStream.class )
                .from( Uri.class )
                .as( SVG.class )
                .transcode( new SvgDrawableTranscoder( ), PictureDrawable.class )
                .sourceEncoder( new StreamEncoder( ) )
                .cacheDecoder( new FileToStreamDecoder<SVG>( new SvgDecoder( ) ) )
                .decoder( new SvgDecoder( ) )
                .placeholder( R.mipmap.ic_launcher )
                .error( android.R.drawable.stat_notify_error )
                .animate( android.R.anim.fade_in )
                .listener( new SvgSoftwareLayerSetter<Uri>( ) );
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

        requestBuilder.diskCacheStrategy( DiskCacheStrategy.SOURCE )
                .load( uri )
                .into( holder.flagView );

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
