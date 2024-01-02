package main.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.db.DBConn;
import main.login.MemberDTO;
import main.login.SessionManager;

public class ProductDAO {		// DB에 연결 처리 클래스 (Data Access Object)
	private Connection connection; // 데이터베이스 연결 객체
	 public ProductDAO() {
	        this.connection = DBConn.getConnection();
	    }
	 private static MemberDTO mto;					// 싱글톤
	 private static MemberDTO getMemberDTOInstance() {
	        if (mto == null) {
	            mto = new MemberDTO();
	            // mto에 필요한 초기화 작업 수행
	        }
	        return mto;
	    }
	 
	// 주문 ID를 기반으로 상품 정보 가져오기
	// 제품 추가 메서드 수정
	 
		// 구매 목록 조회하기
		// 로그인하면서 입력했던 회원 ID, 제품코드, Temp에 저장된 해당 회원(로그인하면서 입력했던 회원ID)이 입력한 상품명,
		// 상품테이블과 연동된 해당 상품의 가격, 해당 회원이 입력했던(temp에 저장됐던) 수량, (가격 * 수량) 출력
		// 만약 상품이 여러개라면 SUM(입력했던 상품명의 가격 * 해당 회원이 입력했던 수량) 이런 느낌으로 출력
		 

	 public int choiceProduct(ProductDTO dto) {	// 물품 고르기
		 		int result = 0;
	    		Connection conn = DBConn.getConnection();
	    		PreparedStatement pstmt = null;	
	    		String sql;
	            // Temp 테이블에 구매 정보 저장
	    		try {
	            sql = "INSERT INTO Temp(order_id, product_name, quantity) VALUES (?, ?, ?) ";
	            	pstmt = conn.prepareStatement(sql);
	                pstmt.setString(1, SessionManager.getCurrentUser().getOrderId());
	                pstmt.setString(2, dto.getProductName());
	                pstmt.setInt(3, dto.getQuantity());
	                pstmt.executeUpdate();
	                
	                conn.commit();
	                
	             // Cart 테이블에 구매 정보 저장
	             sql = "INSERT INTO Cart(cart_id, order_id, product_id, ordered_product_name, product_price, ordered_quantity, total_price) ";
	             sql += "SELECT cart_seq.NEXTVAL, b.order_id, p.product_id, t.product_name, p.price, t.quantity,(p.price * t.quantity) "; // cart_seq.NEXTVAL
	             sql += "FROM Temp t ";
	             sql += "JOIN Buyer b ON t.order_id = b.order_id ";
	             sql += "JOIN Product p ON t.product_name = p.product_name ";
	                pstmt = conn.prepareStatement(sql);       
	                result = pstmt.executeUpdate();
	                pstmt.close();
	                
	        } catch (SQLException e) {
	        	
	            e.printStackTrace();
	        }
				return result;
	    }
	 
