package com.becomejavasenior;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CalculatorTest {
	static Integer[] initValues;
	
	@BeforeClass
	public static void commonSetUp() {
		initValues = new Integer[2];
		
		initValues[0] = 2;
		initValues[1] = 3;
	}
	
	@Test
	public void testAdd() {	
		Integer actualResult = Calculator.add(initValues[0], initValues[1]);
		Integer expectedResult = 5;
		
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	public void testSub() {	
		Integer actualResult = Calculator.sub(initValues[0], initValues[1]);
		Integer expectedResult = -1;
		
		assertEquals(expectedResult, actualResult);
	}
	
	@AfterClass
	public static void commonTearDown() {
		//System.out.println("com.becomejavasenior.CalculatorTest done!");
	}
}
