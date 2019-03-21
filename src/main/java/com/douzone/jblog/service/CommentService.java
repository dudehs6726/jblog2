package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.CommentDao;
import com.douzone.jblog.vo.CommentVo;

@Service
public class CommentService {

	@Autowired
	private CommentDao commentDao;
	
	public void insert(CommentVo vo) {
		commentDao.insert(vo);
	}
	
	public List<CommentVo> getList(long no) {
		return commentDao.getList(no);
	}
	
	public void delete(long no) {
		commentDao.delete(no);
	}
}
