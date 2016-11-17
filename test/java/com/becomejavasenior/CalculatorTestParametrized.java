package com.becomejavasenior;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;

@RunWith(Parameterized.class)
public class CalculatorTestParametrized {
	Integer pA;
	Integer pB;
	Integer pExpectedResult1;
	Integer pExpectedResult2;
	
	public CalculatorTestParametrized(
			Integer pA, 
			Integer pB, 
			Integer pExpectedResult1, 
			Integer pExpectedResult2) {
		this.pA = pA;
		this.pB = pB;
		this.pExpectedResult1 = pExpectedResult1;
		this.pExpectedResult2 = pExpectedResult2;
	}
	
	@Parameters
	public static Collection testValues() {
		return Arrays.asList(new Object[][] {
	  		{2, 3, 5, -1 },
	  		{3, 2, 5, 1 },
	  		{-2, -3, -5, 1 },
	  		{-2, 3, 1, -5 },
	  		{2, -3, -1, 5}});
	}
	
	@Test
	public void testAdd() {	
		Integer actualResult = Calculator.add(pA, pB);		
		assertEquals(pExpectedResult1, actualResult);
	}
	
	@Test
	public void testSub() {	
		Integer actualResult = Calculator.sub(pA, pB);		
		assertEquals(pExpectedResult2, actualResult);
	}
}
