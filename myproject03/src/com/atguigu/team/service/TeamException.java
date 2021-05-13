package com.atguigu.team.service;

public class TeamException extends Exception {
	private static final long serialVersionUID = 526874140881L;
	
	public TeamException() {}
	
	public TeamException(String msg) {
		super(msg);
	}
}
