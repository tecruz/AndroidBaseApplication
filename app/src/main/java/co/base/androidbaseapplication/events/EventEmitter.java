package co.base.androidbaseapplication.events;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.base.androidbaseapplication.injection.ApplicationContext;

@Singleton
public class EventEmitter {

    private LocalBroadcastManager mLocalBroadcastManager;

    @Inject
    public EventEmitter(@ApplicationContext Context context) {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    public void postEvent(Events event) {
        mLocalBroadcastManager.sendBroadcast(new Intent(event.toString()));
    }
}
