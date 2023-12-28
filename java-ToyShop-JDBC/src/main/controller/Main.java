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
				System.out.print("1.구매 / 2. 구매목록 / 3. 재고목록 조회 / 4. 종료  : ");
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
				System.out.println("\n------------------------ 재고목록 ------------------------");
				System.out.println("|\t\t\t\t\t\t\t" + " |");
	            System.out.println("       제품코드\t\t상품명\t\t가격\t   수량");
	            System.out.println("----------------------------------------------------------");
	            ob.List();
				System.out.println("|\t\t\t\t\t\t\t" + " |");
	            System.out.println("----------------------------------------------------------");
				break;
				
				
				
			case 4:
				System.out.println("프로그램 종료");
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
//		ResultSet - 데이터 결과를 임시저장 해두는 곳
		try {
			sql = "{call tempOutput(?)}";
			
			cstmt = conn.prepareCall(sql);
			
			// out 파라미터(넘어오는 CURSOR)의 자료형 설정
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			
			// 프로시저 실행
			cstmt.executeQuery();
			
			// out파라미터의 값을 돌려받기 - ?로 넘어온 데이터를 받음
			rs = (ResultSet)cstmt.getObject(1);	// 첫번째 값
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
