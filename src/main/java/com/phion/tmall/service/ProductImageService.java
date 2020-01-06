package com.phion.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phion.tmall.dao.ProductImageDAO;
import com.phion.tmall.pojo.OrderItem;
import com.phion.tmall.pojo.Product;
import com.phion.tmall.pojo.ProductImage;
import com.phion.tmall.util.ProductImageType;


@Service
public class ProductImageService {

	@Autowired ProductImageDAO productImageDAO;
	@Autowired ProductService productService;
	
	
	public List<ProductImage> listBriefProductImages(int pid) {
		Product product = productService.get(pid);
		return productImageDAO.findByProductAndTypeOrderByIdDesc(product,ProductImageType.BRIEF.toString());
	}
	
	public List<ProductImage> listDetailProductImages(int pid) {
		Product product = productService.get(pid);
		return productImageDAO.findByProductAndTypeOrderByIdDesc(product, ProductImageType.DETAIL.toString());
	}
	
	public void add(ProductImage productImage) {
		productImageDAO.save(productImage);
	}

	public void delete(int id) {
		productImageDAO.delete(id);
	}
	public void update(ProductImage productImage) {
		productImageDAO.save(productImage);
	}
	public ProductImage get(int id) {
		return productImageDAO.findOne(id);
	}

	/**
	 * 从简单图片中抽一张出来作为预览图片
	 * @param product
	 */
	public void setFirstProdutImage(Product product) {
		List<ProductImage> briefImages = listBriefProductImages(product.getId());
		if(!briefImages.isEmpty())
			product.setFirstProductImage(briefImages.get(0));
		else
			product.setFirstProductImage(new ProductImage()); //这样做是考虑到产品还没有来得及设置图片，但是在订单后台管理里查看订单项的对应产品图片。

	}
	/**
	 * 提供用于预览的图片
	 * @param products
	 */
	public void setFirstProdutImages(List<Product> products) {
		if(products==null) return;
		for (Product product : products)
			setFirstProdutImage(product);
	}

	public void setFirstProdutImagesOnOrderItems(List<OrderItem> orderItems) {
		for (OrderItem orderItem : orderItems) {
            setFirstProdutImage(orderItem.getProduct());
        }
	}
	
}
