package com.lms.model;

public class Members {
	private int member_Id;
	private String member_Name;
	private String email;
	private int mobile_No;
	private char gender;
	
	
	
	public Members(String member_Name, String email, int mobile_No, char gender) {
		this.member_Name = member_Name;
		this.email = email;
		this.mobile_No = mobile_No;
		this.gender = gender;
	}

	public Members(int member_Id, String member_Name, String email, int mobile_No, char gender) {
		this.member_Id = member_Id;
		this.member_Name = member_Name;
		this.email = email;
		this.mobile_No = mobile_No;
		this.gender = gender;
	}
	
	public String getMember_Name() {
		return member_Name;
	}
	public void setMember_Name(String member_Name) {
		this.member_Name = member_Name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getMobile_No() {
		return mobile_No;
	}
	public void setMobile_No(int mobile_No) {
		this.mobile_No = mobile_No;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public int getMember_Id() {
		return member_Id;
	}
}
