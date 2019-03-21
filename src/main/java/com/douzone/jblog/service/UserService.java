package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.UserDao;
import com.douzone.jblog.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public boolean existId(String id) {
		UserVo userVo = userDao.get(id);
		return userVo != null;
	}
	
	public UserVo getNo(String id) {
		
		UserVo userVo = userDao.get(id);
		
		return userVo;
	}
	
	public long join(UserVo userVo) {
		// 1. DB에 가입 회원 정보 insert 하기
		return userDao.insert(userVo);
		
	}
	
	public UserVo login(UserVo userVo) {
		
		UserVo authUser = userDao.get(userVo);
		
		return authUser;
	}

}
