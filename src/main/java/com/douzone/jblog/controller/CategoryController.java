package com.douzone.jblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.dto.JSONResult;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;
import com.douzone.security.AuthUser;

@Controller
@RequestMapping("/{id}/admin/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private BlogService blogService;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String category(
			@PathVariable("id") String id,
			@ModelAttribute CategoryVo categoryVo,
			@AuthUser UserVo authUser,
			Model model) {
		
		if(authUser == null || !authUser.getId().equals(id)) {
			return "redirect:/";
		}
		
		BlogVo blogVo = blogService.getBlog(authUser.getNo());
		model.addAttribute("blogVo", blogVo);
		
		return "blog/blog-admin-category";
	}
	
	@ResponseBody
	@RequestMapping("/ajaxList")
	public JSONResult getList(@RequestParam(value="p", required=true, defaultValue="") String sPage,
			 				@AuthUser UserVo authUser) {
		
	
		if("".equals(sPage)) {
			sPage = "1";
		}
		
		if(sPage.matches("\\d*") == false) {
			sPage = "1";
		}
		
		int page = Integer.parseInt(sPage);
		List<CategoryVo> list = categoryService.getCategory(authUser.getNo());
		//List<CategoryVo>list = guestBookService.list(page);

		return JSONResult.success(list);
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajaxInsert", method=RequestMethod.POST)
	public JSONResult insert(@RequestParam(value="name", required=true, defaultValue="") String name,
			@RequestParam(value="description", required=true, defaultValue="") String description,
			@AuthUser UserVo authUser) {
		
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setName(name);
		categoryVo.setDescription(description);
		categoryVo.setUserNo(authUser.getNo());
		
		long no = categoryService.insertMain(categoryVo);
		
		CategoryVo newVo = categoryService.get(no);

		return JSONResult.success(newVo);
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajaxDelete", method=RequestMethod.POST)
	public JSONResult delete(@RequestParam(value="no", required=true, defaultValue="") String no) {
	
		CategoryVo vo = new CategoryVo();
		vo.setNo(Long.valueOf(no));
		
		if(categoryService.delete(vo))
		{
			return JSONResult.success(vo.getNo());
		}
		else {
			return JSONResult.fail("실패");
		}

	}

}
