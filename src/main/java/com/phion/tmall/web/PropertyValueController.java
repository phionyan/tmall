package com.phion.tmall.web;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.phion.tmall.pojo.Product;
import com.phion.tmall.pojo.PropertyValue;
import com.phion.tmall.service.ProductService;
import com.phion.tmall.service.PropertyValueService;

@RestController
public class PropertyValueController {

	@Autowired PropertyValueService propertyValueService;
	@Autowired ProductService productService ;
	
	@GetMapping("/products/{id}/propertyValues")
    public List<PropertyValue> list(@PathVariable("id") int pid) throws Exception {
        propertyValueService.init(pid);
        List<PropertyValue> propertyValues = propertyValueService.list(pid);
        return propertyValues;
    }
	//@RequestParam(name="propertyValues") String propertyValues
	//@RequestParam(value="id")String id
	@PutMapping("/propertyValues")
    public String update(@RequestBody PropertyValue[] propertyValuesArr) throws Exception {
		List<PropertyValue> propertyValues = Arrays.asList(propertyValuesArr);
		System.out.println(propertyValues.size());
		Iterator<PropertyValue> iterator = propertyValues.iterator();
        while(iterator.hasNext()) {
        	PropertyValue propertyValue = iterator.next();
        	propertyValueService.update(propertyValue);;
        	//System.out.println(propertyValue);
        }
        return "";
    }
	
}
