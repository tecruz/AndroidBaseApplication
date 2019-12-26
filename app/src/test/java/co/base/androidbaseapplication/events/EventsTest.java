package co.base.androidbaseapplication.events;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EventsTest {

    @Test
    public void testEventDescription(){
        Events event = new Events(Events.SYNC_COMPLETED);
        assertEquals(event.getDescription(), Events.SYNC_COMPLETED);

        event = new Events(Events.SYNC_ERROR);
        assertEquals(event.getDescription(), Events.SYNC_ERROR);

        event = new Events("other");
        assertEquals(event.getDescription(), "other");
    }
}
