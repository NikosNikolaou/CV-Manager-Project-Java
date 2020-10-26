package parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;


public class DataValidator {
	
	public static boolean checkDataFormat(String dataType, String dataLines, String sentenceFormat, int datePosition){
		if (!dataLines.equals("")){
			String data[] = dataLines.split(System.lineSeparator());
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			for (int i=0; i<data.length; i++){
				if (data[i].equals("")){
					JOptionPane.showMessageDialog(null, "You have an error in " + dataType + " field (in line" + (i+1) + ").\nPlease remove the empty lines!", "Input Error", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				
				String lineParameters[] = data[i].split(",");
				
				if ((lineParameters.length==4 && datePosition==3) || (lineParameters.length==3 && datePosition==2)){
			        try {
			        	String newDateStr = lineParameters[datePosition].trim();
						dateFormat.parse(newDateStr);
						
			        	if (!checkDate(newDateStr)){
			        		JOptionPane.showMessageDialog(null, "You have an error in " + dataType + " field (in line" + (i+1) + ").\nUse this format for Date: dd/MM/yyyy\nWhere dd->[01, 31], MM->[01, 12], yyyy->[1970, " + getCurrentYear() + "]", "Input Error", JOptionPane.WARNING_MESSAGE);
							return false;
			        	}
					} catch (ParseException e) {
						JOptionPane.showMessageDialog(null, "You have an error in " + dataType + " field (in line" + (i+1) + ").\nUse this format for Date: dd/MM/yyyy", "Input Error", JOptionPane.WARNING_MESSAGE);
						return false;
					}
				}else{
					JOptionPane.showMessageDialog(null, "You have an error in " + dataType + " field (in line" + (i+1) + ").\nEach line must be in the following format:\n" + sentenceFormat, "Input Error", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}
		}
		
		return true;
	}
	
	public static boolean checkDate(String dateStr){
		int dd = Integer.parseInt(dateStr.split("/")[0]);
		int MM = Integer.parseInt(dateStr.split("/")[1]);
		int yyyy = Integer.parseInt(dateStr.split("/")[2]);
		
		if ((dd>=1 && dd<=31) && (MM>=1 && MM <=12) && (yyyy>=1970 && yyyy<=getCurrentYear())){
			return true;
		}
		return false;
	}
	
	public static int getCurrentYear(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String dateStr = dateFormat.format(date);
		int currentYear = Integer.parseInt(dateStr.split("/")[2]);
		
		return currentYear;
	}
}
