package co.base.androidbaseapplication.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.base.androidbaseapplication.AndroidBaseApplication;
import co.base.androidbaseapplication.R;
import co.base.androidbaseapplication.injection.component.ActivityComponent;
import co.base.androidbaseapplication.injection.component.DaggerActivityComponent;
import co.base.androidbaseapplication.injection.module.ActivityModule;
import co.base.androidbaseapplication.ui.navigation.Navigator;
import co.base.androidbaseapplication.ui.custom.NavigationDrawerFragment;

public class BaseActivity extends AppCompatActivity
{

    private ActivityComponent activityComponent;
    private NavigationDrawerFragment navigationDrawerFragment;

    @BindView(R.id.my_toolbar)
    protected Toolbar toolbar;

    @Nullable
    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawer;

    @Inject
    protected Navigator navigator;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
    }

    @Override
    public void setContentView (int layoutResID)
    {
        super.setContentView( layoutResID );
        ButterKnife.bind( this );
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId( );

        switch ( id )
        {
            case android.R.id.home:
                onBackPressed( );
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onBackPressed ()
    {
        if ( drawer != null && drawer.isDrawerOpen( GravityCompat.START ) )
        {
            drawer.closeDrawer( GravityCompat.START );
        } else
        {
            super.onBackPressed( );
        }
    }


    public ActivityComponent getActivityComponent ()
    {
        if ( activityComponent == null )
        {
            activityComponent = DaggerActivityComponent.builder( )
                    .applicationComponent( AndroidBaseApplication.get( this ).getComponent( ) )
                    .build( );
        }
        return activityComponent;
    }

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment        The fragment to be replaced.
     */
    protected void replaceFragment (int containerViewId, Fragment fragment, boolean addToBackStack)
    {
        FragmentTransaction fragmentTransaction =
                this.getSupportFragmentManager( ).beginTransaction( );
        fragmentTransaction.replace( containerViewId, fragment );
        if ( addToBackStack )
            fragmentTransaction.addToBackStack( null );
        fragmentTransaction.commit( );
    }


    protected void setupToolBar ()
    {
        toolbar.setTitle( R.string.app_name );
        setSupportActionBar( toolbar );
    }

    protected void setupDrawer ()
    {
        navigationDrawerFragment = ( NavigationDrawerFragment )
                getSupportFragmentManager( ).findFragmentById( R.id.navigation_drawer );

        navigationDrawerFragment.setUp( R.id.navigation_drawer,
                ( DrawerLayout ) findViewById( R.id.drawer_layout ) );

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, ( DrawerLayout ) findViewById( R.id.drawer_layout ),
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState( );
    }
}
