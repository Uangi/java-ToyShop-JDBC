package main.admin;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import main.controller.ProductDTO;
import main.login.MemberDTO;

public class AdminIO {

	private AdminDAO adpao =  new AdminDAO();
	
	// 추가
	public void adminInsert() {
		
		ProductDTO dto = new ProductDTO();
		Scanner sc = new Scanner(System.in);
		System.out.println("===================제품 추가===================");
		System.out.print("제품번호를 입력해주세요. :  ");
		dto.setProductId(sc.next());
		
		System.out.print("상품명를 입력해주세요. :  ");
		dto.setProductName(sc.next());
		
		System.out.print("가격을 입력해주세요 :  ");
		dto.setPrice(sc.nextInt());
		
		System.out.print("수량을 입력해주세요 :  ");
		dto.setQuantity(sc.nextInt());
		
		int result = adpao.dataInsert(dto);
		
		if(result != 0) {
			System.out.println("추가 성공!");
		} else {
			System.out.println("추가 실패!");
		}
	}
	
	// 수정
	public void adminUpdate() {
		ProductDTO dto = new ProductDTO();
		Scanner sc = new Scanner(System.in);
		
		System.out.print("수정할 상품번호를 입력해주세요 :  ");
		dto.setProductId(sc.next());
		
		System.out.print("상품명 : ");
		dto.setProductName(sc.next());
		
		System.out.print("가격 : ");
		dto.setPrice(sc.nextInt());
		
		System.out.print("수량 : ");
		dto.setQuantity(sc.nextInt());
		
		
		int result = adpao.dataUpdate(dto);
		if(result != 0) {
			System.out.println("수정 성공!");
		} else {
			System.out.println("수정 실패!");
		}
	}
	
	// 삭제
	public void adminDelete() {
		Scanner sc = new Scanner(System.in);
		System.out.print("삭제할 상품번호가 무엇인가요 : ");
		int productId = sc.nextInt();
		
		int result = adpao.dataDelete(productId);
		
		if(result != 0) {
			System.out.println("삭제 성공!");
		} else {
			System.out.println("삭제 실패!");
		}
	}
	
	// 회원목록 조회
	public void adminList() {
		AdminDAO adao = new AdminDAO();
		System.out.println("  회원 ID  회원Pwd  잔액");
		List<MemberDTO> lists = adao.dataCheck();
 		Iterator<MemberDTO> it = lists.iterator();
 		while(it.hasNext()) {
 			MemberDTO mto = it.next();
 			System.out.println(mto.toString());
 		}
	}
	
}
