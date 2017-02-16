package foodrev.org.foodrev.domain.models;

public class CoordinatorBuilder {
    private String firstName;
    private String lastName;
    private String email;
    private String sms;
    private String phoneNumber;
    private boolean isAvailable;
    private int preferredMethodOfContact;

    public CoordinatorBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CoordinatorBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CoordinatorBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public CoordinatorBuilder setSms(String sms) {
        this.sms = sms;
        return this;
    }

    public CoordinatorBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public CoordinatorBuilder setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
        return this;
    }

    public CoordinatorBuilder setPreferredMethodOfContact(int preferredMethodOfContact) {
        this.preferredMethodOfContact = preferredMethodOfContact;
        return this;
    }

    public Coordinator createCoordinator() {
        return new Coordinator(firstName, lastName, email, sms, phoneNumber, isAvailable, preferredMethodOfContact);
    }
}