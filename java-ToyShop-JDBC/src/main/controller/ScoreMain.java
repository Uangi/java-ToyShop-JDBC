package main.controller;

import java.util.Scanner;
import main.controller.*;

public class ScoreMain {

	public static void main(String[] args) {
		Scanner sc  = new Scanner(System.in);
		ScoreIO ob = new ScoreIO();
		int ch;
		
		while(true)  {
			do {
				System.out.print("1.추가 / 2. 조회 / 3. 종료  : ");
				ch = sc.nextInt();
			} while(ch < 1);
			
			switch(ch) {
			case 1:
				ob.insert();
				break;
				
			case 2:
				ob.inquery();
				break;
			case 3:
				System.out.println("프로그램 종료");
				System.exit(0);
				
			}
		}
		
	}

}
