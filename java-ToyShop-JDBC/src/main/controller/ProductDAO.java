package main.controller;

import java.sql.CallableStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import main.db.DBConn;
import main.login.Login;
import oracle.jdbc.OracleTypes;

public class ProductDAO {		// DB에 연결 처리 클래스 (Data Access Object)
	
	//  장바구니에 넣기
	
	public ProductDTO ProductInfo(String productName) {
	    ProductDTO dto = new ProductDTO();
	    Connection conn = DBConn.getConnection();
	    CallableStatement cstmt = null;
	    ResultSet rs = null;
	    String sql;
	   
	    try {
	        sql = "{call (?)}";  // getProductInfo는 제품 이름을 받아 해당 제품의 정보를 반환하는 프로시저 또는 쿼리라 가정합니다.
	        cstmt = conn.prepareCall(sql);
	        cstmt.setString(1, productName);
	        cstmt.registerOutParameter(2, Types.VARCHAR);  // 상품 이름
	        cstmt.registerOutParameter(3, Types.NUMERIC);  // 가격
	        cstmt.registerOutParameter(4, Types.NUMERIC);  // 수량
	        cstmt.execute();

	        // 프로시저에서 반환된 값들을 DTO에 설정
	        dto.setProductName(cstmt.getString(2));
	        dto.setPrice(cstmt.getInt(3));
	        dto.setQuantity(cstmt.getInt(4));

	    } catch (Exception e) {
	        System.out.println(e.toString());
	    } finally {
	        // ResultSet, Statement, Connection 등의 자원을 닫는 코드 필요
	    }

	    return dto;
	}
	
	
	// 제품 구매
	public void addToCart(ProductDTO dto) {
        // 해당 사용자의 장바구니에 제품 추가 로직 구현
        // 예: 사용자 ID에 해당하는 장바구니 테이블에 제품 정보 추가
		Connection conn = DBConn.getConnection();
	    CallableStatement cstmt = null;
	    String sql = "{call AddToCart(?,?,?,?)}";
	    try {
	        cstmt = conn.prepareCall(sql);
	        cstmt.setString(1, dto.getId());
	        cstmt.setString(2, dto.getProductName());
	        cstmt.setInt(3, dto.getPrice());  	
	        cstmt.setInt(4, dto.getQuantity());
	        // 사용자 ID 또는 다른 필요한 정보를 설정
	        cstmt.registerOutParameter(1, OracleTypes.VARCHAR);
	        cstmt.registerOutParameter(2, OracleTypes.VARCHAR);
	        cstmt.registerOutParameter(3, OracleTypes.NUMBER);
	        cstmt.registerOutParameter(4, OracleTypes.NUMBER);
	        cstmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // close resources
	        DBConn.close(conn, cstmt, null);
	    }
    }

    // 장바구니 조회 (구매목록)
    public List<ProductDTO> getCartItems() {
        // 해당 사용자의 장바구니에 담긴 제품들을 조회하여 리스트로 반환
        // 예: 사용자 ID에 해당하는 장바구니 테이블에서 제품 정보 조회
    	List<ProductDTO> cartItems = new ArrayList<>();
    	Connection conn = DBConn.getConnection();
        CallableStatement cstmt = null;
        ResultSet rs = null;
        
        try {
        	String sql = "{call getCartItems(?,?)}";
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, "사용자 주문 id");	// 진짜 주문 아이디 넣기
            cstmt.registerOutParameter(2, OracleTypes.CURSOR);

            // 프로시저 실행
            cstmt.execute();

            // out 파라미터의 값을 돌려받기
            rs = (ResultSet) cstmt.getObject(2);

            // 결과 처리
            while (rs.next()) {
            	ProductDTO dto = new ProductDTO();
            	dto.setProductId(rs.getString("product_id"));   // 열 인덱스 대신 열 이름 사용
                dto.setPrice(rs.getInt("price"));
                dto.setQuantity(rs.getInt("quantity"));
                dto.setProductName(rs.getString("product_name"));
                dto.setTotalprice(rs.getInt("price") * rs.getInt("quantity"));

                cartItems.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // close resources
            DBConn.close(conn, cstmt, rs);
        }
        return cartItems;
    }

    // 장바구니 구매
    public void purchaseFromCart(List<ProductDTO> cartItems) {
        // 장바구니에 있는 제품들을 실제 구매로 이관하는 로직 구현
        // 예: 구매한 제품들을 주문 테이블에 추가하고 재고 테이블에서 수량 갱신
    	Connection conn = DBConn.getConnection();
        CallableStatement cstmt = null;
        String sql = "{call purchase_from_cart(?)}";

        try {
            cstmt = conn.prepareCall(sql);
            // 사용자 ID 또는 다른 필요한 정보를 설정
            // cstmt.setString(1, userId);

            // 구매할 제품 정보를 배열 또는 구조체로 설정
            // ...

            cstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // close resources
            DBConn.close(conn, cstmt, null);
        }
    }

    // 장바구니 비우기
    public void clearCart() {
        // 해당 사용자의 장바구니를 비우는 로직 구현
        // 예: 사용자 ID에 해당하는 장바구니 테이블의 내용 삭제
    	Connection conn = DBConn.getConnection();
        CallableStatement cstmt = null;
        String sql = "{call clear_cart(?)}";

        try {
            cstmt = conn.prepareCall(sql);
            // 사용자 ID 또는 다른 필요한 정보를 설정
            // cstmt.setString(1, userId);

            cstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // close resources
            DBConn.close(conn, cstmt, null);
        }
    }
	
	// 제품 검색
	
	
	// 가격순으로 조회
	
	/////////////////////////////////////////////////////////////////////// 오늘까지
	
	
	// 제품 추가    (관리자)
	
	// 제품 수정	(관리자)
	
	
	// 제품 삭제
	public int dataDelete(String productId) {
		int result = 0;
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		String sql;
		try {
			sql = "{call deleteScore(?)}";
			
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, productId);	// 제품코드를 토대로 그 데이터 삭제
			result = cstmt.executeUpdate();
			cstmt.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}
	
	// 제품 목록 조회
	public List<ProductDTO> getAllProducts() {
		List<ProductDTO> lists = new ArrayList<ProductDTO>();
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		String sql;
//		ResultSet - 데이터 결과를 임시저장 해두는 곳
		try {
			sql = "{call listInquiry(?)}";
			
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
