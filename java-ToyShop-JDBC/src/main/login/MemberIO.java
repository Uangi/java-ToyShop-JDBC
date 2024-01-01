package main.login;

import java.util.Scanner;

public class MemberIO {
	
	public MemberIO registInput() {		// 회원가입
		Scanner sc = new Scanner(System.in);
		MemberDTO mto = new MemberDTO();
		MemberDAO mao = MemberDAO.getInstance();
		
		System.out.print("아이디 : ");
        mto.setOrderId(sc.next());
        System.out.print("비밀번호 : ");
        mto.setOrderPwd(sc.next());
        System.out.print("현금 : ");
        mto.setMoney(sc.nextInt());
        
	    int result = mao.userRegister(mto);

	    if (result != 0) {
	        System.out.println("가입 성공!");
	        return this;
	    } else {
	        System.out.println("가입 실패!");
	        return null;
	    }
	}
	
	public MemberDTO login() {		// 로그인
		Scanner scanner = new Scanner(System.in);
		MemberDAO mao = MemberDAO.getInstance();
		MemberDTO member = null;
		
		System.out.println("--------로그인 창---------");	// 나중에 꾸미기
		 
		System.out.print("아이디 : ");
		String id = scanner.next();
		System.out.print("비밀번호 : ");
		String pw = scanner.next();
		 
		member = mao.loginCheck(id, pw);

	        if (member != null) {
	        	 // 로그인 성공 시 회원 정보 가져와서 money 설정
	            MemberDTO loggedInMember = mao.getMemberInfo(id);	// SessionManager 랑 겹치는 기능인듯
	            if (loggedInMember != null) {
	            SessionManager.setCurrentUser(loggedInMember);
	            System.out.println("로그인 성공!");
	            return loggedInMember;
	        } else {
	            System.out.println("로그인 실패!");
	        }
	        }
			return member;
	}
	
	public MemberDTO firstSession() {	// 첫번째 선택화면
		Scanner sc = new Scanner(System.in);
		SignBoard sb = new SignBoard();
		int pu;
		 do {
			 MemberDTO member = null;
			 sb.picture();
			System.out.print("\n1. 로그인 / 2. 회원가입 / 3. 종료 : ");
			pu = sc.nextInt();
			System.out.println("==================================================================");
		
		 switch(pu) {
		 
		 case 1:
			 member = login();
             if (member != null) {
            	 SessionManager.setCurrentUser(member);
                 return member; // 로그인 성공 시에만 반복문 탈출 , member = id, pw
             }
			 break;
		
		 case 2:
			 registInput();	// 회원가입은 중복처리가 아니라면 무조건 성공함.
			 break;
			 
		 case 3:
			 System.out.println("프로그램을 종료합니다.");
			 System.exit(0);
		 	}
		 } while(true); // || result == null
	}
	
}