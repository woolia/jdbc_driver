package ex1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Program3_update {


	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		String title = "TEST3";
		String content = "YUYUYU";
		String files = "";
		int id = 2;
		
		// UPDATE 
		
		String url = "jdbc:oracle:thin:@localhost:1521/system"; // ���� ��� SID �� system �̶� ����
		String sql = "UPDATE NOTICE SET TITLE=?, CONTENT=?, FILES=? WHERE ID=?";
		// update �÷��� �������̸� , �� �÷��� ����������� 
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url , "c##zecrar","1234");
		PreparedStatement st = con.prepareStatement(sql);

		st.setString(1, title);
		st.setString(2, content);
		st.setString(3, files);
		st.setInt(4, id);
		
		int result = st.executeUpdate();

		System.out.println(result);
		
		st.close();
		con.close();
	}
}