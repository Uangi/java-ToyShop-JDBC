package main.controller;

import java.util.Scanner;

import main.login.MemberIO;
import main.login.SessionManager;


public class Main {

	public static void main(String[] args) {
		Scanner sc  = new Scanner(System.in);
		ProductIO ob = new ProductIO();
		MemberIO mi = new MemberIO();
		int ch;
		
		mi.firstSession();
		while(true)  {
			do {
				System.out.print("1. ���� / 2. ���Ÿ�� / 3. ����� ��ȸ / 4. �α׾ƿ� / 5. ����  : ");
				ch = sc.nextInt();
			} while(ch < 1);
			
			switch(ch) {

			case 1:
				ob.basket();
				break;
				
			case 2:
				ob.cartViewAndPurchase();
				break;
				
			case 3:
				System.out.println("\n------------------------ ����� ------------------------");
				System.out.println("|\t\t\t\t\t\t\t" + " |");
	            System.out.println("       ��ǰ�ڵ�\t\t��ǰ��\t\t����\t   ����");
	            System.out.println("----------------------------------------------------------");
	            ob.List();
				System.out.println("|\t\t\t\t\t\t\t" + " |");
	            System.out.println("----------------------------------------------------------");
				break;
				
			case 4:
				SessionManager.logout();
				
			case 5:
				System.out.println("���α׷� ����");
				System.exit(0);
				
			}
		}
		
	}
	
}
