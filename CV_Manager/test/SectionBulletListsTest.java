package test;

import static org.junit.Assert.*;

import org.junit.Test;

import cv.CV;

public class SectionBulletListsTest {

	@Test
	public void test() {
		CV cv = new CV();
		
		cv.addSection("TestSection");
		cv.addBulletListItem(1, "2nd Bullet", "02/02/2016");
		cv.addBulletListItem(1, "4rth Bullet", null);
		cv.addBulletListItem(1, "1st Bullet", "03/03/2015");
		cv.addBulletListItem(1, "3rd Bullet", "01/01/2017");
		
		String actual = cv.getSectionsArray().get(0).getSectionBulletListItems();
		String expected = "1st Bullet, 03/03/2015"
							+ System.lineSeparator()
							+ "2nd Bullet, 02/02/2016"
							+ System.lineSeparator()
							+ "3rd Bullet, 01/01/2017"
							+ System.lineSeparator()
							+ "4rth Bullet";
		
		assertEquals(expected, actual);
	}
}