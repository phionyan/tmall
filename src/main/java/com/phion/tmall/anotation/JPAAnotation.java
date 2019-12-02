/*package com.phion.tmall.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.core.annotation.AliasFor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Entity
@Table
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public @interface JPAAnotation {
	@AliasFor(annotation = Table.class, attribute = "name")
	String tableName() ;
}*/
