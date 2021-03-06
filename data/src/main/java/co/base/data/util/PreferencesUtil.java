package co.base.data.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.base.data.Config;

@Singleton
public class PreferencesUtil
{

    public static final String PREF_FILE_NAME = "android_boilerplate_pref_file";

    private final SharedPreferences sharedPreferences;

    @Inject
    public PreferencesUtil (Application context)
    {
        sharedPreferences = context.getApplicationContext( ).getSharedPreferences( PREF_FILE_NAME,
                Context.MODE_PRIVATE );
    }

    public long getLastSyncTimestamp ()
    {
        return sharedPreferences.getLong( Config.SETTINGS_KEY_LAST_SYNC, 0 );
    }

    public void setLastSyncTimestamp (long syncTimestamp)
    {
        sharedPreferences.edit( ).putLong( Config.SETTINGS_KEY_LAST_SYNC, syncTimestamp ).apply( );
    }

    public void clear ()
    {
        sharedPreferences.edit( ).clear( ).apply( );
    }

}
