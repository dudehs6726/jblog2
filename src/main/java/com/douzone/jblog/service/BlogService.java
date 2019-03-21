package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogDao;
import com.douzone.jblog.vo.BlogVo;

@Service
public class BlogService {
	private static final String IMAGE_PATH = "/uploads/jblog/images/";
	
	@Autowired
	private BlogDao blogDao;
	
	public boolean insert(BlogVo vo) {
		// 1. DB에 가입 회원 정보 insert 하기
		return blogDao.insert(vo);
		
	}
	
	public void update(BlogVo vo) {
		
		blogDao.update(vo);
		
	}
	
	public BlogVo getBlog(long userNo) {
		
		BlogVo blogVo = blogDao.get(userNo);
		
		blogVo.setLogo(IMAGE_PATH + blogVo.getLogo());
		
		return blogVo;
	}
}
