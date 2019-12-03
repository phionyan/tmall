package com.phion.tmall.test;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Test {
	
	static class A {
		String name;

		@JsonIgnore
		B b;
		@Override
		public String toString() {
			return "A [name=" + name + ", b=" + b + "]";
		}
		
	}
	
	static class B{
		String name;
		
		@JsonIgnore
		A a;
		@Override
		public String toString() {
			return "B [name=" + name + ", a=" + a + "]";
		}
		
	}
	
	public static void main(String[] args) {
		A a1 = new A();
		a1.name = "a1";
		a1.b = new B();
		a1.b.name = "b1";
		
		
		a1.b.a = a1;
		System.out.println(a1);
		
	}

}
