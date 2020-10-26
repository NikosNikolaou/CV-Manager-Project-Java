package cv;

import java.util.ArrayList;

public class Section {

	private String title;
	private ArrayList<Paragraph> paragraphsArray;
	private BulletList bulletList;
	private ArrayList<Section> subSections;
	
	
	public Section(String sectionTitle){
		title = sectionTitle;
		
		subSections = new ArrayList<Section>();
		paragraphsArray = new ArrayList<Paragraph>();
		bulletList = new BulletList();
	}
	
	public void addParagraph(String paragraphContents){
		paragraphsArray.add(new Paragraph(paragraphContents));
	}
	
	public void addBulletListItem(String bulletListContents, String bulletListDate){
		bulletList.addBulletListItem(bulletListContents, bulletListDate);
	}
	
	public void addBulletListItem(BulletListItem bListItem){
		bulletList.addBulletListItem(bListItem);
	}
	
	public void addSubSection(Section subSection){
		subSections.add(subSection);
	}
	
	public ArrayList<Section> getSubSections(){
		return subSections;
	}
	
	public ArrayList<Paragraph> getParagraphsArray(){
		return paragraphsArray;
	}
	
	public ArrayList<BulletListItem> getBulletListItemsArray(){
		 return bulletList.getBulletListItemsArray();
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getSectionParagraphs(){
		String chars = "";
		for (Paragraph paragraph : paragraphsArray){
			chars += paragraph.getContents() + System.lineSeparator();
		}
		if (chars.equals("")){
			return null;
		}
		return chars.substring(0, chars.lastIndexOf(System.lineSeparator()));
	}
	
	public String getSectionBulletListItems(){
		if (bulletList.getBulletListItems() != null){
			return bulletList.getBulletListItems();
		}else{
			return null;
		}
	}
	
	public String getSectionParagraphsAsText(){
		String chars = "";
		for (Paragraph paragraph : paragraphsArray){
			chars += "\t" + paragraph.getContents().replace(System.lineSeparator(), System.lineSeparator()+"\t") + System.lineSeparator();
		}
		if (chars.equals("")){
			return null;
		}
		return chars;
	}
	
	public String getSectionParagraphsAsLatex(){
		String chars = "";
		for (Paragraph paragraph : paragraphsArray){
			chars += "\\tab " + paragraph.getContents().replace(System.lineSeparator(), "\\\\"+System.lineSeparator()+"\\tab ") + "\\\\"+System.lineSeparator();
		}
		if (chars.equals("")){
			return null;
		}
		return chars;
	}
	
	public String getSectionBulletListItemsAsText(){
		if (bulletList.getBulletListItemsAsText() != null){
			return bulletList.getBulletListItemsAsText() + System.lineSeparator();
		}else{
			return null;
		}
	}
	
	public String getSectionBulletListItemsAsLatex(){
		if (bulletList.getBulletListItemsAsLatex() != null){
			return bulletList.getBulletListItemsAsLatex() + System.lineSeparator();
		}else{
			return null;
		}
	}
}
