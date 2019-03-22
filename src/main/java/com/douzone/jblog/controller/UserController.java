package com.douzone.jblog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;


@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value="join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";
	}
	
	@RequestMapping(value="join", method=RequestMethod.POST)
	public String join(
		@ModelAttribute @Valid UserVo userVo, 
		BindingResult result, 
		Model model) {
		
		if(result.hasErrors()) {
			
			model.addAllAttributes(result.getModel());//map으로 만들어 보냄
			return "user/join";
		}
		
		//유저 추가
		long no = userService.join(userVo);
		
		BlogVo blogVo = new BlogVo();
		blogVo.setUserNo(no);
		//블로그 추가
		if(blogService.insert(blogVo)) {
			//카테고리 추가
			CategoryVo categoryVo = new CategoryVo();
			categoryVo.setUserNo(no);
			categoryService.insert(categoryVo);
		}
		
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}

}
