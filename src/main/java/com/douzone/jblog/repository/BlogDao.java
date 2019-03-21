package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;

@Repository
public class BlogDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public boolean insert(BlogVo vo) {
		
		return (sqlSession.insert("blog.insert", vo) > 0);
		
	}
	
	public void update(BlogVo vo) {
		
		sqlSession.update("blog.update", vo);
		
	}
	
	public BlogVo get(long userNo) {
		return sqlSession.selectOne("blog.getByUserNo", userNo);
	}

}
