package com.model;

public class HighSchoolStu extends Account{

	//数据库对应的属性
	private String id;
	private int age;
	private String hId;
	private int safetyProblem;
	private String safetyAnswer;
	private String sex;
	private double balance;
	
	//自己添加的属性
	private String hsage;//用于表单接收
	private String provinceId;
	
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	@Override
	public String toString() {
		return "HighSchoolStu [id=" + id + ", age=" + age + ", hId=" + hId + ", safetyProblem=" + safetyProblem
				+ ", safetyAnswer=" + safetyAnswer + ", sex=" + sex + ", balance=" + balance + "]";
	}
	public String getId() {
		return id;
	}

	
	public String getHsage() {
		return hsage;
	}
	public void setHsage(String hsage) {
		this.hsage = hsage;
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
	public String gethId() {
		return hId;
	}
	public void sethId(String hId) {
		this.hId = hId;
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
}
