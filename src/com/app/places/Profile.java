package com.app.places;

import java.util.ArrayList;

public class Profile {
	private String login;
	private String password;
	private int age;
	private String sex;
	

	private ArrayList<Tags> tag;
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public ArrayList<Tags> getTag() {
		return tag;
	}
	
	public void setTag(ArrayList<Tags> tag) {
		this.tag = tag;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = (sex).substring(1);
	}
}
