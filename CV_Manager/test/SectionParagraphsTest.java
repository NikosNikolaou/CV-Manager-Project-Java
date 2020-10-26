package test;

import static org.junit.Assert.*;

import org.junit.Test;

import cv.CV;

public class SectionParagraphsTest {

	@Test
	public void test() {
		CV cv = new CV();
		
		int sectionAddedNumber = cv.addSection("TestSection");
		assertEquals(1, sectionAddedNumber);
		
		cv.addParagraph(1, "FirstTestParagraph");
		cv.addParagraph(1, "SecondTestParagraph");
		cv.addParagraph(1, "ThirdTestParagraph");
		
		assertEquals("FirstTestParagraph"
						+ System.lineSeparator()
						+ "SecondTestParagraph"
						+ System.lineSeparator()
						+ "ThirdTestParagraph", cv.getSectionsArray().get(0).getSectionParagraphs());
	}
}