package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVo;

@Repository
public class CategoryDao {

	@Autowired
	private SqlSession sqlSession;
	
	public void insert(CategoryVo vo) {
		
		sqlSession.insert("category.insert", vo);
		
	}
	
	public long insertMain(CategoryVo vo) {
		
		sqlSession.insert("category.insertMain", vo);
		return vo.getNo();
	}
	
	public boolean delete(CategoryVo vo) {
		int vInt = 0;
		vInt = sqlSession.delete("category.delete", vo);
		
		return vInt > 0;
	}
	
	public List<CategoryVo> getList(long userNo) {
		return sqlSession.selectList("category.getByUserNo", userNo);
	}
	
	public CategoryVo get(long valNo) {
		
		return sqlSession.selectOne("category.getByLong", valNo);
	}
}
