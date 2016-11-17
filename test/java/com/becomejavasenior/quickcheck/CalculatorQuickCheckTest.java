package com.becomejavasenior.quickcheck;

import com.becomejavasenior.Calculator;
import com.pholser.junit.quickcheck.ForAll;
import com.pholser.junit.quickcheck.generator.InRange;
import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;

/**
 * https://github.com/pholser/junit-quickcheck/
 */
@SuppressWarnings("deprecation")
@RunWith(Theories.class)
public class CalculatorQuickCheckTest {


	@Theory
	public void addTest(@ForAll(sampleSize = 5) @InRange(min = "0", max = "200") BigDecimal a,
						@ForAll(sampleSize = 5) @InRange(min = "-180", max = "0")  BigDecimal b) {
		System.out.println(a +" "+ b);
		assumeTrue(Calculator.add(a.intValue(), b.intValue()) < 0);

	}

	@Theory
	public void subTest(@ForAll int a, @ForAll int b) {
		assumeTrue(Calculator.add(a, b) > 0);
		//System.out.println(a);

	}


	/**
	 * With size
	 * @param latitude
	 * @param longitude
	 */
	@Theory public void northernHemisphere(
			@ForAll(sampleSize = 20) @InRange(min = "-90", max = "90") BigDecimal latitude,
			@ForAll(sampleSize = 20) @InRange(min = "-180", max = "180") BigDecimal longitude) {
		System.out.println(latitude + " " +longitude);
		assumeThat(latitude, greaterThan(BigDecimal.ZERO));
	}

}
