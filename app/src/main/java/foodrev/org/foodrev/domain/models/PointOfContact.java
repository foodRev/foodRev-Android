package foodrev.org.foodrev.domain.models;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by Gregory Kielian on 2/15/17.
 */

public abstract class PointOfContact {

    // poc name
    String firstName;
    String lastName;

    // contact methods
    String email;
    String sms;
    String phoneNumber;

    //availability
    boolean isAvailable;

    // preferred method of contact -- avoiding enum for space reasons
    public static final int PREFER_EMAIL = 0;
    public static final int PREFER_SMS = 1;
    public static final int PREFER_PHONENUMBER = 2;

    int preferredMethodOfContact;

    // Contact POC
    public void startPhoneCall(Context context) {
        Intent i = new Intent(Intent.ACTION_DIAL);
        i.setData(Uri.parse("tel:" + this.phoneNumber));

        //return to foodrev app after call
        i.addFlags(i.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(i);
    }

    public void sendSms(Context context) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("sms:" + this.phoneNumber));

        //return to foodrev app after call
        i.addFlags(i.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(i);
    }

    public void sendEmail(Context context, String emailSubject, String emailBody) {

        Intent i = new Intent(Intent.ACTION_SEND);

        i.putExtra(Intent.EXTRA_EMAIL, this.email);
        i.putExtra(Intent.EXTRA_SUBJECT,emailSubject);
        i.putExtra(Intent.EXTRA_TEXT,emailBody);
        i.setType("message/rfc822");

        //return to foodrev app after call
        i.addFlags(i.FLAG_ACTIVITY_NEW_TASK);

        try {
            context.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch( android.content.ActivityNotFoundException ex ) {
            Toast.makeText(context, "There are no email clients installed.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getPreferredMethodOfContact() {
        return preferredMethodOfContact;
    }

    public void setPreferredMethodOfContact(int preferredMethodOfContact) {
        this.preferredMethodOfContact = preferredMethodOfContact;
    }
}
