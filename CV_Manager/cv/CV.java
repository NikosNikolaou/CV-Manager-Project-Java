package cv;

import java.util.ArrayList;

public class CV {
	
	private String name;
	private String address;
	private String homeTelephone;
	private String mobileTelephone;
	private String email;
	private String cvType;
	private ArrayList<Section> sectionsArray;
	
	
	public CV(){
		name = "";
		address = "";
		homeTelephone = "";
		mobileTelephone = "";
		email = "";
		cvType = null;
		
		sectionsArray = new ArrayList<Section>();
	}
	
	public void setCvType(String cvType){
		this.cvType = cvType;
	}
	
	public String getCvType(){
		return cvType;
	}
	
	public void setGeneralInformation(String name, String address, String homeTelephone, String mobileTelephone, String email){
		this.name = name;
		this.address = address;
		this.homeTelephone = homeTelephone;
		this.mobileTelephone = mobileTelephone;
		this.email = email;
	}
	
	public int addSection(String sectionTitle){
		sectionsArray.add(new Section(sectionTitle));
		return sectionsArray.size();
	}
	
	public void addSubSection(int sectionNumber, Section subSection){
		sectionsArray.get(sectionNumber-1).addSubSection(subSection);
	}
	
	public void addParagraph(int sectionNumber, String paragraphContents){
		sectionsArray.get(sectionNumber-1).addParagraph(paragraphContents);
	}
	
	public void addBulletListItem(int sectionNumber, String bulletListContents, String bulletListDate){
		sectionsArray.get(sectionNumber-1).addBulletListItem(bulletListContents, bulletListDate);
	}
	
	public void addBulletListItem(int sectionNumber, BulletListItem bListItem){
		sectionsArray.get(sectionNumber-1).addBulletListItem(bListItem);
	}
	
	public ArrayList<Section> getSectionsArray(){
		return sectionsArray;
	}
	
	public String getCvOwnerName(){
		return name;
	}
	
	public String getCvOwnerAddress(){
		return address;
	}
	
	public String getCvOwnerHomeTelephone(){
		return homeTelephone;
	}
	
	public String getCvOwnerMobileTelephone(){
		return mobileTelephone;
	}
	
	public String getCvOwnerEmail(){
		return email;
	}
	
	public String getGeneralInformation(){
		return name + "|" + address + "|" + homeTelephone + "|" + mobileTelephone + "|" + email;
	}
	
	private String getGeneralInformationAsText(){
		String genInfo = "1. GENERAL INFORMATION" + System.lineSeparator();
		
		genInfo += "\tName: " + name + System.lineSeparator();
		genInfo += "\tAddress: " + address + System.lineSeparator();
		genInfo += "\tTelephone" + System.lineSeparator();
		genInfo += "\t\tHome: " + homeTelephone + System.lineSeparator();
		genInfo += "\t\tMobile: " + mobileTelephone + System.lineSeparator();
		genInfo += "\tEmail: " + email + System.lineSeparator();
		
		return genInfo;
	}
	
	private String getGeneralInformationAsLatex(){
		String genInfo = "\\section*{1. GENERAL INFORMATION}" + System.lineSeparator();
		
		genInfo += "\\tab Name: {\\color{red} " + name + "}\\\\" + System.lineSeparator();
		genInfo += "\\tab Address: {\\color{red} " + address + "}\\\\" + System.lineSeparator();
		genInfo += "\\tab Telephone\\\\" + System.lineSeparator();
		genInfo += "\\tab \\tab Home: {\\color{red} " + homeTelephone + "}\\\\" + System.lineSeparator();
		genInfo += "\\tab \\tab Mobile: {\\color{red} " + mobileTelephone + "}\\\\" + System.lineSeparator();
		genInfo += "\\tab Email: {\\color{red} " + email + "}\\\\" + System.lineSeparator();
		
		return genInfo;
	}
	
	public String toPlainText(){
		String chars = getGeneralInformationAsText() + System.lineSeparator();
		for (Section section : sectionsArray){
			chars += section.getTitle() + System.lineSeparator();
			if (section.getSectionParagraphsAsText() != null){
				chars += section.getSectionParagraphsAsText() + System.lineSeparator();
			}
			if (section.getSectionBulletListItemsAsText() != null){
				chars += section.getSectionBulletListItemsAsText() + System.lineSeparator();
			}
			ArrayList<Section> subSections = section.getSubSections();
			for (Section subSection : subSections){
				chars += "\t" + subSection.getTitle() + System.lineSeparator();
				if (subSection.getSectionParagraphsAsText() != null){
					chars += subSection.getSectionParagraphsAsText().replace("\t", "\t\t") + System.lineSeparator();
				}
				if (subSection.getSectionBulletListItemsAsText() != null){
					chars += subSection.getSectionBulletListItemsAsText().replace("\t", "\t\t") + System.lineSeparator();
				}
			}
		}
		return chars.substring(0, chars.lastIndexOf(System.lineSeparator()));
	}
	
	public String toLatex(){
		String chars = "\\documentclass{article}" + System.lineSeparator() +
				"\\usepackage[margin=1in]{geometry}" + System.lineSeparator() +
				"\\usepackage{enumitem}" + System.lineSeparator() +
				"\\usepackage{color}" + System.lineSeparator() +
				"\\newcommand\\tab[1][1cm]{\\hspace*{#1}}" + System.lineSeparator() +
				"\\renewcommand{\\baselinestretch}{1.2}" + System.lineSeparator() +
				"\\begin{document}" + System.lineSeparator() +
				"\\pagenumbering{gobble}" + System.lineSeparator() + System.lineSeparator();
		
		chars += getGeneralInformationAsLatex() + System.lineSeparator();
		
		for (Section section : sectionsArray){
			chars += "\\section*{" + section.getTitle() + "}" + System.lineSeparator();
			if (section.getSectionParagraphsAsLatex() != null){
				chars += section.getSectionParagraphsAsLatex() + System.lineSeparator();
			}
			if (section.getSectionBulletListItemsAsLatex() != null){
				chars += section.getSectionBulletListItemsAsLatex() + System.lineSeparator();
			}
			ArrayList<Section> subSections = section.getSubSections();
			for (Section subSection : subSections){
				chars += "\\subsubsection*{\\tab " + subSection.getTitle().replace("<", "$<$").replace(">", "$>$") + "}" + System.lineSeparator();
				if (subSection.getSectionParagraphsAsLatex() != null){
					chars += subSection.getSectionParagraphsAsLatex() + System.lineSeparator();
				}
				if (subSection.getSectionBulletListItemsAsLatex() != null){
					chars += subSection.getSectionBulletListItemsAsLatex() + System.lineSeparator();
				}
			}
		}
		
		chars += "\\end{document}" + System.lineSeparator();
		return chars.substring(0, chars.lastIndexOf(System.lineSeparator()));
	}
}
