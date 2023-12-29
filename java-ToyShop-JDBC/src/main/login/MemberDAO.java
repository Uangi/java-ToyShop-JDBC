package main.login;

import java.sql.CallableStatement;
import java.sql.Connection;

import main.db.DBConn;

public class MemberDAO {
	
	public int userRegister() {
			MemberDTO mto = new MemberDTO();
			
			int result = 0;
			Connection conn = DBConn.getConnection();
			CallableStatement cstmt = null;	
			String sql;
			
			try {
				sql = "{call RegisterUser(?,?)}";   // CallableStatement는 해당 값을 ?로 표기하면 된다.
				cstmt = conn.prepareCall(sql);
				
				cstmt.setString(1, mto.getOrderId());
				cstmt.setString(2, mto.getOrderPwd());
				
				result = cstmt.executeUpdate();
				cstmt.close();
				
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			return result;
		}
	
	
	
}
	
