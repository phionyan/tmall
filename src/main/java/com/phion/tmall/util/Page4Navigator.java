package com.phion.tmall.util;

import java.util.List;

import org.springframework.data.domain.Page;

/**
 * 为导航页面制作的分页类，可以直接取属性
 * @author 15037
 *
 * @param <T>
 */
public class Page4Navigator<T> {

	Page<T> pageFromJPA;

	int totalPages;

	int number;

	long totalElements;

	int size;

	int numberOfElements;

	List<T> content;

	boolean isHasContent;

	boolean first;

	boolean last;

	boolean isHasNext;

	boolean isHasPrevious;

	public Page4Navigator(Page<T> pageFromJPA) {
		this.pageFromJPA = pageFromJPA;

		totalPages = pageFromJPA.getTotalPages();

		number = pageFromJPA.getNumber();

		totalElements = pageFromJPA.getTotalElements();

		size = pageFromJPA.getSize();

		numberOfElements = pageFromJPA.getNumberOfElements();

		content = pageFromJPA.getContent();

		isHasContent = pageFromJPA.hasContent();

		first = pageFromJPA.isFirst();

		last = pageFromJPA.isLast();

		isHasNext = pageFromJPA.hasNext();

		isHasPrevious = pageFromJPA.hasPrevious();

	}

	public Page<T> getPageFromJPA() {
		return pageFromJPA;
	}

	public void setPageFromJPA(Page<T> pageFromJPA) {
		this.pageFromJPA = pageFromJPA;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public boolean isHasContent() {
		return isHasContent;
	}

	public void setHasContent(boolean isHasContent) {
		this.isHasContent = isHasContent;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public boolean isHasNext() {
		return isHasNext;
	}

	public void setHasNext(boolean isHasNext) {
		this.isHasNext = isHasNext;
	}

	public boolean isHasPrevious() {
		return isHasPrevious;
	}

	public void setHasPrevious(boolean isHasPrevious) {
		this.isHasPrevious = isHasPrevious;
	}

}
