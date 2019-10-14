package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.utils.PageUtils;

@Controller
public class GoodsController {
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@RequestMapping("list")
	public String list(@RequestParam(defaultValue="1")int cpage,Model model) {
		//获取出所有数据
		BoundListOperations<String,Object> boundListOps = redisTemplate.boundListOps("goodsList");
		//分页
		Map<String, Long> pageMap = PageUtils.pageUtils(boundListOps.size(), cpage);
		//分页后的所有数据
		List<Object> range = boundListOps.range(pageMap.get("start"), pageMap.get("end"));
		model.addAttribute("goodsList", range);
		model.addAttribute("cpage", pageMap.get("cpage"));
		model.addAttribute("pages", pageMap.get("pages"));
		return "list";
	}
}
