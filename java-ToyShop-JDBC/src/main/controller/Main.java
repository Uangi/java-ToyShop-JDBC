package main.controller;

import java.sql.SQLException;
import java.util.Scanner;

import main.login.MemberIO;
import main.login.SessionManager;


public class Main {

		public static void main(String[] args) {
		    Scanner sc  = new Scanner(System.in);
		    ProductIO ob = new ProductIO();
		    MemberIO mi = new MemberIO();
		    int ch;

		    while (true) {
		        if (SessionManager.getCurrentUser() == null) {
		            mi.firstSession();
		            System.out.print("\n1. 구매 / 2. 구매목록 / 3. 재고목록 조회 / 4. 로그아웃 / 5. 종료 : ");
		            ch = sc.nextInt();
		        } else {
		            System.out.print("\n1. 구매 / 2. 구매목록 / 3. 재고목록 조회 / 4. 로그아웃 / 5. 종료 : ");
		            ch = sc.nextInt();
		        }

		        switch (ch) {
		            case 1:
		                if (SessionManager.getCurrentUser() == null) {
		                    mi.login();
		                } else {
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
		            	System.out.println("\n------------------------ 재고목록 ------------------------");
						System.out.println("|\t\t\t\t\t\t\t" + " |");
			            System.out.println("       제품코드\t\t상품명\t\t가격\t   수량");
			            System.out.println("----------------------------------------------------------");
			            ob.List();
						System.out.println("|\t\t\t\t\t\t\t" + " |");
			            System.out.println("----------------------------------------------------------");
						break;
		                
		                
		            case 4:
		                SessionManager.logout();
		                break;

		            case 5:
		                System.out.println("프로그램 종료");
		                System.exit(0);
		                break;
		        }
		    }
		}
	
}