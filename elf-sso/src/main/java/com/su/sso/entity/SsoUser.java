package com.su.sso.entity;

import java.util.List;

public class SsoUser {

	private int id;
	private String account;
	private String passWord;
	private int roleId;
	private int isSuper;
	private int readOnly;
	private List<String> privaleges;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getIsSuper() {
		return isSuper;
	}

	public void setIsSuper(int isSuper) {
		this.isSuper = isSuper;
	}

	public int getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(int readOnly) {
		this.readOnly = readOnly;
	}

	public List<String> getPrivaleges() {
		return privaleges;
	}

	public void setPrivaleges(List<String> privaleges) {
		this.privaleges = privaleges;
	}
}
