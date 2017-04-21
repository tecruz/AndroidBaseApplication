package co.base.androidbaseapplication.domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

import static junit.framework.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class UsecaseTest
{
    private UseCaseTestClass useCase;

    private TestDisposableObserver<Object> testObserver;

    private CompositeDisposable disposable = new CompositeDisposable( );

    @Rule
    public ExpectedException expectedException = ExpectedException.none( );

    @Before
    public void setUp ()
    {
        this.useCase = new UseCaseTestClass( );
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
