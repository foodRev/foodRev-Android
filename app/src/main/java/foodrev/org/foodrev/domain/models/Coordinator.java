package foodrev.org.foodrev.domain.models;

/**
 * Created by Gregory Kielian on 2/15/17.
 */

public class Coordinator extends PointOfContact {
//    private String firstName;
//    private String lastName;
//    private String email;
//    private String sms;
//    private String phoneNumber;
//    private boolean isAvailable;
//    private int preferredMethodOfContact;

    public Coordinator() {

    }

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

//    @Override
//    public String getFirstName() {
//        return firstName;
//    }
//
//    @Override
//    public String getLastName() {
//        return lastName;
//    }
//
//    @Override
//    public String getEmail() {
//        return email;
//    }
//
//    @Override
//    public String getSms() {
//        return sms;
//    }
//
//    @Override
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    @Override
//    public boolean isAvailable() {
//        return isAvailable;
//    }
//
//    @Override
//    public int getPreferredMethodOfContact() {
//        return preferredMethodOfContact;
//    }
}
