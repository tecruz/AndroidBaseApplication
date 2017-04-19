package co.base.androidbaseapplication.ui.custom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import co.base.androidbaseapplication.Config;
import co.base.androidbaseapplication.R;

public class NavigationDrawerFragment extends Fragment
{

    private DrawerLayout drawerLayout;
    private NavigationView navigationViewDrawer;


    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState)
    {
        navigationViewDrawer = ( NavigationView ) inflater.inflate(
                R.layout.fragment_navigation_drawer, container, false );
        return navigationViewDrawer;
    }

    public void setUp (int fragmentId, DrawerLayout drawerLayout)
    {

        this.drawerLayout = drawerLayout;
        // Setup drawer view
        setupDrawerContent( );
    }

    private void setupDrawerContent ()
    {
        navigationViewDrawer.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener( )
                {
                    @Override
                    public boolean onNavigationItemSelected (MenuItem menuItem)
                    {
                        selectDrawerItem( menuItem );
                        return true;
                    }
                } );
    }

    public void selectDrawerItem (MenuItem menuItem)
    {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch ( menuItem.getItemId( ) )
        {
            case R.id.nav_github:
                //fragmentClass = FirstFragment.class;
                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( Config.GITHUB_URL ) );
                startActivity( intent );
                break;
            default:
                break;
        }

        /*try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();*/

        // Highlight the selected item has been done by NavigationView
        //menuItem.setChecked(true);
        // Set action bar title
        //setTitle(menuItem.getTitle());
        // Close the navigation drawer
        //drawerLayout.closeDrawer(GravityCompat.START);
    }

}
