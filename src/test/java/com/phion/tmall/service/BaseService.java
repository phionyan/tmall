package com.phion.tmall.service;

public class BaseService {
	public static void main(String[] args) {
		String s1 = "abc";
		String s2 = new String("abc");
		String s3 = "abc";
		System.out.println(s1==s2);
		System.out.println(s1.equals(s2));
		
		System.out.println(System.identityHashCode(s1));
		System.out.println(System.identityHashCode(s2));
		System.out.println(System.identityHashCode(s3));
	}
}
