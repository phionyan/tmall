package com.phion.tmall.util;

public enum OrderType {
    
    WAIT_PAY("waitPay"),
    WAIT_DELIVERY("waitDelivery"),
    WAIT_CONFIRM("waitConfirm"),
    WAIT_REVIEW("waitReview"),
    FINISH("finish"),
	DELETE("delete");
	

	private String type;
	
	OrderType(String type){
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type;
	}
}
