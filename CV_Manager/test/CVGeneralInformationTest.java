package test;

import static org.junit.Assert.*;

import org.junit.Test;

import cv.CV;

public class CVGeneralInformationTest {

	@Test
	public void test() {
		CV cv = new CV();
		cv.setGeneralInformation("TestName", "TestAddress", "TestHomeTelephone", "TestMobilePhone", "TestEmail");
		
		String actual = cv.getGeneralInformation();
		String expected = "TestName" + "|" + "TestAddress" + "|" + "TestHomeTelephone" + "|" + "TestMobilePhone" + "|" + "TestEmail";
				
		assertEquals(expected, actual);
	}
}