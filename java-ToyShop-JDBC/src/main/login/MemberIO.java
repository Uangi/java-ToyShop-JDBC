package main.login;

import java.lang.reflect.Member;
import java.util.Scanner;

public class MemberIO {

	
	Scanner sc = new Scanner(System.in);
	
	
	MemberDAO mao = new MemberDAO();
	public void registInput() {
		MemberDTO mto = new MemberDTO();
		
		System.out.print("���̵� : ");
		mto.setOrderId(sc.next());
		System.out.print("��й�ȣ : ");
		mto.setOrderPwd(sc.next());
		
		int result = mao.userRegister();
		
		if(result != 0) {
			System.out.println("���� ����!");
		} else {
			System.out.println("���� ����!");
		} 
	}
	
	public void login() {
		Scanner scanner = new Scanner(System.in);
		MemberDTO member;
		
		System.out.println("�α���");
		 
		System.out.print("���̵� : ");
		String id = scanner.nextLine();
		System.out.print("��й�ȣ : ");
		String pw = scanner.nextLine();
		 
		     member = (MemberDTO) mao.login(id, pw);

	        if (member != null) {
	            SessionManager.setCurrentUser(member);
	            System.out.println("�α��� ����!");
	        } else {
	            System.out.println("�α��� ����!");
	        }
	}
	
	public void logout() {
		System.out.println("�α׾ƿ��մϴ�.");
		SessionManager.logout();
	}
	
	public void firstSession() {
		int pu;
		
		 do {
			System.out.println("1. �α��� / 2. ȸ������ / 3. ����");
			pu = sc.nextInt();
		
		 switch(pu) {
		 
		 case 1:
			 login();
			 break;
		
		 case 2:
			 registInput();
			 break;
		 case 3:
			 logout();
			 break;
		 case 4:
			 System.out.println("���α׷� ����");
			 System.exit(0);
	     
		 	}
		 } while(pu < 2);
	}
}
