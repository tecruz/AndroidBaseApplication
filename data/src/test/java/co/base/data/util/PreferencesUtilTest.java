package co.base.data.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import co.base.data.Config;

import static co.base.data.util.PreferencesUtil.PREF_FILE_NAME;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PreferencesUtilTest {

    @Mock
    private Application application;

    @Mock
    private Context context;

    @Mock
    private PreferencesUtil preferencesUtil;

    @Mock
    private SharedPreferences sharedPreferences;

    @Mock
    private SharedPreferences.Editor sharedPreferencesEditor;

    @Before
    public void setUp(){
        when(application.getApplicationContext()).thenReturn(context);
        when(context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)).thenReturn(sharedPreferences);
        when(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor);
        preferencesUtil = new PreferencesUtil(application);
    }

    @Test
    public void testPreferencesClear ()
    {
        when(sharedPreferences.edit().clear()).thenReturn(sharedPreferencesEditor);

        preferencesUtil.clear();

        verify(sharedPreferencesEditor).clear();
    }

    @Test
    public void testLastSyncTimeStampGet(){
        when(sharedPreferences.edit( ).putLong( Config.SETTINGS_KEY_LAST_SYNC, 1L )).thenReturn(sharedPreferencesEditor);
        when(sharedPreferences.getLong( Config.SETTINGS_KEY_LAST_SYNC, 0 )).thenReturn(1L);

        assertEquals(1L, preferencesUtil.getLastSyncTimestamp());
    }

    @Test
    public void testLastSyncTimeStampSet(){
        when(sharedPreferences.edit( ).putLong( Config.SETTINGS_KEY_LAST_SYNC, 1L )).thenReturn(sharedPreferencesEditor);

        preferencesUtil.setLastSyncTimestamp(1L);
        verify(sharedPreferencesEditor).putLong(Config.SETTINGS_KEY_LAST_SYNC, 1L);
    }
}
