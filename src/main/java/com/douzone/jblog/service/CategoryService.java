package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.CategoryDao;
import com.douzone.jblog.vo.CategoryVo;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryDao categoryDao;
	
	public void insert(CategoryVo vo) {
		// 1. DB에 가입 회원 정보 insert 하기
		
		vo.setName("미분류");
		vo.setDescription("카테고리를 지정하지 않은 경우");
		 categoryDao.insert(vo);
	}
	
	public long insertMain(CategoryVo vo) {
		
		 return categoryDao.insertMain(vo);
	}
	
	public List<CategoryVo> getCategory(long userNo) {
		
		return categoryDao.getList(userNo);
	}
	
	public CategoryVo get(long no){
		
		return categoryDao.get(no);
	}
	
	public boolean delete(CategoryVo categoryVo) {
		
		return categoryDao.delete(categoryVo);
	
	}
}
