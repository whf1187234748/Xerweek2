package com.example.demo;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.bean.Goods;
import com.tzh.utils.IOToFileUtils;
import com.tzh.utils.StringUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WhfSenior2Week2ApplicationTests {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Test
	public void contextLoads() {
	}

	//解析文件，并用Redis之List类型应用
	@Test
	public void testList() {
		//获取文件信息
		List<String> readFileByLinesList = IOToFileUtils.readFileByLinesList(System.getProperty("user.dir")+"/src/test/java/test.txt");
		BoundListOperations<String,Object> boundListOps = redisTemplate.boundListOps("goodsList");
		//遍历文件信息
		for (String string : readFileByLinesList) {
			//切割
			String[] split = string.split("==");
			//创建对象
			Goods goods = new Goods();
			//判断id值是否是数字
			boolean nymber = StringUtils.isNymber(split[0]);
			//如果是数字则进行赋值
			if (nymber) {
				goods.setGid(Integer.parseInt(split[0]));
			}
			//判断商品名称是否为空，若不为空则返回true
			boolean null1 = StringUtils.isNull(split[1]);
			if (null1) {
				goods.setGname(split[1]);
			}
			//先判断价钱是否为空
			boolean null2 = StringUtils.isNull(split[2]);
			//如果不为空则赋值为空则赋值默认为0
			if (null2) {
				//切割
				String[] split2 = split[2].split("¥");
				BigDecimal gprice1 = new BigDecimal(split2[1]);
				goods.setGprice(gprice1);
				
			}else {
				//价钱为空赋值为0
				BigDecimal gprice2 = new BigDecimal(0);
				goods.setGprice(gprice2);
			}
			boolean null3 = StringUtils.isNull(split[3]);
			//如果不为空则赋值为空则赋值默认为0
			if (null3) {
				//切割
				String[] split3 = split[3].split("%");
				//如果为空则默认为0
				if (!StringUtils.isBlank(split3[0])) {
					goods.setBfb(0);
				}else {
					//不为空则赋值
					goods.setBfb(Integer.parseInt(split3[0]));
				}
			}
			System.out.println(goods);
			boundListOps.leftPush(goods);
		}
	}
	
	//解析文件并且Redis之ZSet类型应用
	@Test
	public void testZSet() {
		//获取文件信息
		List<String> readFileByLinesList = IOToFileUtils.readFileByLinesList(System.getProperty("user.dir")+"/src/test/java/test.txt");
		BoundZSetOperations<String, Object> boundZSetOps = redisTemplate.boundZSetOps("goodsZSet");
		//遍历文件信息
		for (String string : readFileByLinesList) {
			//切割
			String[] split = string.split("==");
			//创建对象
			Goods goods = new Goods();
			//判断id值是否是数字
			boolean nymber = StringUtils.isNymber(split[0]);
			//如果是数字则进行赋值
			if (nymber) {
				goods.setGid(Integer.parseInt(split[0]));
			}
			//判断商品名称是否为空，若不为空则返回true
			boolean null1 = StringUtils.isNull(split[1]);
			if (null1) {
				goods.setGname(split[1]);
			}
			//先判断价钱是否为空
			boolean null2 = StringUtils.isNull(split[2]);
			//如果不为空则赋值为空则赋值默认为0
			if (null2) {
				//切割
				String[] split2 = split[2].split("¥");
				BigDecimal gprice1 = new BigDecimal(split2[1]);
				goods.setGprice(gprice1);
				
			}else {
				//价钱为空赋值为0
				BigDecimal gprice2 = new BigDecimal(0);
				goods.setGprice(gprice2);
			}
			boolean null3 = StringUtils.isNull(split[3]);
			//如果不为空则赋值为空则赋值默认为0
			if (null3) {
				//切割
				String[] split3 = split[3].split("%");
				//如果为空则默认为0
				if (!StringUtils.isBlank(split3[0])) {
					goods.setBfb(0);
				}else {
					//不为空则赋值
					goods.setBfb(Integer.parseInt(split3[0]));
				}
			}
			System.out.println(goods);
			//zset需要用百分百来权重排序
			boundZSetOps.add(goods, goods.getBfb());
		}
	}
}
