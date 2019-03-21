package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public UserVo get(String id) {
		
		UserVo userVo = sqlSession.selectOne("user.getById", id);
		
		return userVo;
	}
	
	public UserVo get(UserVo vo) {
		
		UserVo userVo = sqlSession.selectOne("user.getByEmailAndPassword", vo);
		
		return userVo;
	}
	
	public long insert(UserVo vo) {
		
		sqlSession.insert("user.insert", vo);
		
		long no = vo.getNo();
		return no;
	}

}
