package main.login;

public class SessionManager {

	private static MemberDTO currentUser;	// 사용자 정보 저장하기

    public static MemberDTO getCurrentUser() {	// 현재 로그인한 사용자 정보 저장
        return currentUser;
    }

    public static void setCurrentUser(MemberDTO user) {	
        currentUser = user;	// 로그인하면 사용자 정보 설정, 로그아웃 시에는 null
    }

    public static void logout() {
    	if (currentUser != null) {
            System.out.println(currentUser.getOrderId() + " 님이 로그아웃 합니다."); // 로그아웃 시에 아이디 출력
        }
        currentUser = null;	// 사용자 정보 null 값으로 바꾸기
    }
    
}
