package com.model;

public class Major {

	private String majorId;
	private String majorName;
	private String majorDiscription;
	private String coreCourses;
	private String employment;
	private int rank;
	
	public Major(){
		
	}
	public Major(String majorId, String majorName) {
		super();
		this.majorId = majorId;
		this.majorName = majorName;
	}
	public String getMajorId() {
		return majorId;
	}
	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}
	public String getMajorName() {
		return majorName;
	}
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	public String getMajorDiscription() {
		return majorDiscription;
	}
	public void setMajorDiscription(String majorDiscription) {
		this.majorDiscription = majorDiscription;
	}
	public String getCoreCourses() {
		return coreCourses;
	}
	public void setCoreCourses(String coreCourses) {
		this.coreCourses = coreCourses;
	}
	public String getEmployment() {
		return employment;
	}
	public void setEmployment(String employment) {
		this.employment = employment;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	
}
