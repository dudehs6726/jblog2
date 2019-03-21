package com.douzone.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.CommentService;
import com.douzone.jblog.service.FileuploadService;
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.CommentVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;
import com.douzone.security.AuthUser;

@RequestMapping("/{id:(?!assets).*}")
@Controller
public class BlogController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categotyService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private FileuploadService fileuploadService;

	@RequestMapping({"","/{pathNo1}","/{pathNo1}/{pathNo2}" })
	public String blogMain(
			@ModelAttribute BlogVo blogVo,
			@AuthUser UserVo authUser,
			@PathVariable("id") String id,
			@PathVariable("pathNo1") Optional<Long> pathNo1,
			@PathVariable("pathNo2") Optional<Long> pathNo2,
			Model model) {
		
		Long category = null;
		Long post = null;
		
		if(pathNo1.isPresent()) {
			category = pathNo1.get();
		}
		
		if(pathNo2.isPresent()) {
			post = pathNo2.get();
		}
				
		UserVo userVo = userService.getNo(id);
		
		if(userVo == null) {
			return "redirect:/";
		}
		
		//블로그 정보 호출
		blogVo = blogService.getBlog(userVo.getNo());
		
		//카테고리 리스트 호출
		List<CategoryVo>categoryList =  categotyService.getCategory(userVo.getNo());

		if(category == null) {
			CategoryVo cateVo = categoryList.get(0);
			category = cateVo.getNo();
		}
		
		//포스트 리스트 호출
		List<PostVo>postList = postService.getList(category);
		PostVo postVo = null;
		
		if(post == null && postList.size() > 0) {
			postVo = postList.get(0);
			post = postVo.getNo();
		}

		
		if(postList.size() > 0) {
			postVo = postService.get(post);
			//댓굴 호출
			List<CommentVo>commentList = commentService.getList(post);
			model.addAttribute("commentList", commentList);
		}
		
		model.addAttribute("id", id);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("postList", postList);
		model.addAttribute("postVo", postVo);
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("authUser", authUser);
		
		//}
		return "blog/blog-main";
	}

	@RequestMapping(value="/admin/basic", method=RequestMethod.GET)
	public String basic(
			@PathVariable("id") String id,
			@ModelAttribute BlogVo blogVo,
			@AuthUser UserVo authUser,
			Model model) {
		
		if(authUser == null || !authUser.getId().equals(id)) {
			return "redirect:/";
		}
		
		blogVo = blogService.getBlog(authUser.getNo());
		model.addAttribute("blogVo", blogVo);
		
		return "blog/blog-admin-basic";
	}
	
	@RequestMapping(value="/admin/basic", method=RequestMethod.POST)
	public String basic(
				@PathVariable("id") String id,
				@ModelAttribute BlogVo blogVo,
				@AuthUser UserVo authUser,
				@RequestParam(value="logo-file") MultipartFile multipartFile) {
		
		String logo = fileuploadService.restore(multipartFile);
		blogVo.setLogo(logo);
		blogVo.setUserNo(authUser.getNo());
		blogService.update(blogVo);
		
		return "redirect:/" + authUser.getId();
	}
	
	
}
