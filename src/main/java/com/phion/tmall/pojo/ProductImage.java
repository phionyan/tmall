package com.phion.tmall.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="productimage")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class ProductImage {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//加上这个注解，jpa会将save后的bean返回
	@Column(name="id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="pid")
	//@JsonBackReference //解除双向关联，因为Product包含它
	private Product product;
	
	String type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ProductImage [id=" + id + ", product=" + product + ", type=" + type + "]";
	}
	
	
}
