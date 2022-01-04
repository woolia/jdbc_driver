package ex1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Program4_delete {


	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		int id = 2;
		
		// delete
		
		String url = "jdbc:oracle:thin:@localhost:1521/system"; 
		String sql = "DELETE NOTICE WHERE ID = ?";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url , "c##zecrar","1234");
		PreparedStatement st = con.prepareStatement(sql);

		st.setInt(1, id);
		
		int result = st.executeUpdate();

		System.out.println(result);
		
		st.close();
		con.close();
	}
}