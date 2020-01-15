package com.phion.tmall.service;

import java.util.List;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import com.phion.tmall.dao.ProductImageDAO;
import com.phion.tmall.pojo.OrderItem;
import com.phion.tmall.pojo.Product;
import com.phion.tmall.pojo.ProductImage;
import com.phion.tmall.util.ProductImageType;


@Service
@CacheConfig(cacheNames="productImages")
@EnableAspectJAutoProxy(exposeProxy = true)
public class ProductImageService {

	@Autowired ProductImageDAO productImageDAO;
	@Autowired ProductService productService;
	
	@Cacheable(key="'productImages-listBriefProductImages-'+#pid")
	public List<ProductImage> listBriefProductImages(int pid) {
		Product product = productService.get(pid);
		return productImageDAO.findByProductAndTypeOrderByIdDesc(product,ProductImageType.BRIEF.toString());
	}

	@Cacheable(key="'productImages-listDetailProductImages-'+#pid")
	public List<ProductImage> listDetailProductImages(int pid) {
		Product product = productService.get(pid);
		return productImageDAO.findByProductAndTypeOrderByIdDesc(product, ProductImageType.DETAIL.toString());
	}

	public void add(ProductImage productImage) {
		productImageDAO.save(productImage);
	}

	@CacheEvict(key="'productImages-one-'+#productImage.id")
	public void delete(int id) {
		productImageDAO.delete(id);
	}
	
	@CacheEvict(key="'productImages-one-'+#productImage.id")
	public void update(ProductImage productImage) {
		productImageDAO.save(productImage);
	}

	@Cacheable(key="'productImages-one-'+#productImage.id")
	public ProductImage get(int id) {
		return productImageDAO.findOne(id);
	}

	/**
	 * 从简单图片中抽一张出来作为预览图片
	 * @param product
	 */
	public void setFirstProdutImage(Product product) {
		//System.out.println("now is : "+System.identityHashCode(this));
		ProductImageService productImageService = (ProductImageService)AopContext.currentProxy();
		//System.out.println(productImageService.equals(SpringContextUtil.getBean(ProductImageService.class)));
		//productImageService = SpringContextUtil.getBean(ProductImageService.class);
		//System.out.println(product.getId()+" is : "+System.identityHashCode(productImageService));
		List<ProductImage> briefImages = productImageService.listBriefProductImages(product.getId());
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
