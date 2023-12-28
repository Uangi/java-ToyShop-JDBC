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

public class ProductDAO {		// DB�� ���� ó�� Ŭ���� (Data Access Object)
	
	//  ��ٱ��Ͽ� �ֱ�
	
	public ProductDTO ProductInfo(String productName) {
	    ProductDTO dto = new ProductDTO();
	    Connection conn = DBConn.getConnection();
	    CallableStatement cstmt = null;
	    ResultSet rs = null;
	    String sql;
	   
	    try {
	        sql = "{call (?)}";  // getProductInfo�� ��ǰ �̸��� �޾� �ش� ��ǰ�� ������ ��ȯ�ϴ� ���ν��� �Ǵ� ������ �����մϴ�.
	        cstmt = conn.prepareCall(sql);
	        cstmt.setString(1, productName);
	        cstmt.registerOutParameter(2, Types.VARCHAR);  // ��ǰ �̸�
	        cstmt.registerOutParameter(3, Types.NUMERIC);  // ����
	        cstmt.registerOutParameter(4, Types.NUMERIC);  // ����
	        cstmt.execute();

	        // ���ν������� ��ȯ�� ������ DTO�� ����
	        dto.setProductName(cstmt.getString(2));
	        dto.setPrice(cstmt.getInt(3));
	        dto.setQuantity(cstmt.getInt(4));

	    } catch (Exception e) {
	        System.out.println(e.toString());
	    } finally {
	        // ResultSet, Statement, Connection ���� �ڿ��� �ݴ� �ڵ� �ʿ�
	    }

	    return dto;
	}
	
	
	// ��ǰ ����
	public void addToCart(ProductDTO dto) {
        // �ش� ������� ��ٱ��Ͽ� ��ǰ �߰� ���� ����
        // ��: ����� ID�� �ش��ϴ� ��ٱ��� ���̺� ��ǰ ���� �߰�
		Connection conn = DBConn.getConnection();
	    CallableStatement cstmt = null;
	    String sql = "{call AddToCart(?,?,?,?)}";
	    try {
	        cstmt = conn.prepareCall(sql);
	        cstmt.setString(1, dto.getId());
	        cstmt.setString(2, dto.getProductName());
	        cstmt.setInt(3, dto.getPrice());  	
	        cstmt.setInt(4, dto.getQuantity());
	        // ����� ID �Ǵ� �ٸ� �ʿ��� ������ ����
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

    // ��ٱ��� ��ȸ (���Ÿ��)
    public List<ProductDTO> getCartItems() {
        // �ش� ������� ��ٱ��Ͽ� ��� ��ǰ���� ��ȸ�Ͽ� ����Ʈ�� ��ȯ
        // ��: ����� ID�� �ش��ϴ� ��ٱ��� ���̺��� ��ǰ ���� ��ȸ
    	List<ProductDTO> cartItems = new ArrayList<>();
    	Connection conn = DBConn.getConnection();
        CallableStatement cstmt = null;
        ResultSet rs = null;
        
        try {
        	String sql = "{call getCartItems(?,?)}";
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, "����� �ֹ� id");	// ��¥ �ֹ� ���̵� �ֱ�
            cstmt.registerOutParameter(2, OracleTypes.CURSOR);

            // ���ν��� ����
            cstmt.execute();

            // out �Ķ������ ���� �����ޱ�
            rs = (ResultSet) cstmt.getObject(2);

            // ��� ó��
            while (rs.next()) {
            	ProductDTO dto = new ProductDTO();
            	dto.setProductId(rs.getString("product_id"));   // �� �ε��� ��� �� �̸� ���
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

    // ��ٱ��� ����
    public void purchaseFromCart(List<ProductDTO> cartItems) {
        // ��ٱ��Ͽ� �ִ� ��ǰ���� ���� ���ŷ� �̰��ϴ� ���� ����
        // ��: ������ ��ǰ���� �ֹ� ���̺� �߰��ϰ� ��� ���̺��� ���� ����
    	Connection conn = DBConn.getConnection();
        CallableStatement cstmt = null;
        String sql = "{call purchase_from_cart(?)}";

        try {
            cstmt = conn.prepareCall(sql);
            // ����� ID �Ǵ� �ٸ� �ʿ��� ������ ����
            // cstmt.setString(1, userId);

            // ������ ��ǰ ������ �迭 �Ǵ� ����ü�� ����
            // ...

            cstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // close resources
            DBConn.close(conn, cstmt, null);
        }
    }

    // ��ٱ��� ����
    public void clearCart() {
        // �ش� ������� ��ٱ��ϸ� ���� ���� ����
        // ��: ����� ID�� �ش��ϴ� ��ٱ��� ���̺��� ���� ����
    	Connection conn = DBConn.getConnection();
        CallableStatement cstmt = null;
        String sql = "{call clear_cart(?)}";

        try {
            cstmt = conn.prepareCall(sql);
            // ����� ID �Ǵ� �ٸ� �ʿ��� ������ ����
            // cstmt.setString(1, userId);

            cstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // close resources
            DBConn.close(conn, cstmt, null);
        }
    }
	
	// ��ǰ �˻�
	
	
	// ���ݼ����� ��ȸ
	
	/////////////////////////////////////////////////////////////////////// ���ñ���
	
	
	// ��ǰ �߰�    (������)
	
	// ��ǰ ����	(������)
	
	
	// ��ǰ ����
	public int dataDelete(String productId) {
		int result = 0;
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		String sql;
		try {
			sql = "{call deleteScore(?)}";
			
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, productId);	// ��ǰ�ڵ带 ���� �� ������ ����
			result = cstmt.executeUpdate();
			cstmt.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}
	
	// ��ǰ ��� ��ȸ
	public List<ProductDTO> getAllProducts() {
		List<ProductDTO> lists = new ArrayList<ProductDTO>();
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		String sql;
//		ResultSet - ������ ����� �ӽ����� �صδ� ��
		try {
			sql = "{call listInquiry(?)}";
			
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
