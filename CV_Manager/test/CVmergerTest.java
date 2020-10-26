package test;

import static org.junit.Assert.*;

import org.junit.Test;

import cv.BulletListItem;
import cv.CV;
import cv.CVmerger;
import cv.Section;

public class CVmergerTest {

	@Test
	public void test() {
		CV cv1 = new CV();
		CV cv2 = new CV();
		
		/////////////////////////////////////////////////////////////////
		
		cv1.setGeneralInformation("TestName", "TestAddress", "TestHomeTelephone", "TestMobileTelephone", "TestEmail");
		cv1.setCvType("Test");
		
		cv1.addSection("1st Section");
		cv1.addParagraph(1, "paragraph1");
		
		cv1.addSection("2nd Section");
		cv1.addBulletListItem(2, "bulletlistitem1", null);
		
		cv1.addSection("3rd Section");
		Section cv1SubSection1 = new Section("3.1");
		cv1SubSection1.addBulletListItem("bulletlistitem1", "22/05/2017");
		Section cv1SubSection2 = new Section("3.2");
		cv1SubSection2.addBulletListItem("bulletlistitem1", "22/05/2017");
		cv1.addSubSection(3, cv1SubSection1);
		cv1.addSubSection(3, cv1SubSection2);
		
		cv1.addSection("4th Section");
		BulletListItem cv1BulletListItem = new BulletListItem("4.1", null);
		cv1BulletListItem.addSubBulletListItem("4.1.1", null);
		cv1.addBulletListItem(4, cv1BulletListItem);
		
		///////////////////////////////////////////////////////////////////
		
		cv2.setGeneralInformation("TestName", "TestAddress", "TestHomeTelephone", "TestMobileTelephone", "TestEmail");
		cv2.setCvType("Test");
		
		cv2.addSection("1st Section");
		cv2.addParagraph(1, "paragraph2");
		
		cv2.addSection("2nd Section");
		cv2.addBulletListItem(2, "bulletlistitem2", null);
		cv2.addSection("3rd Section");
		
		Section cv2SubSection1 = new Section("3.1");
		cv2SubSection1.addBulletListItem("bulletlistitem2", "22/05/2017");
		Section cv2SubSection2 = new Section("3.2");
		cv2SubSection2.addBulletListItem("bulletlistitem1", "22/05/2017");
		Section cv2SubSection3 = new Section("3.3");
		cv2SubSection3.addBulletListItem("bulletlistitem1", "22/05/2017");
		cv2.addSubSection(3, cv2SubSection1);
		cv2.addSubSection(3, cv2SubSection2);
		cv2.addSubSection(3, cv2SubSection3);
		
		cv2.addSection("4th Section");
		BulletListItem cv2BulletListItem = new BulletListItem("4.1", null);
		cv2BulletListItem.addSubBulletListItem("4.1.2", null);
		cv2BulletListItem.addSubBulletListItem("4.1.3", null);
		BulletListItem cv2BulletListItem2 = new BulletListItem("4.2", null);
		cv2BulletListItem2.addSubBulletListItem("4.2.1", null);
		cv2BulletListItem2.addSubBulletListItem("4.2.2", null);
		cv2BulletListItem2.addSubBulletListItem("4.2.3", null);
		cv2.addBulletListItem(4, cv2BulletListItem);
		cv2.addBulletListItem(4, cv2BulletListItem2);
		
		///////////////////////////////////////////////////////////////////
		
		CV mergedCV = CVmerger.merge(cv1, cv2);
		String actual = mergedCV.toPlainText();
		String expected = "1. GENERAL INFORMATION" +
				System.lineSeparator() +
				"\tName: TestName" +
				System.lineSeparator() +
				"\tAddress: TestAddress" +
				System.lineSeparator() +
				"\tTelephone" +
				System.lineSeparator() +
				"\t\tHome: TestHomeTelephone" +
				System.lineSeparator() +
				"\t\tMobile: TestMobileTelephone" +
				System.lineSeparator() +
				"\tEmail: TestEmail" +
				System.lineSeparator() +
				System.lineSeparator() +
				////////////////////////
				"1st Section" +
				System.lineSeparator() +
				"\tparagraph1" +
				System.lineSeparator() +
				"\tparagraph2" +
				System.lineSeparator() +
				////////////////////////
				System.lineSeparator() +
				"2nd Section" +
				System.lineSeparator() +
				"\t* bulletlistitem1" +
				System.lineSeparator() +
				"\t* bulletlistitem2" +
				System.lineSeparator() +
				System.lineSeparator() +
				////////////////////////
				"3rd Section" +
				System.lineSeparator() +
				"\t3.1" +
				System.lineSeparator() +
				"\t\t* bulletlistitem1, 22/05/2017" +
				System.lineSeparator() +
				"\t\t* bulletlistitem2, 22/05/2017" +
				System.lineSeparator() +
				////////////////////////
				System.lineSeparator() +
				"\t3.2" +
				System.lineSeparator() +
				"\t\t* bulletlistitem1, 22/05/2017" +
				System.lineSeparator() +
				System.lineSeparator() +
				////////////////////////
				"\t3.3" +
				System.lineSeparator() +
				"\t\t* bulletlistitem1, 22/05/2017" +
				System.lineSeparator() +
				System.lineSeparator() +
				"4th Section" +
				System.lineSeparator() +
				"\t* 4.1" +
				System.lineSeparator() +
				"\t\t* 4.1.1" +
				System.lineSeparator() +
				"\t\t* 4.1.2" +
				System.lineSeparator() +
				"\t\t* 4.1.3" +
				System.lineSeparator() +
				"\t* 4.2" +
				System.lineSeparator() +
				"\t\t* 4.2.1" +
				System.lineSeparator() +
				"\t\t* 4.2.2" +
				System.lineSeparator() +
				"\t\t* 4.2.3" +
				System.lineSeparator();
		
		assertEquals(expected, actual);
	}

}
