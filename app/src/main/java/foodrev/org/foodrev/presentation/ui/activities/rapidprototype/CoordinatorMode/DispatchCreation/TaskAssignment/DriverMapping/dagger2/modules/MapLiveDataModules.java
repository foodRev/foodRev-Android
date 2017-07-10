package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.dagger2.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.livedata.DispatchDriverLiveData;

/**
 * Created by Gregory Kielian on 7/9/17.
 */

@Module
public class MapLiveDataModules {

    @Provides
    @Singleton
    public DispatchDriverLiveData provideDispatchDriverLiveData(String dispatchKey) {
        return new DispatchDriverLiveData(dispatchKey);
    }

}
