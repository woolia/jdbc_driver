package ex1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Program_select {

	// 1. JDBC Driver �ε� : Class.forName("oracle.jdbc.driver.OracleDriver");
	// 2. Connection ���� : Connection con = DriverManager.getConnection(...);
	// 3. Statement ���൵�� : Statement st = con.createStatement();
	// 4. ResultSet ����� ������ : ResultSet rs = st.executeQuery(sql);
	// 5. rs.next(); ��ġ�� ���� ������
	// 6. ����ȭ : String title = rs.getString("title");
	
	
	/*
	 * ������ ���̺�
	 * 
	 * 
	 * 
 // ���� �߻� FILES VARCHAR(5000) : ORA-00910: ���������� ������ ���̰� �ʹ� ��ϴ� 00910. 00000 -  "specified length too long for its datatype" , REGDATE DATETIME : ORA-00902: ������������ �������մϴ� 00902. 00000 -  "invalid datatype"
 * COMMENT ���̺� ���� ORA-00903: ���̺���� �������մϴ� 00903. 00000 -  "invalid table name"
 *  FILES VARCHAR(5000) -> FILES VARCHAR(4000) ���� ���� https://okky.kr/article/26396 �ؼ� 
 *  REGDATE DATETIME -> DATETIME �� DATE �� ����
 *  COMMENT -> COMMENTS �� ����


CREATE TABLE NOTICE(
     ID INT,
     TITLE VARCHAR(100),
     WRITER_ID VARCHAR(50),
     CONTENT VARCHAR(3000),
     REGDATE DATE,
     HIT INT,
     FILES VARCHAR(4000)
     );

CREATE TABLE ROLE(
     ID VARCHAR(50),
     DISCRIPTION VARCHAR(500)
     );

CREATE TABLE MEMBER_ROLE(
     MEMBER_ID VARCHAR(50),
     ROLE_ID VARCHAR(50)
     );

CREATE TABLE COMMENTS(
     ID INT,
     CONTENT VARCHAR(2000),
     REGDATE DATE,
     WRITER_ID VARCHAR(50),
     NOTICE_ID INT
     );

CREATE TABLE MEMBER(
     ID VARCHAR(50),
     PWD VARCHAR(50),
     NAME VARCHAR(50),
     GENDER VARCHAR(2),
     BIRTHDAY VARCHAR(10),
     PHONE VARCHAR(13),
     REGDATE DATE,
     EMAIL VARCHAR(200)
     );

INSERT INTO NOTICE VALUES(1,'text','test1','aaa',SYSDATE,1,'filename');
���⼭ SYSDATE�� ����ð��� �ǹ� �÷��� ���������� DATE or TIMESTAMP �� ��츸 �����ϴ�.

commit;
����Ŭ������ DB�� �ݿ��Ҷ��� �ݵ�� �������� commit �� ����� DB�� �ݿ��� ������ Ȯ�ε��� ���� ��� 

	 * */
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		/*
		String url = "jdbc:oracle:thin:@localhost:1521/system"; // ���� ��� SID �� system �̶� ����
		String sql = "SELECT * FROM NOTICE";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url , "c##zecrar","1234");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		*/
		
		/*
		while(rs.next()) {// ������ ���� ������ 
			String title = rs.getString("title");
			int id = rs.getInt("ID");
			String writer_id = rs.getString("WRITER_ID");
			String content = rs.getString("CONTENT");
			Date regdate = rs.getDate("REGDATE");
			// Date Ÿ���� java.util.date �� java.sql.date �� �ִµ� java.util.date�� �ؾ���
			int hit = rs.getInt("id");
			String files = rs.getString("FILES");
			
			System.out.println("title : "+ title + " id : " + id + " writer_id : " + writer_id + " content : " + content + " regdate : " + regdate + " hit : " + hit + " files : " + files);
		}
		*/
		// ��ȸ���� 10 �̻��� ���
		
		/*
		while(rs.next()) {// ������ ���� ������ 
			if(rs.getInt("HIT") >= 10) {
				String title = rs.getString("title");
				int id = rs.getInt("ID");
				String writer_id = rs.getString("WRITER_ID");
				String content = rs.getString("CONTENT");
				Date regdate = rs.getDate("REGDATE");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
				
				System.out.println("title : "+ title + " id : " + id + " writer_id : " + writer_id + " content : " + content + " regdate : " + regdate + " hit : " + hit + " files : " + files);
			}
		}
		*/
		
		// �̷��� �ϸ� �ȵȴ�.
		// �׷��� ��� �ؾ��ϴ��� -> sql ���� where���� ����ؼ� hit�� 10 �̻��� ��츸 ���������� ��
		// �� �̷��� �ϴ���?? -> ���࿡ �����Ͱ� 10000�� 100000��  �ִٰ� �ϸ� �ش� �ڵ�� 10000,100000���� ��� �����͸� �����ͼ� ���ǹ��� üũ�Ͽ� �޸� �� �ð��� ���� �Һ��ϰ� �ȴ�
		// �׷��� sql ���� where ���� ���� ���ǿ� �ش��ϴ� �����͸� �������� �ð����� ���� �� ����
		
		// ---- �ذ��� ----
		
		String url = "jdbc:oracle:thin:@localhost:1521/orclpdb";
		// sql �� where ���� �־ DB���� ���ǿ� �´� �����͸� ������ 
		String sql = "SELECT * FROM NOTICE WHERE HIT >= 10";
		
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url , "c##zecrar","1234");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next()) {// ������ ���� ������ 
			String title = rs.getString("title");
			int id = rs.getInt("ID");
			String writer_id = rs.getString("WRITER_ID");
			String content = rs.getString("CONTENT");
			Date regdate = rs.getDate("REGDATE");
			int hit = rs.getInt("HIT");
			String files = rs.getString("FILES");
			
			System.out.println("title : "+ title + " id : " + id + " writer_id : " + writer_id + " content : " + content + " regdate : " + regdate + " hit : " + hit + " files : " + files);
		}
		
		rs.close();
		st.close();
		con.close();
	}

}


