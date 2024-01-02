package main.controller;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import main.login.MemberDTO;
import main.login.MemberDAO;
import main.login.SessionManager;

public class ProductIO {
    private Scanner sc = new Scanner(System.in);
    private ProductDAO dao = new ProductDAO();
    private MemberDTO mto = new MemberDTO();
    private MemberDAO mao = new MemberDAO();
    private ProductDTO dto = new ProductDTO();
    
    /////																					////// 구매할 물건 고르기
    public void basket() {
    	System.out.println("==========================================================================");
        System.out.print("어떤 제품을 구매하시겠습니까 ? ");
        String productName = sc.next();
        System.out.print("수량을 입력해주세요 : ");
        int quantity = sc.nextInt();

        // 임시로 temp에 데이터 담음

        // 이후 입력된 temp의 데이터들을 바탕으로 Cart 테이블에 삽입
        
        dto.setProductName(productName);
        dto.setQuantity(quantity);

        // 장바구니에 제품 추가
        dao.choiceProduct(dto);
        System.out.println("장바구니에 추가되었습니다.");
    }

    //////																					////// 구매하려는 목록조회
    public void cartViewAndPurchase() throws SQLException {
    	
        // 장바구니에 담긴 품목들을 조회
        List<ProductDTO> cartItems = dao.viewCart();
        mto = SessionManager.getCurrentUser();
        Scanner sc = new Scanner(System.in);
        
        // 구매할 품목이 있는지 확인
        if (cartItems.isEmpty()) {
            System.out.println("장바구니가 비어 있습니다.");
        } else {
        	////																					////// 장바구니 내용 출력
        	System.out.printf("%10s%18s%13s%10s%8s\n", "장바구니 번호", "상품명", "가격", "수량", "합계");
            System.out.println("===========================================================================");
            int totalCost = 0;
            for (ProductDTO cartItem : cartItems) {
                System.out.printf("%10d%24s%16d%11d%11d\n", cartItem.getCartId(), cartItem.getProductName(),
        	    		cartItem.getPrice(), cartItem.getQuantity(), cartItem.getTotalprice());	// 출력
        	    totalCost += cartItem.getTotalprice();	// 장바구니에 있는 물품들 SUM(가격*수량)
        	}

        	/////																					////// 구매 여부 확인
        	System.out.println("\n남은 잔액은 " + mto.getMoney() + "원이며 계산하실 총 금액은 "  + totalCost + "원입니다.");
            System.out.print(" Y : 구매 / N : 고민 / C : 선택취소 :  ");
            String answer = sc.next();
            if (answer.equalsIgnoreCase("Y")) {
            	dao.subtractFromStock(dto);	// 재고 감소 메서드
            	int remainingMoney = mto.getMoney() - totalCost;
                mto.setMoney(remainingMoney); // 잔액
                
//                dao.purchaseFromCart(cartItems);
                dao.clearCart(); // 장바구니 비우기
                System.out.println("남아있는 현금은 " + mto.getMoney() + "원입니다.");
                mao.moneyUpdate(mto); // 잔액 업데이트
            }
            if (answer.equalsIgnoreCase("N")) {
            	dao.clearTemp();
            }
            if (answer.equalsIgnoreCase("C")) {	// Cancel 장바구니에서 사기 싫은 물건 빼기
            	System.out.print("취소할 장바구니 번호를 선택해주세요 : ");
            	int cancelNum = sc.nextInt();
            	dao.choiceCancel(cancelNum);
            }
        }
    }
 
    	/////																			////// 가격 순으로 조회
 	public void priceSortList() {
 			List<ProductDTO> lists = dao.descending();
 			Iterator<ProductDTO> it = lists.iterator();
 			while(it.hasNext()) {
 				ProductDTO dto = it.next();
 				System.out.println(dto.toString());
 			}
 		}
 	
	//////																				////// 재고목록 조회
 	public void List() {
 		List<ProductDTO> lists = dao.getAllProducts();
 		Iterator<ProductDTO> it = lists.iterator();
 		while(it.hasNext()) {
 			ProductDTO dto = it.next();
 			System.out.println(dto.toString());
 		}
 	}

	// 관리자 권한으로 물품 정보 수정
	public void update() {
		
	}
	
//	// 관리자 권한으로 물품삭제
//	public void delete() {
//		
//		System.out.print("삭제할 제품 코드 : ");
//		String productId = sc.next();
//		
//		int result = dao.dataDelete(productId);
//		
//		if(result != 0) {
//			System.out.println("삭제 성공!");
//		} else {
//			System.out.println("삭제 실패!");
//		}
//	}
	
}