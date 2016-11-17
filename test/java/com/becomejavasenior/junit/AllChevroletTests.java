package com.becomejavasenior.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	CamaroTest.class,
	CamaroParametrizedSpeedTest.class
  }
)
public class AllChevroletTests {
}
