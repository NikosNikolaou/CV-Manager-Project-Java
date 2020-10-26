package test;

import static org.junit.Assert.*;

import org.junit.Test;

import cv.BulletListItem;
import cv.CV;

public class SectionSubBulletListTest {

	@Test
	public void test() {
		CV cv = new CV();
		
		cv.addSection("TestSection");
		
		BulletListItem b1 = new BulletListItem("1st Bullet", "03/03/2015");
		BulletListItem b2 = b1.addSubBulletListItem("1st Bullet's --> SubBullet 1st Line" + System.lineSeparator() + "1st Bullet's --> SubBullet 2nd Line", null);
		b2.addSubBulletListItem("1st Bullet's Sub --> SubBullet 1", null);
		b2.addSubBulletListItem("1st Bullet's Sub --> SubBullet 2", null);
		
		cv.addBulletListItem(1, b1);
		
		
		String actual = cv.getSectionsArray().get(0).getSectionBulletListItemsAsText();
		String expected = "\t* 1st Bullet, 03/03/2015"
						+ System.lineSeparator()
						+ "\t\t* 1st Bullet's --> SubBullet 1st Line"
						+ System.lineSeparator()
						+ "\t\t  1st Bullet's --> SubBullet 2nd Line"
						+ System.lineSeparator()
						+ "\t\t\t* 1st Bullet's Sub --> SubBullet 1"
						+ System.lineSeparator()
						+ "\t\t\t* 1st Bullet's Sub --> SubBullet 2"
						+ System.lineSeparator();
		
		assertEquals(expected, actual);
	}
}