package main.controller;

import java.sql.Connection;

import java.sql.Statement;
import java.util.Scanner;

import main.db.DBConn;

public class Test1 {

	
	public static void main(String[] args) {

		/* �ڹٴ� AutoCommit -> Ʈ������� false�� �س��� 3���� �����Ͱ� �¾ƾ� ��.*/
		
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
					System.out.println("1. �Է� / 2. ��� / 3. ����");
					ch = sc.nextInt();
				} while(ch<1);
				
				switch(ch) {
				
				case 1 :
					conn.setAutoCommit(false);
					
					System.out.print("���̵� : ");
					id = sc.nextInt();
					
					System.out.print("�̸� : ");
					name = sc.next();
					
					System.out.print("���� : ");
					birth = sc.next();
					
					System.out.print("��ȭ : ");
					tel = sc.next();
					
	sql = String.format("insert into test1 (id,name) values (%d, '%s')",id,name);
					stmt = conn.createStatement();
					stmt.executeUpdate(sql);
			// String.format ��� �� ���ڿ� ���� �޾ƿ;� �Ѵٸ� �ݵ�� ���� ����ǥ��
			// ��������Ѵ�. �� ) '%s'
					
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
