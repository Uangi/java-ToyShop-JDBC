package main.controller;

import java.sql.CallableStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import main.db.DBConn;
import main.login.MemberDAO;
import main.login.MemberDTO;
import oracle.jdbc.OracleTypes;

public class ProductDAO {		// DB에 연결 처리 클래스 (Data Access Object)
	
	private static MemberDTO mto;
	 private static MemberDTO getMemberDTOInstance() {
	        if (mto == null) {
	            mto = new MemberDTO();
	            // mto에 필요한 초기화 작업 수행
	        }
	        return mto;
	    }
	// 제품 구매
	// 주문 ID를 기반으로 상품 정보 가져오기
	// 제품 추가 메서드 수정
	
	@SuppressWarnings("resource")
	
	public void addToCart(ProductDTO dto) {
	    Connection conn = DBConn.getConnection();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        // 주문 ID를 기반으로 상품 정보 가져오기
	        String getProductInfoSQL = "SELECT product_id, price, quantity FROM Product WHERE product_name = ?";
	        pstmt = conn.prepareStatement(getProductInfoSQL);
	        pstmt.setString(1, dto.getProductName());
	        rs = pstmt.executeQuery();

	        // 상품 정보가 있다면
	        if (rs.next()) {
	            String productCode = rs.getString("product_id");
	            int price = rs.getInt("price");
	            int quantity = rs.getInt("quantity");

	            // 주문에 상품 추가
	            String addToCartSQL = "INSERT INTO Cart(order_id, product_id, price, quantity) VALUES (?, ?, ?, ?)";
	            pstmt = conn.prepareStatement(addToCartSQL);
	            pstmt.setString(1, mto.getOrderId());
	            pstmt.setString(2, productCode);
	            pstmt.setInt(3, quantity); // 상품의 수량이 아닌, 구매 시 입력한 수량을 사용
	            pstmt.setInt(4, quantity);
	            pstmt.executeUpdate();

	            System.out.println("장바구니에 추가되었습니다.");
	        } else {
	            System.out.println("상품 정보를 가져올 수 없습니다.");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // close resources
	        DBConn.close(conn, pstmt, rs);
	    }
	}

	// 장바구니 조회 메서드 수정
	public List<ProductDTO> getCartItems() {
	    List<ProductDTO> cartItems = new ArrayList<>();
	    Connection conn = DBConn.getConnection();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        String getCartItemsSQL = "SELECT product_id, product_name, price, quantity FROM Cart WHERE order_id = ?";
	        pstmt = conn.prepareStatement(getCartItemsSQL);
	        pstmt.setString(1, mto.getOrderId());
	        rs = pstmt.executeQuery();

	        // 결과 처리
	        while (rs.next()) {
	            ProductDTO dto = new ProductDTO();
	            dto.setProductId(rs.getString("product_id"));
	            dto.setProductName(rs.getString("product_name"));
	            dto.setPrice(rs.getInt("price"));
	            dto.setQuantity(rs.getInt("quantity"));

	            cartItems.add(dto);
	        }

	        // 장바구니가 비어 있는 경우 메시지 출력
	        if (cartItems.isEmpty()) {
	            System.out.println("장바구니가 비어 있습니다.");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // close resources
	        DBConn.close(conn, pstmt, rs);
	    }

	    return cartItems;
	}

	 // 장바구니 구매
    public void purchaseFromCart(List<ProductDTO> cartItems) throws SQLException {
        Connection conn = DBConn.getConnection();
        PreparedStatement pstmt = null;

        try {
            // 여기서 구매 로직을 추가하세요.
            // 주문 테이블에 추가하고 재고 테이블에서 수량 갱신 등을 수행합니다.

        } finally {
            // close resources
            DBConn.close(conn, pstmt, null);
        }
    }

    // 장바구니 비우기
    public void clearCart() {
        Connection conn = DBConn.getConnection();
        PreparedStatement pstmt = null;

        try {
            String sql = "DELETE FROM Cart WHERE order_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mto.getOrderId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // close resources
            DBConn.close(conn, pstmt, null);
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
