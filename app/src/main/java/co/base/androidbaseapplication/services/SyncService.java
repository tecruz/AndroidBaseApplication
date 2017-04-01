package co.base.androidbaseapplication.services;

import android.content.Context;
import android.content.Intent;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.util.List;

import javax.inject.Inject;

import co.base.androidbaseapplication.AndroidBaseApplication;
import co.base.androidbaseapplication.data.exception.RetrofitException;
import co.base.androidbaseapplication.domain.GetCountriesUsecase;
import co.base.androidbaseapplication.events.EventEmitter;
import co.base.androidbaseapplication.events.Events;
import co.base.androidbaseapplication.ui.entity.Country;
import co.base.androidbaseapplication.util.ErrorMessageFactory;
import co.base.androidbaseapplication.util.PreferencesUtil;
import rx.Observer;
import rx.Subscription;
import timber.log.Timber;

public class SyncService extends JobService {

    @Inject
    GetCountriesUsecase mGetCountriesUsecase;
    @Inject
    PreferencesUtil mPreferencesUtil;
    @Inject
    EventEmitter mEventPostHelper;
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
    public boolean onStartJob(final JobParameters job) {
        Timber.i("Starting sync...");

        if (mSubscription != null && !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();

        mSubscription = mGetCountriesUsecase.setIsSync(true).execute()
                .subscribe(new Observer<List<Country>>() {
                    @Override
                    public void onCompleted() {
                        Timber.i("Synced successfully!");
                        long syncTimeStamp = System.currentTimeMillis();
                        mPreferencesUtil.setLastSyncTimestamp(syncTimeStamp);
                        mEventPostHelper.postEvent(Events.SYNC_COMPLETED);
                        jobFinished(job, false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        /*try {
                            error.getErrorBodyAs(ErrorResponse.class);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }*/
                        RetrofitException error = (RetrofitException) e;
                        Timber.i(ErrorMessageFactory.create(getApplicationContext(),
                                error.getKind()));
                        mEventPostHelper.postEvent(Events.SYNC_ERROR);
                        jobFinished(job, false);
                    }

                    @Override
                    public void onNext(List<Country> countries) {
                    }
                });

        mEventPostHelper.postEvent(Events.SYNC_STARTED);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }

    @Override
    public void onDestroy() {
        if (mSubscription != null) mSubscription.unsubscribe();
        super.onDestroy();
    }
}