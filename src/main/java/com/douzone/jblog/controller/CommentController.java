package com.douzone.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.jblog.service.CommentService;
import com.douzone.jblog.vo.CommentVo;
import com.douzone.jblog.vo.UserVo;
import com.douzone.security.AuthUser;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@RequestMapping(value="/{id}/{category}/{post}/insert", method=RequestMethod.POST)
	public String insert(
			@PathVariable("id") String id,
			@PathVariable("category") Long category,
			@PathVariable("post") Long post,
			@ModelAttribute CommentVo commentVo,
			BindingResult result,
			Model model) {
		
		commentVo.setPostNo(post);
		commentService.insert(commentVo);
		
		return "redirect:/" + id + "/" + category + "/" + post;
	}
	
	@RequestMapping(value="/{id}/{category}/{post}/delete/{no}", method=RequestMethod.GET)
	public String delete(
			@PathVariable("id") String id,
			@PathVariable("category") Long category,
			@PathVariable("post") Long post,
			@PathVariable("no") Long no,
			@ModelAttribute CommentVo commentVo,
			@AuthUser UserVo authUser,
			BindingResult result,
			Model model) {
		
		if(authUser == null || !authUser.getId().equals(id)) {
			return "redirect:/";
		}
		
		commentService.delete(no);
		
		return "redirect:/" + id + "/" + category + "/" + post;
	}
}
