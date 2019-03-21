package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.PostVo;

@Repository
public class PostDao {

	@Autowired
	private SqlSession sqlSession;
	
	public void insert(PostVo vo) {
		
		sqlSession.insert("post.insert", vo);
		
	}
	
	public List<PostVo> getList(long no) {
		
		return sqlSession.selectList("post.getList", no);
		
	}
	
	public PostVo get(long no) {
		return sqlSession.selectOne("post.getByNo", no);
	}
}
