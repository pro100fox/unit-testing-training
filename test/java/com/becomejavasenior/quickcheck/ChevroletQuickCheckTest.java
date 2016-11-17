package com.becomejavasenior.quickcheck;

import com.becomejavasenior.Calculator;
import com.becomejavasenior.applecar.Camaro;
import com.becomejavasenior.applecar.Chevrolet;
import com.pholser.junit.quickcheck.ForAll;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.generator.ValuesOf;
import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;

/**
 * https://github.com/pholser/junit-quickcheck/
 */
@RunWith(Theories.class)
public class ChevroletQuickCheckTest {

	//(sampleSize = 20)
	//@InRange(min = "0", max = "20")


	@Theory
	public void speedTest(@ForAll(sampleSize = 100) @InRange(min = "60", max = "80") BigDecimal speed) {

		Camaro camaro = new Camaro();
		camaro.setSpeed(speed.intValue());
		System.out.println(speed.intValue());
		System.out.println(camaro.getGear());
		assertTrue(camaro.getGear() == 4);
		//assumeTrue("Wrong gear",camaro.getGear() == 1 );



	}


	/**
	 *  Make a mistake in setSpeed - remove ">="
	 * @param speed
	 */
	@Theory
	public void gearsTest2(@ForAll(sampleSize = 100000) @InRange(min = "60", max = "100") BigDecimal speed) {
		System.out.println(speed);
		Chevrolet camaro = new Camaro();

		camaro.setSpeed(speed.intValue());
		Integer[] gears = {3,4,5};

		assertThat(camaro.getGear(), isIn(gears));

	}

	@Theory public void enumTest(
			@ForAll @ValuesOf boolean b,
			@ForAll @ValuesOf Camaro.GearEnum gear) {


		Chevrolet camaro = new Camaro();
		camaro.setGear(gear.getGear());

		System.out.println(b + " " + gear);
		// Each combination of potential values will be generated.

	}

}
