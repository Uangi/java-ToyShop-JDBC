package main.login;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import main.db.DBConn;
import oracle.jdbc.OracleTypes;

public class MemberDAO {
	
	
	private static MemberDAO instance;
	
		public MemberDAO() { }
	
		public static MemberDAO getInstance() {
        
			if (instance == null) {
            instance = new MemberDAO();
        }
        return instance;
    }
		
		public int userRegister(MemberDTO mto) {	// 회원가입
	    int result = 0;
	    Connection conn = DBConn.getConnection();
	    CallableStatement cstmt = null;
	    String sql;

	    try {
	        sql = "{call RegisterUser(?,?)}";   
	        cstmt = conn.prepareCall(sql);

	        cstmt.setString(1, mto.getOrderId());
	        cstmt.setString(2, mto.getOrderPwd());

	        result = cstmt.executeUpdate();
	        cstmt.close();

	        
	    } catch (Exception e) {
	        System.out.println(e.toString());
	    } finally {
	        // 적절한 자원 해제 코드 추가
	    }
	    return result;
	}
		
		 public MemberDTO loginCheck(String orderId, String orderPwd) {
		        MemberDTO member = null;
		        Connection conn = DBConn.getConnection();
		        PreparedStatement pstmt = null;
		        ResultSet rs = null;
		        String sql = "select * from Buyer where order_id = ? AND order_pwd = ?";

		        try {
		            pstmt = conn.prepareStatement(sql);
		            pstmt.setString(1, orderId);
		            pstmt.setString(2, orderPwd);

		            rs = pstmt.executeQuery();

		            if (rs.next()) {
		                member = new MemberDTO();
		                member.setOrderId(rs.getString("order_id"));
		                member.setOrderPwd(rs.getString("order_pwd"));
		            }
		            rs.close();
			        pstmt.close();
		        } catch (Exception e) {
		            System.out.println(e.toString());
		        } 

		        return member;
		    }
	
	
}
	
