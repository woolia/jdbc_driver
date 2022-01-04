package ex1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Program2_insert {


	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		String title = "TEST2";
		String writerId = "newlec";
		String content = "hahaha";
		String files = "";
		
		String url = "jdbc:oracle:thin:@localhost:1521/system"; // ���� ��� SID �� system �̶� ����
		// String sql = "INSERT INTO NOTICE(TITLE , WRITER_ID , CONTENT , FILES) VALUES ('"+title+"' , '"+writerId+"' , '"+content+"' , '"+files+"')";
		// �������굵 ��Ȥ ����ϱ� �ϴµ� �̶� �߿��� ���� ���ڿ����� '' �� ������� �Ѵ�
		// ��û ����.. �׷��� �����͸� ���� �� �ִ� ������ ������ 
		String sql = "INSERT INTO NOTICE(TITLE , WRITER_ID , CONTENT , FILES) VALUES (?,?,?,?)";
		// �����͸� ���� ���� ? �� ���� 
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url , "c##zecrar","1234");
		PreparedStatement st = con.prepareStatement(sql); // PreparedStatement�� con.prepareStatement �� ����ϰ� �ȿ� sql �� �ִ´�. ��, �̸� sql���� �غ��Ѵٴ� ��  
		// st.setString(parameterIndex, x);
		// parameterIndex �� ? �� �ε��� , x �� ��
		st.setString(1, title); // �����ε����� 1
		st.setString(2, writerId);
		st.setString(3, content);
		st.setString(4, files);
		
		// �̷��� �ϸ鼭 sql���� �غ�Ǿ �׳� ���ุ �ϸ� ��
		// select ������ st.executeQuery(sql); �̾��µ� insert, update , delete ������ �ȵ�
		// st.executeUpdate() �� ����ؾ���
		int result = st.executeUpdate();
		// return ���� ������ ����� ��ŭ ��ȯ���� ������ 1���ۿ� �ȵǼ� result = 1 �� �ȴ�.
		
		// INSERT , UPDATE , DELETE �� �Ҷ��� ResultSet �� �ʿ䰡 ���� ResultSet�� ������ SELECT ���� �����ö���

		System.out.println(result);
		
		st.close();
		con.close();
	}
}