package main.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DBConn {

	private static Connection dbConn;
	
	public static Connection getConnection() {	// ���� �� �� �� ip ��ȣ �ٲٰ� AgetConnection(), BgetConnection() �̷�����
		if(dbConn == null) {
			try {
				String url = "jdbc:oracle:thin:@192.168.16.5:1521:xe";  // ip�� ���������� ����
				// [ip]:[��Ʈ��ȣ]:[db��]
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

	public static void close(Connection conn, CallableStatement cstmt, ResultSet rs) {	// db �ݱ�
		if(dbConn!=null) {
			
			try { 
				if(!dbConn.isClosed()) {
					dbConn.close();
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		dbConn = null; // db �ݾҴٸ� �ʱ�ȭ (���ϸ� adapter ����)
	}

	

}
