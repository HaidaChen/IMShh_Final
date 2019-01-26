package com.douniu.imshh.utils.test;

public class Student {
	private String name;
	private String subject;
	private float score;
	
	public Student(String name, String subject, float score) {
		super();
		this.name = name;
		this.subject = subject;
		this.score = score;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
}
