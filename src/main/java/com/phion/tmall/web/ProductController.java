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

import com.phion.tmall.dao.ProductDAO;
import com.phion.tmall.pojo.Product;
import com.phion.tmall.service.ProductService;
import com.phion.tmall.util.Page4Navigator;

@RestController
public class ProductController {

	@Autowired ProductService productService;
	
	@GetMapping("/categories/{id}/products")
	public Page4Navigator<Product> list(@RequestParam(name="start",defaultValue="0")int start,
			@RequestParam(name="size",defaultValue="5")int size,@PathVariable(name="id")int cid){
		return productService.list(cid, start, size);
	}
	
	@GetMapping("/products/{id}")
	public Object get(@PathVariable(name="id")int id) {
		return productService.get(id);
	}

	@DeleteMapping("/products/{id}")
	public Object delete(@PathVariable(name="id")int id ) {
		productService.delete(id);
		return null;
	}
	
	@PutMapping("/products/{id}")
	public Object update(@RequestBody Product product) {
		productService.add(product);
		return product;
	}
	
	@PostMapping("/products")
	public Object add(@RequestBody Product product) {
		productService.add(product);
		return product;
	}
}
