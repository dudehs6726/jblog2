package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.PostDao;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.PostVo;

@Service
public class PostService {
	
	@Autowired
	private PostDao postDao;
	
	public void insert(PostVo vo) {
		// 1. DB에 가입 회원 정보 insert 하기
		postDao.insert(vo);
		
	}
	
	public List<PostVo> getList(long no) {
		// 1. DB에 가입 회원 정보 insert 하기
		return postDao.getList(no);
		
	}
	
	public PostVo get(long no) {
		// 1. DB에 가입 회원 정보 insert 하기
		return postDao.get(no);
		
	}


}
