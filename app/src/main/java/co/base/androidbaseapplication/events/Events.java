package co.base.androidbaseapplication.events;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Events
{
    // Constants
    public static final String SYNC_COMPLETED = "sync-completed";
    public static final String SYNC_ERROR = "sync-error";
    private String event;

    public Events (@Event String event)
    {
        this.event = event;
    }

    // Declare the @ StringDef for these constants:
    @StringDef({SYNC_COMPLETED, SYNC_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Event
    {
    }

    @Event
    public String getDescription ()
    {
        return this.event;
    }
}


