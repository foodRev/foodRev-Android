package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.TaskAssignment.DriverMapping.Interfaces;

/**
 * Created by foodRev on 6/11/17.
 */

public interface MappableObject {
    public double getLatitude();
    public double getLongitude();

    public void setLatitude( double latitude );
    public void setLongitude( double latitude );

    public String getName();
    public String getUid();

    public int getIconId();
}
