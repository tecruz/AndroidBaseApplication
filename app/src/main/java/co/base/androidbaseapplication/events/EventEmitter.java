package co.base.androidbaseapplication.events;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EventEmitter
{

    private LocalBroadcastManager localBroadcastManager;

    @Inject
    public EventEmitter (Context context)
    {
        localBroadcastManager = LocalBroadcastManager.getInstance( context );
    }

    public void postEvent (Events event)
    {
        localBroadcastManager.sendBroadcast( new Intent( event.getDescription( ) ) );
    }
}
