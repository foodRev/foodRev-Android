package foodrev.org.foodrev;

import android.app.Application;

import foodrev.org.foodrev.domain.infos.CareInfo;
import foodrev.org.foodrev.domain.infos.CommunityCenterInfo;
import foodrev.org.foodrev.domain.infos.DonationCenterInfo;
import foodrev.org.foodrev.domain.infos.DriverInfo;
import foodrev.org.foodrev.domain.infos.PopulateInfos;
import foodrev.org.foodrev.domain.infos.models.DonationCenter;
import foodrev.org.foodrev.domain.infos.models.Driver;

/**
 * Created by george on 2/23/17.
 */

public class App extends Application {
    private DriverInfo driverInfo;
    private DonationCenterInfo donorInfo;
    private CommunityCenterInfo ccInfo;
    private CareInfo careInfo;

    public void setAllInfos(DriverInfo driverInfo, DonationCenterInfo donorInfo,
                            CommunityCenterInfo ccInfo, CareInfo careInfo) {
        this.driverInfo = driverInfo;
        this.donorInfo = donorInfo;
        this.ccInfo = ccInfo;
        this.careInfo = careInfo;
    }

    public DriverInfo getDriverInfo() {
        return driverInfo;
    }

    public DonationCenterInfo getDonorInfo() {
        return donorInfo;
    }

    public CommunityCenterInfo getCcInfo() {
        return ccInfo;
    }

    public CareInfo getCareInfo() {
        return careInfo;
    }
}
