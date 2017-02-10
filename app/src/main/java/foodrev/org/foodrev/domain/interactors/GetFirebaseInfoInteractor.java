package foodrev.org.foodrev.domain.interactors;

import foodrev.org.foodrev.domain.interactors.base.Interactor;

/**
 * Created by darver on 2/8/17.
 */

public interface GetFirebaseInfoInteractor extends Interactor {
    interface Callback {
        void onDataReceived();
        void onDataReceiveFailed();
    }
    void populateInfos();
}