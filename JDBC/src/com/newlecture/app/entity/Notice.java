package com.newlecture.app.entity;

import java.util.Date;

public class Notice {

	private int id;
	private String title;
	private String writer_id;
	private Date regdate;
	private int hit;
	private String content;
	private String files;
	
	public Notice() {
		// 오버로드 될경우에는 중복처리
	}
	
	public Notice(int id, String title, String writer_id, Date regdate, int hit, String content , String files) {
		this.id = id;
		this.title = title;
		this.writer_id = writer_id;
		this.regdate = regdate;
		this.hit = hit;
		this.content = content;
		this.files = files;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter_id() {
		return writer_id;
	}
	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}

}
