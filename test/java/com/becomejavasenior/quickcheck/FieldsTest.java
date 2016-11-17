package com.becomejavasenior.quickcheck;

import com.becomejavasenior.Calculator;
import com.pholser.junit.quickcheck.ForAll;
import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.generator.Ctor;
import com.pholser.junit.quickcheck.generator.Fields;
import com.pholser.junit.quickcheck.generator.InRange;
import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;

/**
 * https://github.com/pholser/junit-quickcheck/
 */
@RunWith(Theories.class)
public class FieldsTest {

	/**
	 * From Fields values
	 */
		public static class Point {
			public double x;
			public double y;
			public double z;

			public int size;
		}

		@Theory public void originDistance(@ForAll @From(Fields.class) Point p) {
			System.out.println(p.x +" size=" +p.size);

			assertTrue(Math.sqrt(p.x * 2 + p.y * 2 + p.z * 2) > 0);
		}



}
