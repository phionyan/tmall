package com.phion.tmall.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {
	
	@GetMapping(value = "/admin")
	public String admin() {
		return "redirect:admin_category_list";
	}
	
	@GetMapping("/admin_category_list")
	public String listCategory() {
		return "admin/listCategory";
	}
	
	@GetMapping(value="/admin_property_list")
    public String listProperty(){
        return "admin/listProperty";
    }
	
	@GetMapping(value="/admin_product_list")
    public String listProduct(){
        return "admin/listProduct";
    }
	
	@GetMapping(value="/admin_productImage_list")
    public String listProductImage(){
        return "admin/listProductImage";
    }
	
	@GetMapping(value="/admin_propertyValue_list")
    public String listPropertyValue(){
        return "admin/listPropertyValue";
    }
}
