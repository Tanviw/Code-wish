package com.model;

public class Account {

	private String id;
	private String password;
	private int duty;
	private int status;
	
	//
	private String from;
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getDuty() {
		return duty;
	}
	public void setDuty(int duty) {
		this.duty = duty;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Account [id=" + id + ", password=" + password + ", duty=" + duty + ", status=" + status + "]";
	}
	
	
}
