package main.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import main.db.DBConn;
import main.login.MemberDTO;
import main.controller.ProductDTO;

public class AdminDAO {

	// 추가
	public int dataInsert(ProductDTO dto) {

		int result = 0;
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		String sql;
		try {
			sql = "insert into product (product_id, product_name, price, quantity) ";
			sql += "values (?, ?, ?, ?) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getProductId());
			pstmt.setString(2, dto.getProductName());
			pstmt.setInt(3, dto.getPrice());
			pstmt.setInt(4, dto.getQuantity());
			result = pstmt.executeUpdate();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}

	// 수정
	public int dataUpdate(ProductDTO dto) {
		int result = 0;
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "update Product set product_name = ?, price = ?, quantity = ? ";
			sql += "where product_id =?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getProductName());
			pstmt.setInt(2, dto.getPrice());
			pstmt.setInt(3, dto.getQuantity());
			pstmt.setString(4, dto.getProductId());
			result = pstmt.executeUpdate();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}

	// 삭제
	public int dataDelete(int productId) {
		int result = 0;
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		String sql;
		try {
			sql = "delete from Product where product_id = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, productId); // 학번을 토대로 그 데이터 삭제
			result = pstmt.executeUpdate();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}

	// 회원 목록 조회
	public List<MemberDTO> dataCheck() {
		List<MemberDTO> lists = new ArrayList<MemberDTO>();
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "select * from Buyer ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MemberDTO mto = new MemberDTO();
				mto.setOrderId(rs.getString(1));
				mto.setOrderPwd(rs.getString(2));
				mto.setMoney(rs.getInt(3));

				lists.add(mto);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return lists;
	}
}
