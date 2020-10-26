package output;

import cv.CV;


public class TemplateFileSaver {

	public static void saveCVtoFile(CV cvTemplate, String selectedFileName, String selectedExtension){
		String chars = "";
		switch (selectedExtension){
			case "txt":
				chars = cvTemplate.toPlainText();
				break;
			case "tex":
				chars = cvTemplate.toLatex();
				break;
		}

		String file = selectedFileName + "." + selectedExtension;
		SaveToFile.save(chars, file);
	}
}