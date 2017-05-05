package com.yishu.service;


import com.yishu.model.NoteResult;


public interface UserService {

	public NoteResult checkLogin(String name, String password);
	public NoteResult checkLogin(String author);
}