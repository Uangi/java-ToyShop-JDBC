package main.controller;

import java.sql.Connection;

import java.sql.Statement;
import java.util.Scanner;

import main.db.DBConn;

public class Test1 {

	
	public static void main(String[] args) {

		/* 자바는 AutoCommit -> 트랜잭션을 false로 해놓고 3개의 데이터가 맞아야 들어감.*/
		
		Scanner sc = new Scanner(System.in);
		Connection conn = DBConn.getConnection();
		Statement stmt = null;
		String sql;
		int ch;
		int id;
		String name,birth,tel;
		
		try {
			while(true) {
				do {
					System.out.println("1. 입력 / 2. 출력 / 3. 종료");
					ch = sc.nextInt();
				} while(ch<1);
				
				switch(ch) {
				
				case 1 :
					conn.setAutoCommit(false);
					
					System.out.print("아이디 : ");
					id = sc.nextInt();
					
					System.out.print("이름 : ");
					name = sc.next();
					
					System.out.print("생일 : ");
					birth = sc.next();
					
					System.out.print("전화 : ");
					tel = sc.next();
					
	sql = String.format("insert into test1 (id,name) values (%d, '%s')",id,name);
					stmt = conn.createStatement();
					stmt.executeUpdate(sql);
			// String.format 사용 시 문자열 값을 받아와야 한다면 반드시 작은 따옴표로
			// 묶어줘야한다. 예 ) '%s'
					
	sql = String.format("insert into test2 (id,birth) values (%d, '%s')",id,birth);
					stmt = conn.createStatement();
					stmt.executeUpdate(sql);
					
	sql = String.format("insert into test3 (id,tel) values (%d, '%s')",id,tel);
					stmt = conn.createStatement();
					stmt.executeUpdate(sql);
					
					conn.commit();
					break;

				case 2 :
					break;
					
				case 3 :
					System.exit(0);
				}
				
			}
		} catch (Exception e) {

		}
		
	}

}
