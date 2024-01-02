package main.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import main.login.MemberDTO;
import main.login.MemberIO;
import main.login.SessionManager;
import main.login.SignBoard;

public class Main {

		public static void main(String[] args) {
			SignBoard sb = new SignBoard();
		    Scanner sc  = new Scanner(System.in);
		    ProductIO ob = new ProductIO();
		    ProductDAO pao = new ProductDAO();
		    MemberIO mi = new MemberIO();
		    MemberDTO mto = new MemberDTO();
		    
		    int ch;
		    
		    while (true) {							// 두번째 선택 화면
		        if (SessionManager.getCurrentUser() == null) {
		            mi.firstSession();
		            System.out.print("\n1. 구매 / 2. 장바구니 / 3. 재고목록 조회 / 4. 잔액조회 / 5. 로그아웃 : ");
		            ch = sc.nextInt();
		            System.out.println("============================================================================");
		        } else {
		            System.out.print("\n1. 구매 / 2. 장바구니 / 3. 재고목록 조회 / 4. 잔액조회 / 5. 로그아웃 : ");
		            ch = sc.nextInt();
		            System.out.println("===========================================================================");
		        }

		        switch (ch) {
		            case 1:
		                if (SessionManager.getCurrentUser() == null) {
		                    mi.login();
		                } else {
		                	sb.addToCart();
		                    ob.basket();
		                }
		                break;

		            case 2:
		                if (SessionManager.getCurrentUser() != null) {
		                    try {
		                        ob.cartViewAndPurchase();
		                    } catch (SQLException e) {
		                        e.printStackTrace();
		                    }
		                } else {
		                    System.out.println("로그인 후에 이용할 수 있습니다.");
		                }
		                break;

		            case 3:
		            	System.out.println("\n┌ --------------------------- - 재고목록 - --------------------------- ┐");
	                      System.out.println(" |\t\t\t\t\t\t\t                |");
	                      System.out.println(" |       제품코드\t\t상품명\t\t가격\t   수량         |");
	                      System.out.println(" | -------------------------------------------------------------------- |");
	                                 ob.List();
	                     System.out.println(" |\t\t\t\t\t\t\t                |");
	                      System.out.println("┗_____________________________________________________________________ ┚");
	                   
	                     System.out.print("\n1. 검색 / 2. 높은 가격 순으로 조회 / 3. 뒤로가기 : ");
	                     int subMenuChoice = sc.nextInt();
	                     System.out.println("==============================================================================");
			            
			            switch(subMenuChoice) {
			            
			            case 1: 
		             		System.out.print("검색할 물품명을 입력해주세요 : ");
		             		String searchName = sc.next();
		             		List<ProductDTO> searchResults = pao.productSearch(searchName);
		             		 // 검색 결과 출력
		                    System.out.println("===========================================================================");
		                    System.out.println("\n ------------------------------ 재고목록 ------------------------------");
							System.out.println("|\t\t\t\t\t\t                       |");
				            System.out.println("|       제품코드\t\t상품명\t\t가격\t   수량        |");
				            System.out.println(" ----------------------------------------------------------------------");
				            for (ProductDTO result : searchResults) {
		                        System.out.println(result);
		                    }
				            System.out.println("|\t\t\t\t\t\t                       |");
				            System.out.println(" ----------------------------------------------------------------------");
				            
		                    System.out.println("===========================================================================");
			            case 2:	
			            	System.out.println("");
			            ob.priceSortList(); // 가격순 조회
			            
			            case 3:
			            	break;
			            }	// 서브 메뉴
			            
			            break;
			            
		            case 4:
		            	
//		            	System.out.println(mto.getMoney() + "원");
//		            	mao.moneyCheck();
//		            	System.out.println("재구현 예정,,,,,,,,");
//						break;
		            case 5:
		                SessionManager.logout();
		                break;

		        }
		    }
		}
	
}