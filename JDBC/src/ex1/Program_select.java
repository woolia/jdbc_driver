package ex1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Program_select {

	// 1. JDBC Driver 로드 : Class.forName("oracle.jdbc.driver.OracleDriver");
	// 2. Connection 연결 : Connection con = DriverManager.getConnection(...);
	// 3. Statement 실행도구 : Statement st = con.createStatement();
	// 4. ResultSet 결과를 가져옴 : ResultSet rs = st.executeQuery(sql);
	// 5. rs.next(); 패치로 값을 가져옴
	// 6. 변수화 : String title = rs.getString("title");
	
	
	/*
	 * 수업용 테이블
	 * 
	 * 
	 * 
 // 에러 발생 FILES VARCHAR(5000) : ORA-00910: 데이터형에 지정된 길이가 너무 깁니다 00910. 00000 -  "specified length too long for its datatype" , REGDATE DATETIME : ORA-00902: 데이터유형이 부적합합니다 00902. 00000 -  "invalid datatype"
 * COMMENT 테이블 생성 ORA-00903: 테이블명이 부적합합니다 00903. 00000 -  "invalid table name"
 *  FILES VARCHAR(5000) -> FILES VARCHAR(4000) 으로 변경 https://okky.kr/article/26396 해설 
 *  REGDATE DATETIME -> DATETIME 을 DATE 로 변경
 *  COMMENT -> COMMENTS 로 변경


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
여기서 SYSDATE는 현재시간을 의미 컬럼의 데이터형이 DATE or TIMESTAMP 인 경우만 가능하다.

commit;
오라클에서는 DB에 반영할때는 반드시 수동으로 commit 을 해줘야 DB에 반영됨 일종의 확인도장 같은 경우 

	 * */
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		/*
		String url = "jdbc:oracle:thin:@localhost:1521/system"; // 나의 경우 SID 가 system 이라서 넣음
		String sql = "SELECT * FROM NOTICE";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url , "c##zecrar","1234");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		*/
		
		/*
		while(rs.next()) {// 가져온 것이 있으면 
			String title = rs.getString("title");
			int id = rs.getInt("ID");
			String writer_id = rs.getString("WRITER_ID");
			String content = rs.getString("CONTENT");
			Date regdate = rs.getDate("REGDATE");
			// Date 타입은 java.util.date 와 java.sql.date 가 있는데 java.util.date를 해야함
			int hit = rs.getInt("id");
			String files = rs.getString("FILES");
			
			System.out.println("title : "+ title + " id : " + id + " writer_id : " + writer_id + " content : " + content + " regdate : " + regdate + " hit : " + hit + " files : " + files);
		}
		*/
		// 조회수가 10 이상인 경우
		
		/*
		while(rs.next()) {// 가져온 것이 있으면 
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
		
		// 이렇게 하면 안된다.
		// 그러면 어떻게 해야하느냐 -> sql 에서 where절을 사용해서 hit가 10 이상인 경우만 가져오도록 함
		// 왜 이렇게 하는지?? -> 만약에 데이터가 10000개 100000개  있다고 하면 해당 코드는 10000,100000개의 모든 데이터를 가져와서 조건문을 체크하여 메모리 및 시간을 많이 소비하게 된다
		// 그런데 sql 에서 where 절을 통해 조건에 해당하는 데이터만 가져오면 시간낭비를 줄일 수 있음
		
		// ---- 해결방법 ----
		
		String url = "jdbc:oracle:thin:@localhost:1521/orclpdb";
		// sql 에 where 절을 넣어서 DB에서 조건에 맞는 데이터만 가져옴 
		String sql = "SELECT * FROM NOTICE WHERE HIT >= 10";
		
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url , "c##zecrar","1234");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next()) {// 가져온 것이 있으면 
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
 *  ※ 트랜잭션 처리
 *  - 하나의 단위로 수행되길 바라는 쿼리의 묶음을 의미 (= 업무 수행단위, 논리적인 수행단위)
 *  EX)
 *  UPDATE NOTICE SET PUB = 1 WHERE ID IN (2,4,6,8,10);
 *  UPDATE NOTICE SET PUB = 0 WHERE ID IN (1,3,5,7,9);
 *  이 두가지 SQL을 하나로 처리하고 싶은것
 *  만약에 UPDATE NOTICE SET PUB = 1 WHERE ID IN (2,4,6,8,10); 로직은 실행되고 UPDATE NOTICE SET PUB = 0 WHERE ID IN (1,3,5,7,9); 가 에러가 발생했다면?
 *  이것은 업무단위(수행단위가) 깨진것이다. -> 반쪽만 실행되었기 때문에 따라서 앞에서 실행되었던 동작은 원래대로 되돌려야 해야만 한다. -> 이것이 트랜잭션 처리를 의미
 *   
 *  트랜잭션 처리란?
 *  ACID 를 유지하는 것이다. 
 *  A:Automaticity(원자성=원자는 깨지지 않는다.(하나처럼 동작하도록))
 *  C:consistency(일관성=데이터의 결함이 발생하지않도록 일관성을 유지(수행전에는 결함이 없었는데 해당 로직을 실행할때 결함이 생기면 일관성이 깨지는 것))
 *  I:Isolation(고립성=여러 쓰레드에 의해 데이터가 사용될 수 있다.(한쪽에서는 조작하거나 한쪽에서 조회할때) 그럴때 작업을 처리하기 전에 다른곳에서 접근하지 못하도록 하는 옵션)
 *  D:Durability(지속성=데이터를 변경했을 때 트랜잭션이 끝났을때 DB에 적용되어야 할때 )
 */