package main.login;

import java.util.Scanner;

import main.admin.AdminDAO;
import main.admin.AdminIO;

public class MemberIO {

	private SignBoard sb = new SignBoard();

	public MemberIO registInput() { // 회원가입
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

	public MemberDTO login() { // 로그인
		Scanner scanner = new Scanner(System.in);
		MemberDAO mao = MemberDAO.getInstance();
		MemberDTO member = null;

		sb.loginMonitor();
		System.out.println("========================================================================");
		System.out.print("아이디 : ");
		String id = scanner.next();
		System.out.print("비밀번호 : ");
		String pw = scanner.next();

		member = mao.loginCheck(id, pw); // 아이디 비번 member에 저장

		if (member != null) {
			// 로그인 성공 시 입력된 id를 기반으로 db에서 찾아옴.
			MemberDTO loggedInMember = mao.getMemberInfo(id); // SessionManager 랑 겹치는 기능인듯
			if (loggedInMember != null) { // 사용자 정보를 입력했다면
				SessionManager.setCurrentUser(loggedInMember); // 현재 로그인 중인 사용자로 설정.
				System.out.println("로그인 성공!");
				return loggedInMember;
			} else {
				System.out.println("로그인 실패!");
			}
		}
		return member;
	}

	public void adminMenu() { /// 관리자 모드로 로그인
		Scanner scanner = new Scanner(System.in);
		AdminIO adio = new AdminIO();

		int adminChoice = 0;

		do {
			System.out.println("\n======================= 관리자 모드 =======================");
			System.out.println("|  1. 제품 추가  /  2. 제품 수정  /  3. 제품 삭제          |");
			System.out.println("|  4. 회원 목록 조회  /  5. 로그아웃                       |");
			System.out.println("===========================================================");
			System.out.print("----> ");
			adminChoice = scanner.nextInt();
			System.out.println("===================================================");

			switch (adminChoice) {
			case 1:
				adio.adminInsert();
				break;

			case 2:
				adio.adminUpdate();
				break;

			case 3:
				adio.adminDelete();
				break;

			case 4:
				// 회원 목록 조회 메소드 호출
				adio.adminList();
				break;

			case 5:
				System.out.println("로그아웃 합니다.");
				SessionManager.logout();
				break;
			default:
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
				break;
			}
		} while (adminChoice != 6);
	}

	public MemberDTO firstSession() { // 첫번째 선택화면
		Scanner sc = new Scanner(System.in);

		int pu;
		do {
			MemberDTO member = null;
			sb.positionMonitor();
			System.out.println("===========================================");
			System.out.println("|  1. 로그인  /  2. 회원가입  /  3. 종료   |");
			System.out.println("===========================================");
			System.out.print("----> ");
			pu = sc.nextInt();
			System.out.println("========================================================================");

			switch (pu) {

			case 1:
				member = login();
				if (member != null) {
					SessionManager.setCurrentUser(member);
					if ("system".equals(member.getOrderId())) {
						adminMenu(); // 시스템 계정일 경우 관리자 메뉴 호출
					}
					return member; // 로그인 성공 시에만 반복문 탈출 , member = id, pw
				}
				break;

			case 2:
				registInput(); // 회원가입은 중복처리가 아니라면 무조건 성공함.
				break;

			case 3:
				System.out.println("프로그램을 종료합니다.");
				sb.systemExit();
				System.exit(0);
			}
		} while (true); // || result == null
	}

}