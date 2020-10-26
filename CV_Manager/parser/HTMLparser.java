package parser;

import java.util.HashMap;
import javax.swing.JComboBox;


public class HTMLparser {
	
	public static String getUpdatedTemplateText(String currentText, String commentPoint, String textToBeAdded){
		String updatedText = currentText;
		
		String preEditPointHtml = updatedText.substring(0, updatedText.indexOf("<!--" + commentPoint + "-START-->")+("<!--" + commentPoint + "-START-->").length());
		String afterEditPointHtml = updatedText.substring(updatedText.indexOf("<!--" + commentPoint + "-END-->"));
		updatedText = preEditPointHtml + textToBeAdded + afterEditPointHtml;
		
		return updatedText;
	}
	
	public static String getUpdatedTemplateText(String currentText, String commentPoint, String textToBeAdded, String htmlCommand, boolean addHtmlToParagraph){
		String updatedText = currentText;
		
		if (!textToBeAdded.equals("")){
			String preEditPointHtml = updatedText.substring(0, updatedText.indexOf("<!--" + commentPoint + "-START-->")+("<!--" + commentPoint + "-START-->").length());
			String afterEditPointHtml = updatedText.substring(updatedText.indexOf("<!--" + commentPoint + "-END-->"));
			
			String textPaneLines[] = textToBeAdded.split(System.lineSeparator());
			
			String htmlToBeAdded = "";
			int start = 0;
			if (addHtmlToParagraph){
				start = 1;
				htmlToBeAdded += textPaneLines[0].trim() + System.lineSeparator();
			}
			for (int i=start; i<textPaneLines.length; i++){
				htmlToBeAdded += "<"+htmlCommand+">" + textPaneLines[i].trim() + "</"+htmlCommand+">" + System.lineSeparator();
			}
			
			updatedText = preEditPointHtml + htmlToBeAdded + afterEditPointHtml;
		}
		
		return updatedText;
	}
	
	public static String getUpdatedTemplateText(String currentText, JComboBox<String> skillsComboBox, HashMap<String, String> skillDetails){
		String updatedText = currentText;
		
		String htmlToBeAdded = "";
		for (int i=0; i<skillsComboBox.getItemCount(); i++){
			if (i==0){
				htmlToBeAdded += "<!--SKILL-START-->\n";
				htmlToBeAdded += "<b>3.1. SKILLS AND EXPERIENCE ON &lt;" + skillsComboBox.getItemAt(i).toString() + "&gt;</b>\n";
				htmlToBeAdded += "<!--SKILL-END-->\n";
			}else{
				htmlToBeAdded += "<div>\n";
				htmlToBeAdded += "<!--SKILL-START-->\n";
				htmlToBeAdded += "<b>3." + (i+1) + ". SKILLS AND EXPERIENCE ON &lt;" + skillsComboBox.getItemAt(i).toString() + "&gt;</b>\n";
				htmlToBeAdded += "<!--SKILL-END-->\n";
				htmlToBeAdded += "</div>\n";
			}
			
			if (!skillDetails.get(skillsComboBox.getItemAt(i)).equals("")){
				String skillLineDetails[] = skillDetails.get(skillsComboBox.getItemAt(i)).split(System.lineSeparator());
				
				htmlToBeAdded += "<ul style=\"padding-left: 50px;\">\n";
				htmlToBeAdded += "<!--SKILL-DETAILS-START-->\n";
				for (int k=0; k<skillLineDetails.length; k++){
					htmlToBeAdded += "<li>" + skillLineDetails[k] + "</li>\n";
				}
				htmlToBeAdded += "<!--SKILL-DETAILS-END-->\n";
				htmlToBeAdded += "</ul>\n";
			}
		}
		
		String preEditPointHtml = updatedText.substring(0, updatedText.indexOf("<!--SKILLS-AND-EXPERIENCE-START-->")+("<!--SKILLS-AND-EXPERIENCE-START-->").length());
		String afterEditPointHtml = updatedText.substring(updatedText.indexOf("<!--SKILLS-AND-EXPERIENCE-END-->"));
		updatedText = preEditPointHtml + htmlToBeAdded + afterEditPointHtml;
		
		return updatedText;
	}
	
	public static String getUpdatedTemplateText(String currentText, JComboBox<String> experienceComboBox, HashMap<String, String> experienceResponsibilities, HashMap<String, String> experienceResponsibilityAchivements){
		String updatedText = currentText;
		
		String htmlToBeAdded = "";
		for (int i=0; i<experienceComboBox.getItemCount(); i++){
			htmlToBeAdded += "<ul style=\"padding-left: 25px;\">\n";
			htmlToBeAdded += "<!--EXPERIENCE-START-->\n";
			htmlToBeAdded += "<li>" + experienceComboBox.getItemAt(i).toString() + "</li>\n";
			htmlToBeAdded += "<!--EXPERIENCE-END-->\n";
			htmlToBeAdded += "<ul style=\"padding-left: 25px;\" type=\"circle\">\n";
			htmlToBeAdded += "<!--EXPERIENCE-PARAGRAPH-START-->\n";
			htmlToBeAdded += "<li>" + experienceResponsibilities.get(experienceComboBox.getItemAt(i)).replace(System.lineSeparator(), System.lineSeparator()+"<br>") + "</li>\n";
			htmlToBeAdded += "<!--EXPERIENCE-PARAGRAPH-END-->\n";
			htmlToBeAdded += "<ul style=\"padding-left: 25px;\" type=\"square\">\n";
			
			String achievementsLines[] = experienceResponsibilityAchivements.get(experienceComboBox.getItemAt(i)).split(System.lineSeparator());
			htmlToBeAdded += "<!--EXPERIENCE-ACHIEVEMENTS-START-->\n";
			for (int k=0; k<achievementsLines.length; k++){
				htmlToBeAdded += "<li>" + achievementsLines[k] + "</li>\n";
			}
			htmlToBeAdded += "<!--EXPERIENCE-ACHIEVEMENTS-END-->\n";
			
			htmlToBeAdded += "</ul>\n";
			htmlToBeAdded += "</ul>\n";
			htmlToBeAdded += "</ul>\n";
		}
		
		String preEditPointHtml = updatedText.substring(0, updatedText.indexOf("<!--PROFESSIONAL-EXPERIENCE-START-->")+("<!--PROFESSIONAL-EXPERIENCE-START-->").length());
		String afterEditPointHtml = updatedText.substring(updatedText.indexOf("<!--PROFESSIONAL-EXPERIENCE-END-->"));
		updatedText = preEditPointHtml + htmlToBeAdded + afterEditPointHtml;
		
		return updatedText;
	}
}