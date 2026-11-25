package com.example.clothes;

public class SESL {
    public static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void logout() {
        currentUser = null;
    }
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}
