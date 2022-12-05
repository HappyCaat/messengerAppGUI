package messenger;

public class User {
    private static String userName;
    private static String userId;

    public static String getUserName() {
        return userName;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserName(String userName) {
        User.userName = userName;
    }

    public static void setUserId(String userId) {
        User.userId = userId;
    }
}
