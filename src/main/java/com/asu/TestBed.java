package com.asu;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class TestBed {

	public static void main(String[] args) {
		
		/**
		 *  StringJoiner class with delimiter
		 */
		StringJoiner stringJoiner= new StringJoiner(",");
		stringJoiner.add("one");
		stringJoiner.add("two");
		stringJoiner.add("three");
		
		System.out.println(stringJoiner);
		
		/**
		 *  StringJoiner class with delimiter,suffix and prefix
		 */
		StringJoiner stringJoinerWithSXPX=new StringJoiner(",","{","}");
		stringJoinerWithSXPX.add("one");
		stringJoinerWithSXPX.add("two");
		stringJoinerWithSXPX.add("three");
		System.out.println(stringJoinerWithSXPX);
		
		/**
		 *  String Class with join method
		 */
		String strWithJoiner=String.join(",","one","two","three");
		System.out.println(strWithJoiner);
		
		/**
		 *  String join method with an iterable (anything that can be iterable say list)
		 */
		List<String> alphabetList=Arrays.asList("A","B","C");
		strWithJoiner=String.join(",", alphabetList);
		System.out.println(strWithJoiner);
		
		/**
		 *  Java8 Collectors.joining() method
		 */
		String stringList=alphabetList.stream().map(element->element).collect(Collectors.joining());
		System.out.println(stringList);
		
		stringList=alphabetList.stream().map(element->element).collect(Collectors.joining(","));
		System.out.println(stringList);
		
		stringList=alphabetList.stream().map(element->element).collect(Collectors.joining(",","{","}"));
		System.out.println(stringList);
		
		System.out.println(stringList.indexOf('C'));
	}
}
