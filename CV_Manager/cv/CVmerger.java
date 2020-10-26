package cv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JOptionPane;

public class CVmerger {
	
	public static CV merge(CV cv1, CV cv2){
		if (checkCvTypeAndOwnerMatch(cv1, cv2)){
			int totalSections = cv2.getSectionsArray().size();
			for (int i=0; i<totalSections; i++){
				mergeParagraphs(cv1.getSectionsArray().get(i), cv2.getSectionsArray().get(i));
				mergeBulletLists(cv1.getSectionsArray().get(i).getBulletListItemsArray(), cv2.getSectionsArray().get(i).getBulletListItemsArray());
				mergeSubSections(cv1.getSectionsArray().get(i), cv2.getSectionsArray().get(i));
			}
			
			return cv1;
		}else{
			return null;
		}
	}
	
	private static void mergeSubSections(Section cv1Section, Section cv2Section){
		for (Section cv2SubSection : cv2Section.getSubSections()){
			boolean found = false;
			for (Section cv1SubSection : cv1Section.getSubSections()){
				if (cv1SubSection.getTitle().equals(cv2SubSection.getTitle())){
					found = true;
					mergeSubSections(cv1SubSection, cv2SubSection);
					mergeParagraphs(cv1SubSection, cv2SubSection);
					mergeBulletLists(cv1SubSection.getBulletListItemsArray(), cv2SubSection.getBulletListItemsArray());
					break;
				}
			}
			
			if (!found){
				cv1Section.addSubSection(cv2SubSection);
			}
		}
	}
	
	private static void mergeParagraphs(Section cv1Section, Section cv2Section){
		for (Paragraph cv2Paragraph : cv2Section.getParagraphsArray()){
			boolean found = false;
			for (Paragraph cv1Paragraph : cv1Section.getParagraphsArray()){
				if (cv1Paragraph.getContents().equals(cv2Paragraph.getContents())){
					found = true;
					break;
				}
			}
			
			if (!found){
				cv1Section.addParagraph(cv2Paragraph.getContents());
			}
		}
	}
	
	private static void mergeBulletLists(ArrayList<BulletListItem> cv1BulletListItemsArray, ArrayList<BulletListItem> cv2BulletListItemsArray){
		for (BulletListItem cv2BulletListItem : cv2BulletListItemsArray){
			boolean found = false;
			for (BulletListItem cv1BulletListItem : cv1BulletListItemsArray){
				if (cv1BulletListItem.getContentsWithDate().equals(cv2BulletListItem.getContentsWithDate())){
					found = true;
					mergeBulletLists(cv1BulletListItem.getSubBulletListItems(), cv2BulletListItem.getSubBulletListItems());
					break;
				}
			}
			
			if (!found){
				cv1BulletListItemsArray.add(cv2BulletListItem);
				Collections.sort(cv1BulletListItemsArray, new Comparator<BulletListItem>() {
			        @Override
			        public int compare(BulletListItem item1, BulletListItem item2){
			        	if (item1.getDate() == null){
			        		return 1;
			        	}
			        	if (item2.getDate() == null){
			        		return -1;
			        	}
			            return (item1.getDate()).compareTo(item2.getDate());
			        }
			    });
			}
		}
	}
	
	private static boolean checkCvTypeAndOwnerMatch(CV cv1, CV cv2){
		if (!cv1.getCvType().equals(cv2.getCvType())){
			JOptionPane.showMessageDialog(null, "Given cv's type doesn't match!" , "CV Compare", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (!cv1.getCvOwnerName().equals(cv2.getCvOwnerName())){
			JOptionPane.showMessageDialog(null, "Given cv's owner name doesn't match!" , "CV Compare", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (!cv1.getCvOwnerAddress().equals(cv2.getCvOwnerAddress())){
			JOptionPane.showMessageDialog(null, "Given cv's owner address doesn't match!", "CV Compare", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (!cv1.getCvOwnerHomeTelephone().equals(cv2.getCvOwnerHomeTelephone())){
			JOptionPane.showMessageDialog(null, "Given cv's owner home telephone doesn't match!", "CV Compare", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (!cv1.getCvOwnerMobileTelephone().equals(cv2.getCvOwnerMobileTelephone())){
			JOptionPane.showMessageDialog(null, "Given cv's owner mobile phone doesn't match!", "CV Compare", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (!cv1.getCvOwnerEmail().equals(cv2.getCvOwnerEmail())){
			JOptionPane.showMessageDialog(null, "Given cv's owner email doesn't match!", "CV Compare", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}
