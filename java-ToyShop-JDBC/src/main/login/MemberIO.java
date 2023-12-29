package main.login;

import java.lang.reflect.Member;
import java.util.Scanner;

public class MemberIO {

	
	Scanner sc = new Scanner(System.in);
	
	
	MemberDAO mao = new MemberDAO();
	public void registInput() {
		MemberDTO mto = new MemberDTO();
		
		System.out.print("아이디 : ");
		mto.setOrderId(sc.next());
		System.out.print("비밀번호 : ");
		mto.setOrderPwd(sc.next());
		
		int result = mao.userRegister();
		
		if(result != 0) {
			System.out.println("가입 성공!");
		} else {
			System.out.println("가입 실패!");
		} 
	}
	
	public void login() {
		Scanner scanner = new Scanner(System.in);
		MemberDTO member;
		
		System.out.println("로그인");
		 
		System.out.print("아이디 : ");
		String id = scanner.nextLine();
		System.out.print("비밀번호 : ");
		String pw = scanner.nextLine();
		 
		     member = (MemberDTO) mao.login(id, pw);

	        if (member != null) {
	            SessionManager.setCurrentUser(member);
	            System.out.println("로그인 성공!");
	        } else {
	            System.out.println("로그인 실패!");
	        }
	}
	
	public void logout() {
		System.out.println("로그아웃합니다.");
		SessionManager.logout();
	}
	
	public void firstSession() {
		int pu;
		
		 do {
			System.out.println("1. 로그인 / 2. 회원가입 / 3. 종료");
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
			 System.out.println("프로그램 종료");
			 System.exit(0);
	     
		 	}
		 } while(pu < 2);
	}
}
