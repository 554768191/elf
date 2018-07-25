package com.su.sso.entity;

import java.util.List;

public class SsoUser {

	private int id;
	private String account;
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
