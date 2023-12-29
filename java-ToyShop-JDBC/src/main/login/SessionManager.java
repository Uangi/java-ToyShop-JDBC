package main.login;

import java.lang.reflect.Member;

public class SessionManager {

	private static MemberDTO currentUser;

    public static MemberDTO getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(MemberDTO user) {
        currentUser = user;
    }

    public static MemberDTO login(String orderId, String orderPwd) {
    	MemberDTO member = null;
    	return member;
    	
    }

    public static void logout() {
        currentUser = null;
    }
    
}
