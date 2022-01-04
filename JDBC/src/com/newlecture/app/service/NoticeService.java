package com.newlecture.app.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newlecture.app.entity.Notice;

public class NoticeService {
	
	private String url = "jdbc:oracle:thin:@localhost:1521/system";
	private String uid = "c##zecrar";
	private String pwd = "1234";
	private String driver = "oracle.jdbc.driver.OracleDriver"; // driver 도 따로 관리

	public List<Notice> getList(int page){
		// sql 에 where 절을 넣어서 DB에서 조건에 맞는 데이터만 가져옴
		
		// 페이징 (파라미터로 page 값을 받아옴) 
		/*String sql = "SELECT * FROM ( "
				+ "    SELECT ROWNUM NUM , N.* FROM ( "
				+ "        SELECT * FROM NOTICE ORDER BY REGDATE DESC "
				+ "    ) N "
				+ ") "
				+ "WHERE NUM BETWEEN ? AND ?";
		*/
		// 쿼리문이 복잡해서 DB에서 NOTICE_VIEW를 만들어서 사용
		String sql = "SELECT * FROM NOTICE_VIEW WHERE NUM BETWEEN ? AND ?"; // 결과는 같지만 훨씬 간결해짐
		
		
		int start = 1 + (page - 1) * 10; // 1 -> 11 -> 21 -> 31 -> 41 -> == 등차수열임 a + (n-1)d a:초항(1) d : 등차(10) : n : 차수(page)
		int end = 10 * page; // 10 -> 20 -> 30 -> 40 -> == 10 * page
		List<Notice> list = new ArrayList<Notice>();
		
		Connection conn = null;
		// 페이징 하는 쿼리로 바뀌었기 때문에 st 도 PreparedStatment로 바뀌어야 한다.
	    PreparedStatement st = null;
	    ResultSet rs = null;
		
		try {
		
			Class.forName(driver);
			conn = DriverManager.getConnection(url , uid , pwd);
			st = conn.prepareStatement(sql);
			// 페이징 처리
			st.setInt(1, start);
			st.setInt(2, end);
			
			rs = st.executeQuery();
			
			while(rs.next()) {// 가져온 것이 있으면 
				String title = rs.getString("title");
				int id = rs.getInt("ID");
				String writer_id = rs.getString("WRITER_ID");
				String content = rs.getString("CONTENT");
				Date regdate = rs.getDate("REGDATE");
				int hit = rs.getInt("HIT"); 
				String files = rs.getString("FILES");
				
				// Notice 클래스에 값 저장
				Notice notice = new Notice(id,title,writer_id,regdate,hit,content,files);
				list.add(notice);
				//System.out.println("title : "+ title + " id : " + id + " writer_id : " + writer_id + " content : " + content + " regdate : " + regdate + " hit : " + hit + " files : " + files);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		finally {
	        if (rs != null) try { rs.close(); } catch(Exception e) {}
	        if (st != null) try { st.close(); } catch(Exception e) {}
	        if (conn != null) try { conn.close(); } catch(Exception e) {}
			
		}
		return list;
	}
	
	// 사용자로 부터 페이지와 검색정보를 얻음
	public List<Notice> getList(int page , String field , String query){
		// field 는 NoticeConsole의 searchField 가 되고 query 는 NoticeConsole의 searchWord 가 된다. 
		
		//String sql = "SELECT * FROM NOTICE_VIEW WHERE 'TITLE' LIKE '%A%' AND NUM BETWEEN ? AND ?";  이렇게 하도록 꽃아넣으면 됨
		// ? 을 사용해서 PreparedStatement로 꽂아 넣으려고 해도 안된다. 
		// ? 로 TITLE 을 꽃으면 
		// "SELECT * FROM NOTICE_VIEW WHERE TITLE LIKE ? AND NUM BETWEEN ? AND ?";
		// "SELECT * FROM NOTICE_VIEW WHERE 'TITLE' LIKE ? AND NUM BETWEEN ? AND ?";
		// TITLE에 ''가 붙어서 값으로 사용된다. 그러면 쿼리문이 실행되지 않음 그러면 어떻게 ?? 그냥 +로 넣거나 format 형식으로 넣어야 한다.
		
		String sql = "SELECT * FROM NOTICE_VIEW WHERE " +field+ " LIKE ? AND NUM BETWEEN ? AND ?"; // 이렇게 해야함
		
		int start = 1 + (page - 1) * 10; 
		int end = 10 * page; 
		List<Notice> list = new ArrayList<Notice>();
		
		Connection conn = null;
	    PreparedStatement st = null;
	    ResultSet rs = null;
		
		try {
		
			Class.forName(driver);
			conn = DriverManager.getConnection(url , uid , pwd);
			st = conn.prepareStatement(sql);
			st.setString(1, "%" +query+ "%"); // "%query%" 가 되어야함
 			st.setInt(2, start);
			st.setInt(3, end);
			
			rs = st.executeQuery();
			
			while(rs.next()) {
				String title = rs.getString("title");
				int id = rs.getInt("ID");
				String writer_id = rs.getString("WRITER_ID");
				String content = rs.getString("CONTENT");
				Date regdate = rs.getDate("REGDATE");
				int hit = rs.getInt("HIT"); 
				String files = rs.getString("FILES");
				

				Notice notice = new Notice(id,title,writer_id,regdate,hit,content,files);
				list.add(notice);
				
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		finally {
	        if (rs != null) try { rs.close(); } catch(Exception e) {}
	        if (st != null) try { st.close(); } catch(Exception e) {}
	        if (conn != null) try { conn.close(); } catch(Exception e) {}
			
		}
		return list;
	}
	
	public int insert(Notice notice) {
		String sql = "INSERT INTO NOTICE(TITLE , WRITER_ID , CONTENT , FILES) VALUES (?,?,?,?)";
		
		String title = notice.getTitle();
		String writer_id = notice.getWriter_id();
		String content = notice.getContent();
		String files = notice.getFiles();
		
		int result = 0;
		Connection conn = null;
		PreparedStatement st = null;
		
		try{
			Class.forName(driver);
			
			conn = DriverManager.getConnection(url , uid , pwd);
			st = conn.prepareStatement(sql);
			st.setString(1, title);
			st.setString(2, writer_id);
			st.setString(3, content);
			st.setString(4, files);
			
			result = st.executeUpdate();
			
			System.out.println(result);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		finally {
	        if (st != null) try { st.close(); } catch(Exception e) {}
	        if (conn != null) try {conn.close();} catch (Exception e2) {}
		}
		
		
		return result;
	}
	
	public int update(Notice notice) {
		String title = notice.getTitle();
		String content = notice.getContent();
		String files = notice.getFiles();
		int id = notice.getId();
		
		// UPDATE 
		int result = 0;
		Connection conn = null;
		PreparedStatement st = null;
		
		String sql = "UPDATE NOTICE SET TITLE=?, CONTENT=?, FILES=? WHERE ID=?";
		// update 컬럼이 여러개이면 , 로 컬럼을 구분해줘야함 
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url , uid , pwd);
			st = conn.prepareStatement(sql);
	
			st.setString(1, title);
			st.setString(2, content);
			st.setString(3, files);
			st.setInt(4, id);
			
			result = st.executeUpdate();
	
			System.out.println(result);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			if(st != null) try {st.close();} catch(Exception e) {}
			if(conn != null) try{conn.close();} catch (Exception e) {}
		}
		
		return result;
	}
	
	public int delete(Notice notice) {
		
		int id = notice.getId();
		
		// delete
		
		String sql = "DELETE NOTICE WHERE ID = ?";
		
		int result = 0;
		Connection conn = null;
		PreparedStatement st = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url , uid , pwd);
			st = conn.prepareStatement(sql);
	
			st.setInt(1, id);
			
			result = st.executeUpdate();
	
			System.out.println(result);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			if(st != null) try {st.close();} catch(Exception e) {}
			if(conn != null) try {conn.close();} catch(Exception e) {}
		}
		
		return result;
	}

	
	// 단일 값을 가져옴(count) ==> Scalar(스칼라)  따라서 스칼라 값 : 단일값(count , min ,max 등)
	public int getCount() {
		// select가 필요함
		int count = 0;
		
		Connection conn = null;
	    Statement st = null;
	    ResultSet rs = null;
		
	    String sql = "SELECT COUNT(ID) COUNT FROM NOTICE";
	    // 컬럼명을 별칭을 줘서 rs에서 쉽게 받도록 설정
	    
		try {
		
			Class.forName(driver);
			conn = DriverManager.getConnection(url , uid , pwd);
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				count = rs.getInt("COUNT");
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		finally {
	        if (rs != null) try { rs.close(); } catch(Exception e) {}
	        if (st != null) try { st.close(); } catch(Exception e) {}
	        if (conn != null) try { conn.close(); } catch(Exception e) {}
			
		}
		
		return count;
	}
}

// DB 연결 Connection , Statement , ResultSet 들은 메서드마다 따로 있어야 한다 (하나로 만들어서 불러서 사용 x)
// 다만 DB 아이디 , 비밀번호 들은 관리를 편하게 하기 위해 전역변수로 두어도 됨
