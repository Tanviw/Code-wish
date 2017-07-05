package com.model;


import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;


public class CollegeStu extends Account{

	private String id;
	private int age;
	private String sex;
	private String realName;
	private String universityId;
	private String majorId;
	private int sYear;
	private String picSrc;
	private Timestamp requestTime;
	private int safetyProblem;
	private String safetyAnswer;
	private double balance;
	
	//
	private String hsage;
	private MultipartFile pic;
	private String syear;
	private String provinceId;
	
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getSyear() {
		return syear;
	}
	public void setSyear(String syear) {
		this.syear = syear;
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
	public String getMajorId() {
		return majorId;
	}
	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}
	public int getsYear() {
		return sYear;
	}
	public void setsYear(int sYear) {
		this.sYear = sYear;
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
	@Override
	public String toString() {
		return "CollegeStu [id=" + id + ", age=" + age + ", sex=" + sex + ", realName=" + realName + ", universityId="
				+ universityId + ", majorId=" + majorId + ", sYear=" + sYear + ", picSrc=" + picSrc + ", requestTime="
				+ requestTime + ", safetyProblem=" + safetyProblem + ", safetyAnswer=" + safetyAnswer + ", balance="
				+ balance + "]";
	}
	
	
}
