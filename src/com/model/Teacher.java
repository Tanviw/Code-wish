package com.model;


import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

public class Teacher extends Account{

	private String id;
	private int age;
	private String sex;
	private String realName;
	private String universityId;
	private String resDirection;
	private String position;
	private String picSrc;
	private Timestamp requestTime;
	private int safetyProblem;
	private String safetyAnswer;
	private double balance;
	
	//
    private MultipartFile pic;
    private String hsage;
    private String ui;
	private String provinceId;
	
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getUi() {
		return ui;
	}
	public void setUi(String ui) {
		this.ui = ui;
	}
	public String getHsage() {
		return hsage;
	}
	public void setHsage(String hsage) {
		this.hsage = hsage;
	}
	public MultipartFile getPic() {
		return pic;
	}
	public void setPic(MultipartFile pic) {
		this.pic = pic;
	}
	@Override
	public String toString() {
		return "Teacher [id=" + id + ", age=" + age + ", sex=" + sex + ", realName=" + realName + ", universityId="
				+ universityId + ", resDirection=" + resDirection + ", position=" + position + ", picSrc=" + picSrc
				+ ", requestTime=" + requestTime + ", safetyProblem=" + safetyProblem + ", safetyAnswer=" + safetyAnswer
				+ ", balance=" + balance + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getUniversityId() {
		return universityId;
	}
	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}
	public String getResDirection() {
		return resDirection;
	}
	public void setResDirection(String resDirection) {
		this.resDirection = resDirection;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPicSrc() {
		return picSrc;
	}
	public void setPicSrc(String picSrc) {
		this.picSrc = picSrc;
	}
	public Timestamp getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}
	public int getSafetyProblem() {
		return safetyProblem;
	}
	public void setSafetyProblem(int safetyProblem) {
		this.safetyProblem = safetyProblem;
	}
	public String getSafetyAnswer() {
		return safetyAnswer;
	}
	public void setSafetyAnswer(String safetyAnswer) {
		this.safetyAnswer = safetyAnswer;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
}
