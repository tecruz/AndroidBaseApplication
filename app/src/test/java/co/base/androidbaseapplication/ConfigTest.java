package co.base.androidbaseapplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ConfigTest {
    
    @Test
    public void testGitHubUrlConfig ()
    {
        assertEquals("https://github.com/tecruz/AndroidBaseApplication", Config.GITHUB_URL);
    }

    @Test
    public void testSettingKeyLastSync(){
        assertEquals("last_sync", Config.SETTINGS_KEY_LAST_SYNC);
    }
}