	 // 구매 목록 조회
	    public List<ProductDTO> viewCart() {
	        List<ProductDTO> cartItems = new ArrayList<>();
    		Connection conn = DBConn.getConnection();
    		PreparedStatement pstmt = null;	
    		ResultSet rs = null;
    		String sql;
	        try {
	            // Cart 테이블에서 해당 회원의 장바구니 정보 조회
	            sql = "SELECT cart_id, ordered_product_name, product_price, ordered_quantity, total_price FROM Cart WHERE order_id = ?";
	            pstmt = conn.prepareStatement(sql);
	               pstmt.setString(1, SessionManager.getCurrentUser().getOrderId());
	               rs = pstmt.executeQuery();
	                    while (rs.next()) {
	                        ProductDTO cartItem = new ProductDTO();
	                        
	                        cartItem.setCartId(rs.getInt("cart_id")); 
	                        cartItem.setProductName(rs.getString("ordered_product_name"));
	                        cartItem.setPrice(rs.getInt("product_price")); //"product_price"
	                        cartItem.setQuantity(rs.getInt("ordered_quantity")); // "ordered_quantity"
	                        cartItem.setTotalprice(rs.getInt("total_price"));
	                        cartItems.add(cartItem);
	                    }
	                    rs.close();
	                    DBConn.close(conn, pstmt);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return cartItems;
	    }

	    																				///// 장바구니 구매결정
//    public void purchaseFromCart(List<ProductDTO> cartItems) throws SQLException {
//        Connection conn = DBConn.getConnection();
//        PreparedStatement pstmt = null;
//            DBConn.close(conn, pstmt);
//    }
    
    																					//// temp 비우기
    public void clearTemp() {	// 구매하지 않을 경우 cart는 남겨두고 temp만 지워두기. ( 안그러면 새로 구매하려는 물품 입력 시 중복됨.)
        Connection conn = DBConn.getConnection();
        PreparedStatement pstmt = null;
        String sql = null;
        try {
        	
        	sql = "DELETE FROM Temp WHERE order_id = ?";
        	pstmt = conn.prepareStatement(sql);
        	pstmt.setString(1, SessionManager.getCurrentUser().getOrderId());
        	pstmt.executeUpdate();
        	
//        	sql = "ALTER SEQUENCE cart_seq RESTART WITH 1 NOCACHE";
//        	pstmt = conn.prepareStatement(sql);
//        	pstmt.executeUpdate();
        	
            conn.commit();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // close resources
            DBConn.close(conn, pstmt);
        }
    }	
    
																						//// cart 비우기
	public void clearCart() {	// 구매하면 temp와 cart 둘 다 지우기
        Connection conn = DBConn.getConnection();
        PreparedStatement pstmt = null;
        String sql = null;
        try {
        	
        	sql = "DELETE FROM Temp WHERE order_id = ?";	// 임시로 담았던 구매목록(입력한 상품명, 수량 삭제)
        	pstmt = conn.prepareStatement(sql);
        	pstmt.setString(1, SessionManager.getCurrentUser().getOrderId());
        	pstmt.executeUpdate();
        	
        	sql = "DELETE FROM Cart WHERE order_id = ?";	// 카트에 담은 구매목록 삭제
        	pstmt = conn.prepareStatement(sql);
        	pstmt.setString(1, SessionManager.getCurrentUser().getOrderId());
        	pstmt.executeUpdate();
//        	sql = "DROP SEQUENCE cart_seq ";	
//        	pstmt = conn.prepareStatement(sql);
//        	pstmt.executeUpdate();
//        	
//        	sql = "CREATE SEQUENCE cart_seq START WITH 1 INCREMENT BY 1 ";	// 시퀀스 재생성
//        	pstmt = conn.prepareStatement(sql);
//        	pstmt.executeUpdate();
        	
        	String[] queries = {
        		    "DROP SEQUENCE cart_seq",	// 시퀀스 삭제 ( 번호 초기화 )
        		    "CREATE SEQUENCE cart_seq START WITH 1 INCREMENT BY 1" }; // 시퀀스 재생성
        		
        		for (String query : queries) {
        		    pstmt = conn.prepareStatement(query);
        		    pstmt.executeUpdate();
        		}
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // close resources
            DBConn.close(conn, pstmt);
        }
    }	
    
	//																					//// 장바구니 품목 삭제
    public void choiceCancel(int cartId) {
        Connection conn = DBConn.getConnection();
        PreparedStatement pstmt = null;

        try {
            // Cart 테이블에서 해당 품목 삭제
            String sql = "DELETE FROM Cart WHERE cart_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cartId);
            int rowCount = pstmt.executeUpdate();

            // 삭제된 품목이 있다면 출력
            if (rowCount > 0) {
                System.out.println(cartId + "번 품목이 취소되었습니다.");
            } else {
                System.out.println("취소할 품목이 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConn.close(conn, pstmt);
        }
    }
    
	//																					//// 재고 차감 메서드
    public int subtractFromStock(ProductDTO dto) {
        Connection conn = DBConn.getConnection();
        PreparedStatement pstmtStock= null;
        String sql = null;
        int result = 0;
        try {
            // 재고에서 수량 차감
            sql = "UPDATE Product SET quantity = quantity - ? WHERE product_name = ?";
            pstmtStock = conn.prepareStatement(sql);
            pstmtStock.setInt(1, dto.getQuantity()); // 구매 시 입력한 수량
            pstmtStock.setString(2, dto.getProductName());
            result = pstmtStock.executeUpdate();
            
            conn.commit();
            System.out.println("재고에서 남은 "+ dto.getProductName() + "의 개수는 " + dto.getQuantity() + "개입니다.");
            // 가장 나중에 입력한 재고 설명
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // close resources
            DBConn.close(conn, pstmtStock);
        }
		return result;
    }
	//																					//// 제품명 검색
    public List<ProductDTO> productSearch(String name) {				
    	
		List<ProductDTO> lists = new ArrayList<ProductDTO>();
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "select * from Product ";
			sql += "where product_name like ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+name+"%");
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ProductDTO dto = new ProductDTO();
				dto.setProductId(rs.getString(1));
				dto.setProductName(rs.getString(2));
				dto.setPrice(rs.getInt(3));
				dto.setQuantity(rs.getInt(4));
			
				lists.add(dto);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return lists;
	}
	
	//																					//// 가격순으로 조회
    public List<ProductDTO> descending() {				

		List<ProductDTO> lists = new ArrayList<ProductDTO>();
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "select * from Product order by price DESC";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ProductDTO dto = new ProductDTO();
				dto.setProductId(rs.getString(1));
				dto.setProductName(rs.getString(2));
				dto.setPrice(rs.getInt(3));
				dto.setQuantity(rs.getInt(4));
			
				lists.add(dto);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return lists;
	}
	
	
    /////				상품목록 조회															////// 제품 목록 조회
	public List<ProductDTO> getAllProducts() {
		List<ProductDTO> lists = new ArrayList<ProductDTO>();
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "select * from product ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ProductDTO dto = new ProductDTO();
				dto.setProductId(rs.getString(1));
				dto.setProductName(rs.getString(2));
				dto.setPrice(rs.getInt(3));
				dto.setQuantity(rs.getInt(4));
			
				lists.add(dto);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return lists;
	}
	
	
	
	
}
