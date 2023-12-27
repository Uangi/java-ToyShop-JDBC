package main.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import main.db.DBConn;

import oracle.jdbc.OracleTypes;

public class ScoreDAO {		// DB에 연결 처리 클래스 (Data Access Object)
	
	// 추가
	
	// 수정
	
	// 삭제
	public int dataDelete(String hak) {
		int result = 0;
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		String sql;
		try {
			sql = "{call deleteScore(?)}";
			
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, hak);	// 학번을 토대로 그 데이터 삭제
			result = cstmt.executeUpdate();
			cstmt.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}
	
	// 조회
	public List<ScoreDTO> getList() {
		List<ScoreDTO> lists = new ArrayList<ScoreDTO>();
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
				ScoreDTO dto = new ScoreDTO();
				
				// 태그 보류 
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
	
	

	// 이름 검색
	// 학번검색
}
