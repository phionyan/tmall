package com.phion.tmall.pojo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="product")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//加上这个注解，jpa会将save后的bean返回
	@Column(name = "id")
	private int id;
	
	
	private String name;//商品名称
	
	private String subTitle;//小标题
	
	private float originalPrice;//原价
	
	private float promotePrice;//促销价
	
	private int stock;//库存量
	
	@ManyToOne
	@JoinColumn(name="cid")
	private Category category;
	
	Date createDate;
	
	@Transient
	private ProductImage firstProductImage;//这个字段用于提供预览图片，不需要序列化

	
	/**
	 * 前台用于展示的数据
	 */
	@Transient
	private List<ProductImage> productBriefImages;
	
	@Transient
	private List<ProductImage> productDetailImages;
	
	@Transient
	private int saleCount;//销量
	
	@Transient
	private int reviewCount;//评价数量 
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public float getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(float originalPrice) {
		this.originalPrice = originalPrice;
	}

	public float getPromotePrice() {
		return promotePrice;
	}

	public void setPromotePrice(float promotePrice) {
		this.promotePrice = promotePrice;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public ProductImage getFirstProductImage() {
		return firstProductImage;
	}

	public void setFirstProductImage(ProductImage firstProductImage) {
		this.firstProductImage = firstProductImage;
	}
	
	
	

	public List<ProductImage> getProductBriefImages() {
		return productBriefImages;
	}

	public void setProductBriefImages(List<ProductImage> productBriefImages) {
		this.productBriefImages = productBriefImages;
	}

	public List<ProductImage> getProductDetailImages() {
		return productDetailImages;
	}

	public void setProductDetailImages(List<ProductImage> productDetailImages) {
		this.productDetailImages = productDetailImages;
	}
	
	
	public int getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", subTitle=" + subTitle + ", originalPrice=" + originalPrice
				+ ", promotePrice=" + promotePrice + ", stock=" + stock + ", category=" + category + ", createDate="
				+ createDate + ", firstProductImage=" + firstProductImage + ", productBriefImages=" + productBriefImages
				+ ", productDetailImages=" + productDetailImages + ", saleCount=" + saleCount + ", reviewCount="
				+ reviewCount + "]";
	}
	
}
