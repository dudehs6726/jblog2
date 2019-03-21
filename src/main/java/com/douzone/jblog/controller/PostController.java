package com.douzone.jblog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;
import com.douzone.security.AuthUser;

@Controller
public class PostController {
	
	@Autowired
	PostService postService;
	
	@Autowired
	BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value="/{id}/admin/write", method=RequestMethod.GET)
	public String write(
				@PathVariable("id") String id,
				@ModelAttribute PostVo postVo,
				@AuthUser UserVo authUser, Model model) {
		
		if(authUser == null || !authUser.getId().equals(id)) {
			return "redirect:/";
		}
		
		List<CategoryVo> list =  categoryService.getCategory(authUser.getNo());
		model.addAttribute("list", list);
		
		BlogVo blogVo = blogService.getBlog(authUser.getNo());
		model.addAttribute("blogVo", blogVo);
		
		return "blog/blog-admin-write";
	}
	
	@RequestMapping(value="/{id}/admin/write", method=RequestMethod.POST)
	public String write(
			@PathVariable("id") String id,
			@AuthUser UserVo authUser,
			@ModelAttribute @Valid PostVo postVo,
			BindingResult result,
			Model model) {
		
		if(result.hasErrors()) {
			
			model.addAllAttributes(result.getModel());//map으로 만들어 보냄
			
			List<CategoryVo> list =  categoryService.getCategory(authUser.getNo());
			model.addAttribute("list", list);
			
			return "blog/blog-admin-write";
		}
		
		postService.insert(postVo);
		
		return "redirect:/" + id;
	}
}
