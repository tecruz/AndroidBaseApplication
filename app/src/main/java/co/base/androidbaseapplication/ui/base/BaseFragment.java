package co.base.androidbaseapplication.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment extends Fragment
{
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate( getFragmentLayout( ), container, false );
    }

    @Override
    public void onAttach (Context context)
    {
        AndroidSupportInjection.inject( this );
        super.onAttach( context );
    }

    protected abstract int getFragmentLayout ();

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated( view, savedInstanceState );
        unbinder = ButterKnife.bind( this, view );
    }


    @Override
    public void onDestroyView ()
    {
        super.onDestroyView( );
        unbinder.unbind( );
    }
}