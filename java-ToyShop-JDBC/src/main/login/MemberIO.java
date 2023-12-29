package main.login;

import java.util.Scanner;

public class MemberIO {

	Scanner sc = new Scanner(System.in);
	
	public MemberIO registInput() {
		
		MemberDTO mto = new MemberDTO();
		MemberDAO mao = MemberDAO.getInstance();
		System.out.print("아이디 : ");
        mto.setOrderId(sc.next());
        System.out.print("비밀번호 : ");
        mto.setOrderPwd(sc.next());
        
	    int result = mao.userRegister(mto);

	    if (result != 0) {
	        System.out.println("가입 성공!");
	        return this;
	    } else {
	        System.out.println("가입 실패!");
	        return null;
	    }
	}
	
	public MemberDTO login() {
		Scanner scanner = new Scanner(System.in);
		MemberDAO mao = MemberDAO.getInstance();
		MemberDTO member = null;
		
		System.out.println("로그인 창");	// 나중에 꾸미기
		 
		System.out.print("아이디 : ");
		String id = scanner.next();
		System.out.print("비밀번호 : ");
		String pw = scanner.next();
		 
		member = mao.loginCheck(id, pw);

	        if (member != null) {
	            SessionManager.setCurrentUser(member);
	            System.out.println("로그인 성공!");
	        } else {
	            System.out.println("로그인 실패!");
	        }
			return member;
	}
	
	public void logout() {
		System.out.println("로그아웃합니다.");
		SessionManager.logout();
	}
	
	public MemberDTO firstSession() {
		int pu;
		MemberIO result = this;
		 do {
			System.out.println("1. 로그인 / 2. 회원가입 / 3. 로그아웃 / 4. 종료");
			pu = sc.nextInt();
		
		 switch(pu) {
		 
		 case 1:
			 MemberDTO member = login();
             if (member != null) {
            	 SessionManager.setCurrentUser(member);
                 return member; // 로그인 성공 시에만 반복문 탈출
             }
			 break;
		
		 case 2:
			 registInput();	// 회원가입은 중복처리가 아니라면 무조건 성공함.
			 break;
			 
		 case 3:
			 logout();
			 break;
			 
		 case 4:
			 System.out.println("프로그램 종료");
			 System.exit(0);
	     
		 	}
		 } while(true); // || result == null
	}
}
