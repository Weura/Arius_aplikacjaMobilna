package  com.example.pizzeria.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
public class LoggedInUserView {
    private String displayName;
    private int userId;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName, int userId) {
        this.displayName = displayName;
        this.userId = userId;
    }

    String getDisplayName() {
        return displayName;
    }
    int getUserId() {
        return userId;
    }
}