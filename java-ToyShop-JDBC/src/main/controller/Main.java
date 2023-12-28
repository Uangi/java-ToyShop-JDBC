package main.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.db.DBConn;
import oracle.jdbc.OracleTypes;


public class Main {

	public static void main(String[] args) {
		Scanner sc  = new Scanner(System.in);
		ProductIO ob = new ProductIO();
		int ch;
		
		while(true)  {
			do {
				System.out.print("1.���� / 2. ���Ÿ�� / 3. ����� ��ȸ / 4. ����  : ");
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
				System.out.println("���α׷� ����");
				System.exit(0);
				
			}
		}
		
	}
	
	public List<ProductDTO> getAllProducts() {
		List<ProductDTO> lists = new ArrayList<ProductDTO>();
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		String sql;
//		ResultSet - ������ ����� �ӽ����� �صδ� ��
		try {
			sql = "{call tempOutput(?)}";
			
			cstmt = conn.prepareCall(sql);
			
			// out �Ķ����(�Ѿ���� CURSOR)�� �ڷ��� ����
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			
			// ���ν��� ����
			cstmt.executeQuery();
			
			// out�Ķ������ ���� �����ޱ� - ?�� �Ѿ�� �����͸� ����
			rs = (ResultSet)cstmt.getObject(1);	// ù��° ��
			while (rs.next()) {
				ProductDTO dto = new ProductDTO();
				
				dto.setProductId(rs.getString(1));
				dto.setProductName(rs.getString(2));
				dto.setPrice(rs.getInt(3));
				dto.setQuantity(rs.getInt(4));
				lists.add(dto);
			}
			rs.close();
			cstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return lists;
	}

}
