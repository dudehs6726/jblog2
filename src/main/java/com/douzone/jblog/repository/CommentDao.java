package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CommentVo;

@Repository
public class CommentDao {

	@Autowired
	private SqlSession sqlSession;
	
	public void insert(CommentVo vo) {
		sqlSession.insert("comment.insert", vo);
	}
	
	public List<CommentVo> getList(long no) {
		return sqlSession.selectList("comment.getList", no);
	}
	
	public void delete(long no) {
		sqlSession.delete("comment.delete", no);
	}
}
