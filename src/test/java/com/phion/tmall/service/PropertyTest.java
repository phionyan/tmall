package com.phion.tmall.service;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.phion.tmall.Application;
import com.phion.tmall.pojo.Category;
import com.phion.tmall.pojo.Property;
import com.phion.tmall.pojo.PropertyValue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class PropertyTest {

	@Autowired PropertyService propertyService;
	
	@Test
	public void list() {
		Property p =  propertyService.get(1);
		System.out.println(p);
	}
	
	//@Test
	/*public void get() {
		int cid = 13;
		List<Property> properties = propertyService.list(cid,0,5).getContent();
		Iterator<Property> iterator = properties.iterator();
		while(iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		
	}*/
}
