package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.messaging.model;

import java.util.HashMap;

public class User {
    private HashMap<String, HashMap<String, String>> username;

    public User() {}

    public User(HashMap<String, HashMap<String, String>> username) {
        this.username = username;
    }

    public  HashMap<String, HashMap<String, String>> getUsername() {
        return username;
    }

    public void setUsername(HashMap<String, HashMap<String, String>> username) {
        this.username = username;
    }
}
