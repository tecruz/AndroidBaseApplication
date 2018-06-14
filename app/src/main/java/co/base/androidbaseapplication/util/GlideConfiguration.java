package co.base.androidbaseapplication.util;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;

import co.base.androidbaseapplication.util.svg.SvgDecoder;
import co.base.androidbaseapplication.util.svg.SvgDrawableTranscoder;

@GlideModule
public final class GlideConfiguration extends AppGlideModule
{

    @Override
    public void applyOptions (Context context, GlideBuilder builder)
    {
        // Apply options to the builder here.
        builder.setDefaultRequestOptions(new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888));
    }

    @Override
    public void registerComponents (@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry)
    {
        registry.register(SVG.class, PictureDrawable.class, new SvgDrawableTranscoder())
                .append(InputStream.class, SVG.class, new SvgDecoder());
    }
}
