package com.phion.tmall.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="User")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	
	private String name;
	
	private String password;
	
	private String salt;//用于加密密码
	
	@Transient
	private String anonymousName;//匿名 名称，用于评价,数据库中并没有，需要Transient标记

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getAnonymousName() {
		anonymousName = name;
		int len = anonymousName.length();
		if(len<=2)
			anonymousName=anonymousName.substring(0,1)+"*";
		else {
			String hideChars ="";
			for(int i = 0 ; i <len-2;i++) {
				hideChars+="*";
			}
			anonymousName=anonymousName.substring(0,1)+hideChars+anonymousName.substring(len-1,len);
		}
		return anonymousName;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", salt=" + salt + ", anonymousName="
				+ anonymousName + "]";
	}
	
	
}
