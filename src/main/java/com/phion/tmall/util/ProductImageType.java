package com.phion.tmall.util;

/**
 * 用以规范ProductImage的类型
 * 两种图片，一种是简单图片用于显示商品基本信息，详情图片，位于评论下方，模仿天猫
 * @author 15037
 *
 */
public enum ProductImageType {

	BRIEF("brief"),
	DETAIL("detail");
	

	private String type;
	
	ProductImageType(String type){
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type;
	}
}
