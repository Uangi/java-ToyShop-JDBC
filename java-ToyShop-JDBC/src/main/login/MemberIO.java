package main.login;

import java.util.Scanner;

public class MemberIO {

	Scanner sc = new Scanner(System.in);
	
	public MemberIO registInput() {
		
		MemberDTO mto = new MemberDTO();
		MemberDAO mao = MemberDAO.getInstance();
		System.out.print("���̵� : ");
        mto.setOrderId(sc.next());
        System.out.print("��й�ȣ : ");
        mto.setOrderPwd(sc.next());
        
	    int result = mao.userRegister(mto);

	    if (result != 0) {
	        System.out.println("���� ����!");
	        return this;
	    } else {
	        System.out.println("���� ����!");
	        return null;
	    }
	}
	
	public MemberDTO login() {
		Scanner scanner = new Scanner(System.in);
		MemberDAO mao = MemberDAO.getInstance();
		MemberDTO member = null;
		
		System.out.println("�α��� â");	// ���߿� �ٹ̱�
		 
		System.out.print("���̵� : ");
		String id = scanner.next();
		System.out.print("��й�ȣ : ");
		String pw = scanner.next();
		 
		member = mao.loginCheck(id, pw);

	        if (member != null) {
	            SessionManager.setCurrentUser(member);
	            System.out.println("�α��� ����!");
	        } else {
	            System.out.println("�α��� ����!");
	        }
			return member;
	}
	
	public void logout() {
		System.out.println("�α׾ƿ��մϴ�.");
		SessionManager.logout();
	}
	
	public MemberDTO firstSession() {
		int pu;
		MemberIO result = this;
		 do {
			System.out.println("1. �α��� / 2. ȸ������ / 3. �α׾ƿ� / 4. ����");
			pu = sc.nextInt();
		
		 switch(pu) {
		 
		 case 1:
			 MemberDTO member = login();
             if (member != null) {
            	 SessionManager.setCurrentUser(member);
                 return member; // �α��� ���� �ÿ��� �ݺ��� Ż��
             }
			 break;
		
		 case 2:
			 registInput();	// ȸ�������� �ߺ�ó���� �ƴ϶�� ������ ������.
			 break;
			 
		 case 3:
			 logout();
			 break;
			 
		 case 4:
			 System.out.println("���α׷� ����");
			 System.exit(0);
	     
		 	}
		 } while(true); // || result == null
	}
}