/*
 *  �� Ʈ����� ó��
 *  - �ϳ��� ������ ����Ǳ� �ٶ�� ������ ������ �ǹ� (= ���� �������, ������ �������)
 *  EX)
 *  UPDATE NOTICE SET PUB = 1 WHERE ID IN (2,4,6,8,10);
 *  UPDATE NOTICE SET PUB = 0 WHERE ID IN (1,3,5,7,9);
 *  �� �ΰ��� SQL�� �ϳ��� ó���ϰ� ������
 *  ���࿡ UPDATE NOTICE SET PUB = 1 WHERE ID IN (2,4,6,8,10); ������ ����ǰ� UPDATE NOTICE SET PUB = 0 WHERE ID IN (1,3,5,7,9); �� ������ �߻��ߴٸ�?
 *  �̰��� ��������(���������) �������̴�. -> ���ʸ� ����Ǿ��� ������ ���� �տ��� ����Ǿ��� ������ ������� �ǵ����� �ؾ߸� �Ѵ�. -> �̰��� Ʈ����� ó���� �ǹ�
 *   
 *  Ʈ����� ó����?
 *  ACID �� �����ϴ� ���̴�. 
 *  A:Automaticity(���ڼ�=���ڴ� ������ �ʴ´�.(�ϳ�ó�� �����ϵ���))
 *  C:consistency(�ϰ���=�������� ������ �߻������ʵ��� �ϰ����� ����(���������� ������ �����µ� �ش� ������ �����Ҷ� ������ ����� �ϰ����� ������ ��))
 *  I:Isolation(����=���� �����忡 ���� �����Ͱ� ���� �� �ִ�.(���ʿ����� �����ϰų� ���ʿ��� ��ȸ�Ҷ�) �׷��� �۾��� ó���ϱ� ���� �ٸ������� �������� ���ϵ��� �ϴ� �ɼ�)
 *  D:Durability(���Ӽ�=�����͸� �������� �� Ʈ������� �������� DB�� ����Ǿ�� �Ҷ� )
 */