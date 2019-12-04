package com.phion.tmall.service;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.phion.tmall.Application;
import com.phion.tmall.pojo.Product;
import com.phion.tmall.pojo.PropertyValue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class PropertyValueTest {

	@Autowired PropertyValueService propertyValueService;
	@Autowired ProductService productService;
	
	//@Test
	public void get() {
		/*int cid = 13;
		List<Product> products = productService.list(cid, 0, 5).getContent();
		System.out.println(Arrays.toString(products.toArray()));
		Product product = products.get(0);*/
		List<PropertyValue> values = propertyValueService.listAll();
		System.out.println(values.size());
		Iterator<PropertyValue> iterator = values.iterator();
		while(iterator.hasNext()) {
			PropertyValue p = iterator.next();
			System.out.println(p.getProduct());
			System.out.println(p.getProperty());
			System.out.println(p.getValue());
		}
	}
	
	
	@Test
	public void testInit() {
		//测试初始化属性值
		//找到所有产品
		List<Product> products = productService.list();
		
		//初始化每一个产品的属性值
		Iterator<Product> iterator = products.iterator();
		while(iterator.hasNext()) {
			Product product = iterator.next();
			propertyValueService.init(product.getId());
		}
		//查看数据库
		get();
	}
}
