package foodrev.org.foodrev.domain.executor;

import foodrev.org.foodrev.domain.interactors.base.AbstractInteractor;

public interface Executor {


    void execute(final AbstractInteractor interactor);
}
