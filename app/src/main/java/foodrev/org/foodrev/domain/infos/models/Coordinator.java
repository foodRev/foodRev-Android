package foodrev.org.foodrev.domain.infos.models;

public class Coordinator {
    private String mName = null;
    private String mPhoneNumber = null;

    public Coordinator() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public void copy(Coordinator otherCoordinator) {
        this.mName = otherCoordinator.mName;
        this.mPhoneNumber = otherCoordinator.mPhoneNumber;
    }
}
