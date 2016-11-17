package com.smarterama.zhevaha.integerDivision;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.smarterama.zhevaha.integerDivision.Division;

public class DivisionTest {

	private static int dividend;
	private static int divider;
	private static Division division;

	@Test
	public void testDivisionWithoutRemainder() {
		dividend = 550;
		divider = 55;
		division = new Division(dividend, divider);
		String result = division.composeDivisionOutput();
		int intResult = division.getDividend() % division.getDivider();

		String expected = " 550 |55\n-    -----\n 55  |10\n __\n   0\n";

		assertEquals(expected, result);
		assertTrue(intResult == 0);
	}

	@Test
	public void testDivisionWithRemainder() {
		dividend = 553;
		divider = 55;
		division = new Division(dividend, divider);
		String result = division.composeDivisionOutput();
		int intResult = division.getDividend() % division.getDivider();

		String expected = " 553 |55\n-    -----\n 55  |10.0(54)\n __\n   3\n  -\n   275"
				+ "\n   ___\n    250\n   -\n    220\n    ___\n     30\n";

		assertEquals(expected, result);
		assertTrue(intResult == 3);
	}

	@Test
	public void testDividendMoreThanDivider() {

		dividend = 78459;
		divider = 4;
		division = new Division(dividend, divider);
		String result = division.composeDivisionOutput();
		int intResult = division.getDividend() / division.getDivider();

		String expected = " 78459 |4\n-      -----\n 4     |19614.75\n _\n 38\n-\n 36\n __\n  24\n -"
				+ "\n  24\n  __\n    5\n   -\n    4\n    _\n    19\n   -\n    16\n    __\n     30"
				+ "\n    -\n     28\n     __\n      20\n     -\n      20\n      __\n       0\n";

		assertEquals(expected, result);
		assertTrue(intResult > 1);

	}

	@Test
	public void testDividerMoreThanDivident() {

		dividend = 7;
		divider = 12;
		division = new Division(dividend, divider);
		String result = division.composeDivisionOutput();
		double intResult = (double) division.getDividend()
				/ division.getDivider();

		String expected = " 7  |12\n-   -----\n 60 |0.58(3)\n __\n 100\n"
				+ "-\n  96\n ___\n   40\n  -\n   36\n   __\n    4\n";

		assertEquals(expected, result);
		assertTrue(intResult > 0 && intResult < 1);

	}

	@Test
	public void testDividendEqualsDivider() {
		dividend = 33;
		divider = 33;
		division = new Division(dividend, divider);

		int result = division.getDividend() / division.getDivider();
		assertTrue(result == 1);
	}

	@Test(expected = ArithmeticException.class)
	public void testDividerZero() {
		dividend = 100000;
		divider = 0;
		division = new Division(dividend, divider);

		int intResult = division.getDividend() / division.getDivider();
	}

	@Test
	public void testDividendZero() {
		dividend = 0;
		divider = 100;
		division = new Division(dividend, divider);

		int intResult = division.getDividend() / division.getDivider();
		assertTrue(intResult == 0);

	}

	@Test
	public void testNegativeDivision() {
		dividend = -2016;
		divider = 33;
		division = new Division(dividend, divider);
		String result = division.composeDivisionOutput();

		int intResult = division.getDividend() / division.getDivider();

		String expected = "-2016 |33\n-     -----\n 198  |-61.(09)\n ___\n   36"
				+ "\n  -\n   33\n   __\n    30\n   -\n    297\n    ___\n      3\n";

		assertEquals(expected, result);
		assertTrue(intResult < 0);
	}

}
