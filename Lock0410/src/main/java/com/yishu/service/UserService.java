package com.yishu.service;



import java.util.List;

import com.yishu.model.NoteResult;
import com.yishu.model.User;


public interface UserService {

	public NoteResult checkLogin(String name, String password);
	public NoteResult checkLogin(String author);
}