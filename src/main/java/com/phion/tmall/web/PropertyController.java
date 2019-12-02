package com.phion.tmall.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phion.tmall.pojo.Property;
import com.phion.tmall.service.PropertyService;
import com.phion.tmall.util.Page4Navigator;

@RestController
public class PropertyController {

	@Autowired PropertyService propertyService;
	
	@GetMapping("/categories/{cid}/properties")
	public Page4Navigator<Property> list(@PathVariable("cid")int cid,@RequestParam(name="start",defaultValue="0")int start,
			@RequestParam(name="size",defaultValue="5")int size){
		System.out.println("查询properties");
		return propertyService.list(cid, start, size);
	}
	
	@PostMapping("/properties")
	public Object add(@RequestBody Property property) {
		System.out.println(property);
		propertyService.add(property);
		return property;
	}
	
	@PutMapping("/properties/{id}")
	public Object update(Property property) {
		System.out.println(property);
		propertyService.update(property);
		return property;
	}
	
	@GetMapping("/properties/{id}")
	public Object get(@PathVariable("id")int id) {
		return propertyService.get(id);
	}
	
	@DeleteMapping("/properties/{id}")
	public Object delete(@PathVariable("id")int id) {
		propertyService.delete(id);
		return null;
	}
}
