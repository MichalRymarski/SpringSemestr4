package org.example;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class Authentication {
    private static Authentication instance = null;
    public boolean loggedIn;

    private Authentication() {
        loggedIn = false;
    }

    public static Authentication getInstance() {
        if (instance == null) {
            instance = new Authentication();
        }
        return instance;
    }

    public void authenticate(String userName, String unhashedPassword) {
        String hashedPassword = Hashing.sha256().hashString(unhashedPassword, StandardCharsets.UTF_8).toString();
        List<User> users = UserRepository.getInstance().getAllUsers();
        User foundUser = users.stream()
            .filter(user -> user.getLogin().equals(userName) && user.getHashedPassword().equals(hashedPassword))
            .findFirst()
            .orElse(null);

        if (foundUser != null) {
            loggedIn = true;
            User.overwriteInstance(foundUser);
            return ;
        }

        return ;
    }
}