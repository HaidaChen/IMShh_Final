package com.douniu.imshh.sys.domain;

public class Dictionary {
	private String name;
	private String key;
	private String text;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return "Dictionary [name=" + name + ", key=" + key + ", text=" + text + "]";
	}	
}
