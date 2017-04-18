package co.base.androidbaseapplication.util;

import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.Target;

import javax.sql.DataSource;

/**
 * Listener which updates the {@link ImageView} to be software rendered, because
 * {@link com.caverock.androidsvg.SVG SVG}/{@link android.graphics.Picture Picture} can't render on
 * a hardware backed {@link android.graphics.Canvas Canvas}.
 */
public class SvgSoftwareLayerSetter<T> implements RequestListener<T, PictureDrawable> {


    @Override
    public boolean onException (Exception e, Object model, Target target, boolean isFirstResource)
    {
        ImageView view = ((ImageViewTarget<?>) target).getView();
        if (Build.VERSION_CODES.HONEYCOMB <= Build.VERSION.SDK_INT) {
            view.setLayerType(ImageView.LAYER_TYPE_NONE, null);
        }
        return false;
    }

    @Override
    public boolean onResourceReady (PictureDrawable resource, T model, Target<PictureDrawable> target, boolean isFromMemoryCache, boolean isFirstResource)
    {
        ImageView view = ((ImageViewTarget<?>) target).getView();
        if (Build.VERSION_CODES.HONEYCOMB <= Build.VERSION.SDK_INT) {
            view.setLayerType(ImageView.LAYER_TYPE_SOFTWARE, null);
        }
        return false;
    }
}
