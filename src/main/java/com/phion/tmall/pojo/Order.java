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
import com.phion.tmall.util.OrderType;

@Entity
@Table(name = "order_")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne//查询订单的时候查出对应的用户
	@JoinColumn(name="uid")
	private User user;
	
	private String orderCode;//订单编号
	private String address;//收获地址
	private String post;//邮编
	private String receiver;//收货人
	private String mobile;//手机号
	private String userMessage;//备注
	private Date createDate;//订单创建日期
	private Date payDate;//支付日期
	private Date deliveryDate;//发货日期
	private Date confirmDate;//确认收获日期
	private String status;//订单状态
	
	@Transient
	private List<OrderItem> orderItems;
	
	@Transient
	private float totalPrice;//订单总金额
	@Transient
	private int totalNumber;//订单项的数量
	@Transient
	private String statusDesc;//订单状态的描述
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUserMessage() {
		return userMessage;
	}
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

	public String getStatusDesc() {
		if(OrderType.WAIT_PAY.toString().equals(status)) {
			return "等待支付";
		}else if(OrderType.WAIT_DELIVERY.toString().equals(status)) {
			return "等待发货";
		}else if(OrderType.WAIT_CONFIRM.toString().equals(status)) {
			return "等待确认收货";
		}else if(OrderType.WAIT_REVIEW.toString().equals(status)) {
			return "等待评价";
		}else if(OrderType.FINISH.toString().equals(status)) {
			return "已完成";
		}else if(OrderType.DELETE.toString().equals(status)) {
			return "已删除";
		}
		return null;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", user=" + user + ", orderCode=" + orderCode + ", address=" + address + ", post="
				+ post + ", receiver=" + receiver + ", mobile=" + mobile + ", userMessage=" + userMessage
				+ ", createDate=" + createDate + ", payDate=" + payDate + ", deliveryDate=" + deliveryDate
				+ ", confirmDate=" + confirmDate + ", status=" + status + ", orderItems=" + orderItems + ", totalPrice="
				+ totalPrice + ", totalNumber=" + totalNumber + ", statusDesc=" + statusDesc + "]";
	}
	
	
}
