package main.login;

public class SessionManager {

	private static MemberDTO currentUser;

    public static MemberDTO getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(MemberDTO user) {	// Id�� Pwd�� Ŭ���� ��ü�� user ����
        currentUser = user;
    }


    public static void logout() {
    	if (currentUser != null) {
            System.out.println(currentUser.getOrderId() + " ���� �α׾ƿ� �մϴ�."); // �α׾ƿ� �ÿ� ���̵� ���
        } else {
            System.out.println("�α��� ���°� �ƴմϴ�.");
        }
        currentUser = null;
    }
    
}
