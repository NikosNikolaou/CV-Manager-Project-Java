package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import cv.CV;
import cv.Section;

public class SectionsTest {

	@Test
	public void test() {
		CV cv = new CV();
		cv.addSection("1stTestSection");
		cv.addSection("2ndTestSection");
		cv.addSection("3rdTestSection");
		cv.addSubSection(1, new Section("1stTestSection_SubSection"));
		
		ArrayList<Section> sections = cv.getSectionsArray();
		
		assertEquals(3, sections.size());
		
		assertEquals("1stTestSection", sections.get(0).getTitle());
		assertEquals("2ndTestSection", sections.get(1).getTitle());
		assertEquals("3rdTestSection", sections.get(2).getTitle());
		
		assertEquals(null, sections.get(0).getSectionParagraphs());
		assertEquals(null, sections.get(1).getSectionParagraphs());
		assertEquals(null, sections.get(2).getSectionParagraphs());
		
		assertEquals(null, sections.get(0).getSectionBulletListItems());
		assertEquals(null, sections.get(1).getSectionBulletListItems());
		assertEquals(null, sections.get(2).getSectionBulletListItems());
		
		assertEquals(1, sections.get(0).getSubSections().size());
		assertEquals(0, sections.get(1).getSubSections().size());
		assertEquals(0, sections.get(2).getSubSections().size());
		
		assertEquals("1stTestSection_SubSection", sections.get(0).getSubSections().get(0).getTitle());
	}
}