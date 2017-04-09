package foodrev.org.foodrev.domain.models;

/**
 * Created by Gregory Kielian on 2/15/17.
 */

public class Coordinator extends PointOfContact {

    public Coordinator(String firstName,
                       String lastName,
                       String email,
                       String sms,
                       String phoneNumber,
                       boolean isAvailable,
                       int preferredMethodOfContact) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.sms = sms;
        this.phoneNumber = phoneNumber;
        this.isAvailable = isAvailable;
        this.preferredMethodOfContact = preferredMethodOfContact;
    }

}
