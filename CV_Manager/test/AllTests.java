package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CVGeneralInformationTest.class, SectionBulletListsTest.class, SectionParagraphsTest.class,
				SectionsTest.class, SectionSubBulletListTest.class, CVmergerTest.class })

public class AllTests {}