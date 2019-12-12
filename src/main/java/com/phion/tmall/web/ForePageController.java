package com.phion.tmall.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用于负责前端的页面之前的跳转
 * @author 15037
 *
 */
@Controller
public class ForePageController {

	/**
	 * 访问主页
	 */
	@GetMapping("")
	public String fore() {
		return "redirect:home";
	}
	
	@GetMapping("home")
	public String home() {
		return "fore/home";
	}
	
}
