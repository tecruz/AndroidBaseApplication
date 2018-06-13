package co.base.domain.domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import co.base.domain.Usecase;
import co.base.domain.executor.PostExecutionThread;
import co.base.domain.executor.ThreadExecutor;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UsecaseTest
{
    private UseCaseTestClass useCase;

    @Mock
    private ThreadExecutor mockThreadExecutor;

    @Mock
    private PostExecutionThread mockPostExecutionThread;

    private TestDisposableObserver<Object> testObserver;
    private CompositeDisposable disposable = new CompositeDisposable( );

    @Rule
    public ExpectedException expectedException = ExpectedException.none( );

    @Before
    public void setUp ()
    {
        when( mockThreadExecutor.getScheduler( ) ).thenReturn( Schedulers.trampoline( ) );
        when( mockPostExecutionThread.getScheduler( ) ).thenReturn( Schedulers.trampoline( ) );
        this.useCase = new UseCaseTestClass( mockThreadExecutor, mockPostExecutionThread );
        this.testObserver = new TestDisposableObserver<>( );
        this.disposable = new CompositeDisposable( );
    }

    @Test
    public void testBuildUseCaseObservableReturnCorrectResult ()
    {
        useCase.execute( );

        assertTrue( testObserver.valuesCount == 0 );
    }

    @Test
    public void testSubscriptionWhenExecutingUseCase ()
    {
        disposable.add( useCase.execute( ).subscribeWith( testObserver ) );
        disposable.clear( );

        assertTrue( testObserver.isDisposed( ) );
    }

    private static class UseCaseTestClass extends Usecase<Object>
    {

        UseCaseTestClass (ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread)
        {
            super( threadExecutor, postExecutionThread );
        }

        @Override
        public Observable<Object> buildObservable ()
        {
            return Observable.empty( );
        }
    }

    private static class TestDisposableObserver<T> extends DisposableObserver<T>
    {
        private int valuesCount = 0;

        @Override
        public void onNext (T value)
        {
            valuesCount++;
        }

        @Override
        public void onError (Throwable e)
        {
            // no-op by default.
        }

        @Override
        public void onComplete ()
        {
            // no-op by default.
        }
    }
}
