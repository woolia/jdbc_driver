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
		
		String url = "jdbc:oracle:thin:@localhost:1521/system"; // 나의 경우 SID 가 system 이라서 넣음
		// String sql = "INSERT INTO NOTICE(TITLE , WRITER_ID , CONTENT , FILES) VALUES ('"+title+"' , '"+writerId+"' , '"+content+"' , '"+files+"')";
		// 덧셈연산도 간혹 사용하긴 하는데 이때 중요한 점은 문자열값이 '' 로 감싸줘야 한다
		// 엄청 복잡.. 그래서 데이터를 넣을 수 있는 도구가 존재함 
		String sql = "INSERT INTO NOTICE(TITLE , WRITER_ID , CONTENT , FILES) VALUES (?,?,?,?)";
		// 데이터를 넣을 곳은 ? 로 넣음 
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url , "c##zecrar","1234");
		PreparedStatement st = con.prepareStatement(sql); // PreparedStatement의 con.prepareStatement 을 사용하고 안에 sql 을 넣는다. 즉, 미리 sql문을 준비한다는 뜻  
		// st.setString(parameterIndex, x);
		// parameterIndex 는 ? 의 인덱스 , x 는 값
		st.setString(1, title); // 시작인덱스는 1
		st.setString(2, writerId);
		st.setString(3, content);
		st.setString(4, files);
		
		// 이렇게 하면서 sql문이 준비되어서 그냥 실행만 하면 됨
		// select 에서는 st.executeQuery(sql); 이었는데 insert, update , delete 에서는 안됨
		// st.executeUpdate() 를 사용해야함
		int result = st.executeUpdate();
		// return 값은 쿼리가 실행된 만큼 반환해줌 실행이 1번밖에 안되서 result = 1 이 된다.
		
		// INSERT , UPDATE , DELETE 를 할때는 ResultSet 이 필요가 없다 ResultSet은 무조건 SELECT 값을 가져올때만

		System.out.println(result);
		
		st.close();
		con.close();
	}
}