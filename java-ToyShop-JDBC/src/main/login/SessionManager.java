package main.login;


public class SessionManager {

	private static MemberDTO currentUser;

    public static MemberDTO getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(MemberDTO user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }
}
