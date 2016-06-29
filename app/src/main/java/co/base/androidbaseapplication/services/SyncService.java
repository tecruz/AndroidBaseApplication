package co.base.androidbaseapplication.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;

import java.util.List;

import javax.inject.Inject;

import co.base.androidbaseapplication.AndroidBaseApplication;
import co.base.androidbaseapplication.domain.SyncCountriesUsecase;
import co.base.androidbaseapplication.events.EventPosterHelper;
import co.base.androidbaseapplication.events.Events;
import co.base.androidbaseapplication.model.entities.Country;
import co.base.androidbaseapplication.model.rest.exceptions.RetrofitException;
import co.base.androidbaseapplication.util.NetworkUtil;
import co.base.androidbaseapplication.util.PreferencesUtil;
import rx.Observer;
import rx.Subscription;
import timber.log.Timber;

public class SyncService extends Service {

    @Inject
    SyncCountriesUsecase mSyncCountriesUsecase;
    @Inject
    PreferencesUtil mPreferencesUtil;
    @Inject
    EventPosterHelper mEventPostHelper;
    private Subscription mSubscription;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SyncService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidBaseApplication.get(this).getComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        Timber.i("Starting sync...");

        if (mSubscription != null && !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();

        mSubscription = mSyncCountriesUsecase.execute()
                .subscribe(new Observer<List<Country>>() {
                    @Override
                    public void onCompleted() {
                        Timber.i("Synced successfully!");
                        long syncTimeStamp = System.currentTimeMillis();
                        mPreferencesUtil.setLastSyncTimestamp(syncTimeStamp);
                        stopSelf(startId);
                    }

                    @Override
                    public void onError(Throwable e) {
                        /*try {
                            error.getErrorBodyAs(ErrorResponse.class);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }*/
                        RetrofitException error = (RetrofitException) e;
                        if (error.getKind() == RetrofitException.Kind.NO_INTERNET_CONNECTION) {
                            Timber.i("Sync canceled, connection not available");
                        } else {
                            Timber.w(e, "Error syncing.");
                        }
                        mEventPostHelper.postEvent(Events.SYNC_ERROR);
                        stopSelf(startId);

                    }

                    @Override
                    public void onNext(List<Country> countries) {
                    }
                });

        mEventPostHelper.postEvent(Events.SYNC_STARTED);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mSubscription != null) mSubscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static class SyncOnConnectionAvailable extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)
                    && NetworkUtil.isNetworkConnected(context)) {
                Timber.i("Connection is now available, triggering sync...");
                context.startService(getStartIntent(context));
            }
        }
    }

}