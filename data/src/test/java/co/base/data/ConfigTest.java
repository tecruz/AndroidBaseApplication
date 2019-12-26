package co.base.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ConfigTest {
    
    @Test
    public void testDataExpirationTime ()
    {
        assertEquals(600000, Config.EXPIRATION_TIME);
    }

    @Test
    public void testDataSettingKeyLastSync(){
        assertEquals("last_sync", Config.SETTINGS_KEY_LAST_SYNC);
    }
}
