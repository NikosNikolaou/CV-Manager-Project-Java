package input;

import cv.CV;
import parser.LatexToCVobject;
import parser.TextToCVobject;

public class LoadCVFromFile {

	public static CV load(String cvFile){
		
		String cvAsText = ReadFromFile.read(cvFile, false);
		
		switch (cvFile.substring(cvFile.lastIndexOf("."))){
			case ".txt":
				return TextToCVobject.convert(cvAsText);
			case ".tex":
				return LatexToCVobject.convert(cvAsText);
			default:
				return null;
		}
	}
}
