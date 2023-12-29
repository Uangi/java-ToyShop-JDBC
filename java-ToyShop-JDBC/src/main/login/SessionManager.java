package main.login;

public class SessionManager {

	private static MemberDTO currentUser;

    public static MemberDTO getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(MemberDTO user) {	// Id와 Pwd를 클래스 객체로 user 생성
        currentUser = user;
    }


    public static void logout() {
    	if (currentUser != null) {
            System.out.println(currentUser.getOrderId() + " 님이 로그아웃 합니다."); // 로그아웃 시에 아이디 출력
        } else {
            System.out.println("로그인 상태가 아닙니다.");
        }
        currentUser = null;
    }
    
}
