package co.base.androidbaseapplication.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import co.base.androidbaseapplication.di.component.ActivityComponent;

public abstract class BaseFragment extends Fragment
{

    private Unbinder unbinder;

    protected abstract void injectDependencies (ActivityComponent component);

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState)
    {
        injectDependencies( (( BaseActivity ) getActivity( )).getActivityComponent( ) );
        return inflater.inflate( getFragmentLayout( ), container, false );
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