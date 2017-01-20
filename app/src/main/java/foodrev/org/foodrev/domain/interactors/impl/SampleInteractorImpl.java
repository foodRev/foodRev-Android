package foodrev.org.foodrev.domain.interactors.impl;


import foodrev.org.foodrev.domain.executor.Executor;
import foodrev.org.foodrev.domain.executor.MainThread;
import foodrev.org.foodrev.domain.interactors.SampleInteractor;
import foodrev.org.foodrev.domain.interactors.base.AbstractInteractor;
import foodrev.org.foodrev.domain.repository.Repository;

/**
 * This is an interactor boilerplate with a reference to a model repository.
 * <p/>
 */
public class SampleInteractorImpl extends AbstractInteractor implements SampleInteractor {

    private SampleInteractor.Callback mCallback;
    private Repository mRepository;

    public SampleInteractorImpl(Executor threadExecutor,
                                MainThread mainThread,
                                Callback callback, Repository repository) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mRepository = repository;
    }

    @Override
    public void run() {
        // TODO: Implement this with your business logic
    }
}
