package main.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DBConn {

	private static Connection dbConn;
	
	public static Connection getConnection() {	// 연동 더 할 때 ip 번호 바꾸고 AgetConnection(), BgetConnection() 이런느낌
		if(dbConn == null) {
			try {
				String url = "jdbc:oracle:thin:@192.168.0.10:1521:xe";  // ip와 계정만으로 연결
				// [ip]:[포트번호]:[db명]
				String user = "JYP";
				String pwd = "a123";
				
				Class.forName("oracle.jdbc.driver.OracleDriver");	// oracle.jdbc.driver
				dbConn = DriverManager.getConnection(url,user,pwd);
				
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		return dbConn;
	}

	public static void close(Connection conn, CallableStatement cstmt, ResultSet rs) {	// db 닫기
		if(dbConn!=null) {
			
			try { 
				if(!dbConn.isClosed()) {
					dbConn.close();
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		dbConn = null; // db 닫았다면 초기화 (안하면 adapter 오류)
	}

	

}