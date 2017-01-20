package foodrev.org.foodrev.domain.interactors.base;

import foodrev.org.foodrev.domain.executor.Executor;
import foodrev.org.foodrev.domain.executor.MainThread;

public abstract class AbstractInteractor implements Interactor {

    protected Executor mThreadExecutor;
    protected MainThread mMainThread;

    protected volatile boolean mIsCanceled;
    protected volatile boolean mIsRunning;

    public AbstractInteractor(Executor threadExecutor, MainThread mainThread) {
        mThreadExecutor = threadExecutor;
        mMainThread = mainThread;
    }

    public abstract void run();

    public void cancel() {
        mIsCanceled = true;
        mIsRunning = false;
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    public void onFinished() {
        mIsRunning = false;
        mIsCanceled = false;
    }

    public void execute() {

        // mark this interactor as running
        this.mIsRunning = true;

        // start running this interactor in a background thread
        mThreadExecutor.execute(this);
    }

}
