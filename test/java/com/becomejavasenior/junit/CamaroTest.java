package com.becomejavasenior.junit;

import com.becomejavasenior.applecar.Camaro;
import com.becomejavasenior.applecar.Chevrolet;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CamaroTest {
	static Integer[] speedValues;

	static Chevrolet camaro;
	
	@BeforeClass
	public static void commonSetUp() {
		camaro = new Camaro();
		speedValues = new Integer[5];
		speedValues[0] = 5;
		speedValues[1] = 15;
		speedValues[2] = 25;
		speedValues[3] = 35;
		speedValues[4] = 45;
	}

	@Before
	public void setUp(){
		camaro.setSpeed(5);
	}

	@Test
	public void testSpeed() {
		camaro.setSpeed(230);
		assertEquals("Gear is not correct",camaro.getGear(), 1);

		camaro.setSpeed(speedValues[4]);
		assertEquals(camaro.getGear(), 3);

		camaro.setSpeed(speedValues[3]);
		assertEquals(camaro.getGear(), 2);

		camaro.setSpeed(speedValues[1]);
		assertEquals(camaro.getGear(), 1);

		camaro.setSpeed(speedValues[2]);
		assertEquals(camaro.getGear(), 2);
	}

	@Test
	public void testIncreaseSpeed() {
		camaro.increaseSpeed(10);
		assertEquals(camaro.getGear(), 1);

		camaro.increaseSpeed(10);
		assertEquals(camaro.getGear(), 2);

		camaro.increaseSpeed(10);
		assertEquals(camaro.getGear(), 2);

		camaro.increaseSpeed(10);
		assertEquals(camaro.getGear(), 3);

		camaro.increaseSpeed(10);
		assertEquals(camaro.getGear(), 3);
	}

	
	@AfterClass
	public static void commonTearDown() {
		camaro.stop();
	}
}
