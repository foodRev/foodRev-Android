package foodrev.org.foodrev.Threading;

import android.os.Handler;
import android.os.Looper;

import foodrev.org.foodrev.domain.executor.MainThread;

/**
 * Created by darver on 1/25/17.
 */

public class MainThreadImpl implements MainThread {

    private static MainThread sMainThread;

    private Handler mHandler;

    private MainThreadImpl() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(Runnable runnable) {
        mHandler.post(runnable);
    }

    public static MainThread getInstance() {
        if (sMainThread == null) {
            sMainThread = new MainThreadImpl();
        }

        return sMainThread;
    }
}
