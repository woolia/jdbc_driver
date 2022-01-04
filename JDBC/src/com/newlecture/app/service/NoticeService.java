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
	private String driver = "oracle.jdbc.driver.OracleDriver"; // driver �� ���� ����

	public List<Notice> getList(int page){
		// sql �� where ���� �־ DB���� ���ǿ� �´� �����͸� ������
		
		// ����¡ (�Ķ���ͷ� page ���� �޾ƿ�) 
		/*String sql = "SELECT * FROM ( "
				+ "    SELECT ROWNUM NUM , N.* FROM ( "
				+ "        SELECT * FROM NOTICE ORDER BY REGDATE DESC "
				+ "    ) N "
				+ ") "
				+ "WHERE NUM BETWEEN ? AND ?";
		*/
		// �������� �����ؼ� DB���� NOTICE_VIEW�� ���� ���
		String sql = "SELECT * FROM NOTICE_VIEW WHERE NUM BETWEEN ? AND ?"; // ����� ������ �ξ� ��������
		
		
		int start = 1 + (page - 1) * 10; // 1 -> 11 -> 21 -> 31 -> 41 -> == ���������� a + (n-1)d a:����(1) d : ����(10) : n : ����(page)
		int end = 10 * page; // 10 -> 20 -> 30 -> 40 -> == 10 * page
		List<Notice> list = new ArrayList<Notice>();
		
		Connection conn = null;
		// ����¡ �ϴ� ������ �ٲ���� ������ st �� PreparedStatment�� �ٲ��� �Ѵ�.
	    PreparedStatement st = null;
	    ResultSet rs = null;
		
		try {
		
			Class.forName(driver);
			conn = DriverManager.getConnection(url , uid , pwd);
			st = conn.prepareStatement(sql);
			// ����¡ ó��
			st.setInt(1, start);
			st.setInt(2, end);
			
			rs = st.executeQuery();
			
			while(rs.next()) {// ������ ���� ������ 
				String title = rs.getString("title");
				int id = rs.getInt("ID");
				String writer_id = rs.getString("WRITER_ID");
				String content = rs.getString("CONTENT");
				Date regdate = rs.getDate("REGDATE");
				int hit = rs.getInt("HIT"); 
				String files = rs.getString("FILES");
				
				// Notice Ŭ������ �� ����
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
	
	// ����ڷ� ���� �������� �˻������� ����
	public List<Notice> getList(int page , String field , String query){
		// field �� NoticeConsole�� searchField �� �ǰ� query �� NoticeConsole�� searchWord �� �ȴ�. 
		
		//String sql = "SELECT * FROM NOTICE_VIEW WHERE 'TITLE' LIKE '%A%' AND NUM BETWEEN ? AND ?";  �̷��� �ϵ��� �ɾƳ����� ��
		// ? �� ����ؼ� PreparedStatement�� �Ⱦ� �������� �ص� �ȵȴ�. 
		// ? �� TITLE �� ������ 
		// "SELECT * FROM NOTICE_VIEW WHERE TITLE LIKE ? AND NUM BETWEEN ? AND ?";
		// "SELECT * FROM NOTICE_VIEW WHERE 'TITLE' LIKE ? AND NUM BETWEEN ? AND ?";
		// TITLE�� ''�� �پ ������ ���ȴ�. �׷��� �������� ������� ���� �׷��� ��� ?? �׳� +�� �ְų� format �������� �־�� �Ѵ�.
		
		String sql = "SELECT * FROM NOTICE_VIEW WHERE " +field+ " LIKE ? AND NUM BETWEEN ? AND ?"; // �̷��� �ؾ���
		
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
			st.setString(1, "%" +query+ "%"); // "%query%" �� �Ǿ����
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
		// update �÷��� �������̸� , �� �÷��� ����������� 
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

	
	// ���� ���� ������(count) ==> Scalar(��Į��)  ���� ��Į�� �� : ���ϰ�(count , min ,max ��)
	public int getCount() {
		// select�� �ʿ���
		int count = 0;
		
		Connection conn = null;
	    Statement st = null;
	    ResultSet rs = null;
		
	    String sql = "SELECT COUNT(ID) COUNT FROM NOTICE";
	    // �÷����� ��Ī�� �༭ rs���� ���� �޵��� ����
	    
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

// DB ���� Connection , Statement , ResultSet ���� �޼��帶�� ���� �־�� �Ѵ� (�ϳ��� ���� �ҷ��� ��� x)
// �ٸ� DB ���̵� , ��й�ȣ ���� ������ ���ϰ� �ϱ� ���� ���������� �ξ ��
