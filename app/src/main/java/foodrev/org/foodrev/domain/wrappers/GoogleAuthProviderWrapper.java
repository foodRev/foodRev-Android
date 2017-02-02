package foodrev.org.foodrev.domain.wrappers;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Created by darver on 2/1/17.
 */

public class GoogleAuthProviderWrapper {
    public AuthCredential getCredential(String var1, String var2) {
        return GoogleAuthProvider.getCredential(var1, var2);
    }
}
