package com.becomejavasenior.junit;

import com.becomejavasenior.applecar.Camaro;
import com.becomejavasenior.applecar.Chevrolet;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CamaroParametrizedSpeedTest {
	int speed;
	int speedIncrease;
	int expectedGear;
	int expectedIncreasedGear;


	static Chevrolet camaro;

	public CamaroParametrizedSpeedTest(int speed, int speedIncrease, int expectedGear, int expectedIncreasedGear) {
		this.speed = speed;
		this.speedIncrease = speedIncrease;
		this.expectedGear = expectedGear;
		this.expectedIncreasedGear = expectedIncreasedGear;
	}

	@BeforeClass
	public static void commonSetUp() {
		camaro = new Camaro();
		camaro.setSpeed(5);
	}

	@Parameterized.Parameters
	public static Collection testValues() {
		return Arrays.asList(new Object[][]{
				{5,  10, 1, 1},
				{15, 10, 1, 2},
				{25, 10, 2, 2},
				{35, 10, 2, 3},
				{45, 10, 3, 3},
				{180, 10, 6, 6}});
	}

	@Test
	public void testSpeed() {
		camaro.setSpeed(speed);
		assertEquals(camaro.getGear(), expectedGear);
	}

	@Test
	public void testIncreaseSpeed() {
		camaro.increaseSpeed(speedIncrease);
		assertEquals(camaro.getGear(), expectedIncreasedGear);
	}

	
	@AfterClass
	public static void commonTearDown() {
		camaro.stop();
	}
}
