package com.he.data.first.weather;

import java.io.Serializable;

public class CityBean implements Serializable{

	private static final long serialVersionUID = 2005295701925847160L;

	public Integer id;

	public String name;
	/* 城市拼音首字母 */
	public char pinyin;

	public CityBean(Integer id, String name, char pinyin) {
		this.id = id;
		this.name = name;
		this.pinyin = pinyin;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getPinyin() {
		return pinyin;
	}

	public void setPinyin(char pinyin) {
		this.pinyin = pinyin;
	}

}
