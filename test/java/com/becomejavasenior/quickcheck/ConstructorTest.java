package com.becomejavasenior.quickcheck;

import com.pholser.junit.quickcheck.ForAll;
import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.generator.Ctor;
import com.pholser.junit.quickcheck.generator.Fields;
import com.pholser.junit.quickcheck.generator.InRange;
import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;

/**
 * https://github.com/pholser/junit-quickcheck/
 */
@RunWith(Theories.class)
public class ConstructorTest {



	/**
	 * From Constructor values
	 */
	public static class Coordinate {
		private final BigDecimal latitude;
		private final BigDecimal longitude;

		public Coordinate(
				@InRange(min = "-90", max = "0") BigDecimal latitude,
				@InRange(min = "-180", max = "180") BigDecimal longitude) {

			this.latitude = latitude;
			this.longitude = longitude;
		}

		public BigDecimal latitude() { return latitude; }
		public BigDecimal longitude() { return longitude; }
		public boolean inNorthern() {
			return latitude.compareTo(BigDecimal.ZERO) > 0;
		}
	}

	@Theory public void northernHemisphere(@ForAll @From(Ctor.class) Coordinate c) {
		assumeThat(c.latitude(), greaterThan(BigDecimal.ZERO));

		System.out.println(c.latitude());

		assertTrue(c.inNorthern());
	}




}
