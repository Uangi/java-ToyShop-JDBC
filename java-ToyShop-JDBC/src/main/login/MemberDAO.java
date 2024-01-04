package main.login;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import main.db.DBConn;

public class MemberDAO {

	private static MemberDAO instance;

	public MemberDAO() {
	} // 싱글톤 생성 - 두 개의 다른 메소드에서 클래스객체 사용

	public static MemberDAO getInstance() {
		if (instance == null) {
			instance = new MemberDAO();
		}
		return instance;
	}

	// 회원가입
	public int userRegister(MemberDTO mto) {
		int result = 0;
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		String sql;
		try {
			sql = "{call RegisterUser(?,?,?)}";
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, mto.getOrderId());
			cstmt.setString(2, mto.getOrderPwd());
			cstmt.setInt(3, mto.getMoney());
			result = cstmt.executeUpdate();
			cstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {

		}
		return result;
	}

	// DB 내부에 입력한 order_id와 order_pwd가 일치하는지
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
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			DBConn.close(conn, pstmt);
		}
		return member;
	}

	// 사용자 정보 가져오기
	public MemberDTO getMemberInfo(String orderId) {
		MemberDTO member = null;
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Buyer WHERE order_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orderId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				member = new MemberDTO();
				member.setOrderId(rs.getString("order_id"));
				member.setOrderPwd(rs.getString("order_pwd"));
				member.setMoney(rs.getInt(3));
			}
			rs.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			DBConn.close(conn, pstmt);
		}
		return member;
	}

	// 구매 후 잔액 업데이트
	public void moneyUpdate(MemberDTO mto) {
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE Buyer SET money = ? WHERE order_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mto.getMoney());
			pstmt.setString(2, mto.getOrderId());
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			DBConn.close(conn, pstmt);
		}
	}

	// 잔액 조회
	public MemberDTO moneyCheck() { // 매개변수에 String Order_id로 해보기
		MemberDTO member = null;
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String orderId = SessionManager.getCurrentUser().getOrderId(); // 현재 로그인한 사용자의 orderId 가져오기
		String sql = "SELECT money FROM Buyer WHERE order_id = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orderId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				member = new MemberDTO();
				member.setMoney(rs.getInt("money"));
			}
			rs.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			DBConn.close(conn, pstmt);
		}
		System.out.println(member + "원");
		return member;
	}
}
