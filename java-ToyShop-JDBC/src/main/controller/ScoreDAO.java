package main.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import main.db.DBConn;

import oracle.jdbc.OracleTypes;

public class ScoreDAO {		// DB�� ���� ó�� Ŭ���� (Data Access Object)
	
	// �߰�
	
	// ����
	
	// ����
	public int dataDelete(String hak) {
		int result = 0;
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		String sql;
		try {
			sql = "{call deleteScore(?)}";
			
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, hak);	// �й��� ���� �� ������ ����
			result = cstmt.executeUpdate();
			cstmt.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}
	
	// ��ȸ
	public List<ScoreDTO> getList() {
		List<ScoreDTO> lists = new ArrayList<ScoreDTO>();
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
				ScoreDTO dto = new ScoreDTO();
				
				// �±� ���� 
				dto.setProductId(rs.getString(1));
				dto.setProductName(rs.getString(2));
				dto.setPrice(rs.getInt(3));
			
				lists.add(dto);
			}
			rs.close();
			cstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return lists;
	}
	
	

	// �̸� �˻�
	// �й��˻�
}
