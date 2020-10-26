package parser;

import cv.BulletListItem;
import cv.CV;
import cv.Section;

public class LatexToCVobject {

	public static CV convert(String cvAsLatex){
		CV cv = new CV();
		
		cvAsLatex = cvAsLatex.substring(cvAsLatex.indexOf("\\section"));
		cvAsLatex = cvAsLatex.replace("\\\\", "");
		String cvAsLatexLines[] = cvAsLatex.split(System.lineSeparator());

		//System.out.println(cvAsLatex);
		for (int i=0; i<cvAsLatexLines.length; i++){
			if (cvAsLatexLines[i].equals(""))
				continue;
			
			if (cvAsLatexLines[i].equals("\\section*{1. GENERAL INFORMATION}")){
				String name = null;
				String address = null;
				String homeTelephone = null;
				String mobileTelephone = null;
				String email = null;
				
				if (cvAsLatexLines[++i].contains("\\tab Name: {\\color{red} ")){
					name = cvAsLatexLines[i].substring("\\tab Name: {\\color{red} ".length(), cvAsLatexLines[i].lastIndexOf("}"));
				}
				if (cvAsLatexLines[++i].contains("\\tab Address: {\\color{red} ")){
					address = cvAsLatexLines[i].substring("\\tab Address: {\\color{red} ".length(), cvAsLatexLines[i].lastIndexOf("}"));
				}
				if (!cvAsLatexLines[++i].equals("\\tab Telephone")){
					return null;
				}
				if (cvAsLatexLines[++i].contains("\\tab Home: {\\color{red} ")){
					homeTelephone = cvAsLatexLines[i].substring("\\tab \\tab Home: {\\color{red} ".length(), cvAsLatexLines[i].lastIndexOf("}"));
				}
				if (cvAsLatexLines[++i].contains("\\tab Mobile: {\\color{red} ")){
					mobileTelephone = cvAsLatexLines[i].substring("\\tab \\tab Mobile: {\\color{red} ".length(), cvAsLatexLines[i].lastIndexOf("}"));
				}
				if (cvAsLatexLines[++i].contains("\\tab Email: {\\color{red} ")){
					email = cvAsLatexLines[i].substring("\\tab Email: {\\color{red} ".length(), cvAsLatexLines[i].lastIndexOf("}"));
				}
				
				if (name==null || address==null || homeTelephone==null || mobileTelephone==null || email==null){
					return null;
				}else{
					cv.setGeneralInformation(name, address, homeTelephone, mobileTelephone, email);
				}
			}
			else if (cvAsLatexLines[i].equals("\\section*{2. PROFESSIONAL PROFILE}")){
				int sectionNum = cv.addSection("2. PROFESSIONAL PROFILE");
				
				String profProfileParagraph = "";
				while(!cvAsLatexLines[++i].equals("\\section*{3. SKILLS AND EXPERIENCE}") || cvAsLatexLines[i].equals("\\section*{3. CORE STRENGTHS}")){
					if (i==cvAsLatexLines.length-1){
						return null;
					}
					if (cvAsLatexLines[i].equals("")){
						continue;
					}
					
					profProfileParagraph += cvAsLatexLines[i].substring("\\tab ".length()) + System.lineSeparator();
				}
				--i;
				
				cv.addParagraph(sectionNum, profProfileParagraph.substring(0, profProfileParagraph.lastIndexOf(System.lineSeparator())));
			}
			else if (cvAsLatexLines[i].equals("\\section*{3. SKILLS AND EXPERIENCE}") || cvAsLatexLines[i].equals("\\section*{3. CORE STRENGTHS}")){
				if (cvAsLatexLines[i].equals("\\section*{3. SKILLS AND EXPERIENCE}")){
					int sectionNum = cv.addSection("3. SKILLS AND EXPERIENCE");
					while(!(cvAsLatexLines[++i].equals("\\section*{4. PROFESSIONAL EXPERIENCE}") || cvAsLatexLines[i].equals("\\section*{4. CAREER SUMMARY}"))){
						if (i==cvAsLatexLines.length-1){
							return null;
						}
						if (cvAsLatexLines[i].equals("")){
							continue;
						}
						
						if (cvAsLatexLines[i].contains("SKILLS AND EXPERIENCE ON")){
							Section subSection = new Section(cvAsLatexLines[i].substring("\\subsubsection*{\\tab ".length(), cvAsLatexLines[i].lastIndexOf("}")).replace("$", ""));
							while(true){
								++i;
								if (cvAsLatexLines[i].equals("") || cvAsLatexLines[i].contains("\\begin") || cvAsLatexLines[i].contains("\\end")){
									continue;
								}
								
								if (cvAsLatexLines[i].contains("\\item ")){
									subSection.addBulletListItem(cvAsLatexLines[i].substring("\\item ".length()), null);
								}else{
									break;
								}
							}
							--i;
							
							cv.addSubSection(sectionNum, subSection);
						}else{
							return null;
						}
					}
					--i;
				}else{
					int sectionNum = cv.addSection("3. CORE STRENGTHS");
					
					String coreStrengthParagraph = "";
					while(!(cvAsLatexLines[++i].equals("\\section*{4. PROFESSIONAL EXPERIENCE}") || cvAsLatexLines[i].equals("\\section*{4. CAREER SUMMARY}"))){
						if (i==cvAsLatexLines.length-1){
							return null;
						}
						if (cvAsLatexLines[i].equals("")){
							continue;
						}
						
						coreStrengthParagraph += cvAsLatexLines[i].substring("\\tab ".length()) + System.lineSeparator();
					}
					--i;
					
					cv.addParagraph(sectionNum, coreStrengthParagraph.substring(0, coreStrengthParagraph.lastIndexOf(System.lineSeparator())));
				}
			}
			else if (cvAsLatexLines[i].equals("\\section*{4. PROFESSIONAL EXPERIENCE}") || cvAsLatexLines[i].equals("\\section*{4. CAREER SUMMARY}")){
				if (cvAsLatexLines[i].equals("\\section*{4. PROFESSIONAL EXPERIENCE}")){
					int sectionNum = cv.addSection("4. PROFESSIONAL EXPERIENCE");
					if (cv.getSectionsArray().get(sectionNum-2).getTitle().equals("3. CORE STRENGTHS")){
						cv.setCvType("CHRONOLOGICAL");
					}
					else if (cv.getSectionsArray().get(sectionNum-2).getTitle().equals("3. SKILLS AND EXPERIENCE")){
						cv.setCvType("COMBINED");
					}
					else{
						return null;
					}
					
					while (!cvAsLatexLines[++i].equals("\\section*{5. EDUCATION AND TRAINING}")){
						if (i==cvAsLatexLines.length-1){
							return null;
						}
						if (cvAsLatexLines[i].equals("") || cvAsLatexLines[i].contains("\\begin") || cvAsLatexLines[i].contains("\\end")){
							continue;
						}
						
						if (!cvAsLatexLines[i].contains("\\item ")){
							return null;
						}else{
							String bulletListContents = cvAsLatexLines[i].substring("\\item ".length(), cvAsLatexLines[i].lastIndexOf(","));
							String date = cvAsLatexLines[i].substring(cvAsLatexLines[i].lastIndexOf(",")+1);
							BulletListItem b1 = new BulletListItem(bulletListContents, date);

							if (!cvAsLatexLines[++i].equals("\\begin{list}{$\\circ$}{}")){
								return null;
							}else{
								String responsibilities = cvAsLatexLines[++i].substring("\\item ".length()) + System.lineSeparator();
								
								while(!cvAsLatexLines[++i].equals("\\begin{list}{$\\diamond$}{}")){
									responsibilities += cvAsLatexLines[i] + System.lineSeparator();
								}
								--i;
								BulletListItem b2 = b1.addSubBulletListItem(responsibilities.substring(0, responsibilities.lastIndexOf(System.lineSeparator())), null);
								
								while((cvAsLatexLines[++i].indexOf(",") >= cvAsLatexLines[i].lastIndexOf(",") && cvAsLatexLines[i].indexOf("/") >= cvAsLatexLines[i].lastIndexOf("/")) && (!cvAsLatexLines[i].equals("\\section*{5. EDUCATION AND TRAINING}"))){
									if (i==cvAsLatexLines.length-1){
										return null;
									}
									if (cvAsLatexLines[i].equals("") || cvAsLatexLines[i].contains("\\begin") || cvAsLatexLines[i].contains("\\end")){
										continue;
									}
									
									b2.addSubBulletListItem(cvAsLatexLines[i].substring("\\item ".length()), null);
									
								}
								--i;
								
								cv.addBulletListItem(sectionNum, b1);
							}
						}
					}
					--i;
				}else{
					int sectionNum = cv.addSection("4. CAREER SUMMARY");
					if (cv.getSectionsArray().get(sectionNum-2).getTitle().equals("3. SKILLS AND EXPERIENCE")){
						cv.setCvType("FUNCTIONAL");
					}
					else{
						return null;
					}
					
					while(!cvAsLatexLines[++i].equals("\\section*{5. EDUCATION AND TRAINING}")){
						if (i==cvAsLatexLines.length-1){
							return null;
						}
						if (cvAsLatexLines[i].equals("") || cvAsLatexLines[i].contains("\\begin") || cvAsLatexLines[i].contains("\\end")){
							continue;
						}
						
						if (!cvAsLatexLines[i].contains("\\item ")){
							return null;
						}else{
							String bulletListContents = cvAsLatexLines[i].substring("\\item ".length(), cvAsLatexLines[i].lastIndexOf(","));
							String date = cvAsLatexLines[i].substring(cvAsLatexLines[i].lastIndexOf(",")+1);
							cv.addBulletListItem(sectionNum, bulletListContents, date);
						}
					}
					--i;
				}
			}
			else if (cvAsLatexLines[i].equals("\\section*{5. EDUCATION AND TRAINING}")){
				int sectionNum = cv.addSection("5. EDUCATION AND TRAINING");
				
				while(!cvAsLatexLines[++i].equals("\\section*{6. FURTHER COURSES}")){
					if (i==cvAsLatexLines.length-1){
						return null;
					}
					if (cvAsLatexLines[i].equals("") || cvAsLatexLines[i].contains("\\begin") || cvAsLatexLines[i].contains("\\end")){
						continue;
					}
					
					if (!cvAsLatexLines[i].contains("\\item ")){
						return null;
					}else{
						String bulletListContents = cvAsLatexLines[i].substring("\\item ".length(), cvAsLatexLines[i].lastIndexOf(","));
						String date = cvAsLatexLines[i].substring(cvAsLatexLines[i].lastIndexOf(",")+1);
						cv.addBulletListItem(sectionNum, bulletListContents, date);
					}
				}
				--i;
			}
			else if (cvAsLatexLines[i].equals("\\section*{6. FURTHER COURSES}")){
				int sectionNum = cv.addSection("6. FURTHER COURSES");
				
				while(!cvAsLatexLines[++i].equals("\\section*{7. ADDITIONAL INFORMATION}")){
					if (i==cvAsLatexLines.length-1){
						return null;
					}
					if (cvAsLatexLines[i].equals("") || cvAsLatexLines[i].contains("\\begin") || cvAsLatexLines[i].contains("\\end")){
						continue;
					}
					
					if (!cvAsLatexLines[i].contains("\\item ")){
						return null;
					}else{
						String bulletListContents = cvAsLatexLines[i].substring("\\item ".length(), cvAsLatexLines[i].lastIndexOf(","));
						String date = cvAsLatexLines[i].substring(cvAsLatexLines[i].lastIndexOf(",")+1);
						cv.addBulletListItem(sectionNum, bulletListContents, date);
					}
				}
				--i;
			}
			else if (cvAsLatexLines[i].equals("\\section*{7. ADDITIONAL INFORMATION}")){
				int sectionNum = cv.addSection("7. ADDITIONAL INFORMATION");
				
				String addInfoParagraph = "";
				while(!cvAsLatexLines[++i].equals("\\section*{8. INTERESTS}")){
					if (i==cvAsLatexLines.length-1){
						return null;
					}
					if (cvAsLatexLines[i].equals("")){
						continue;
					}
					
					addInfoParagraph += cvAsLatexLines[i].substring("\\tab ".length()) + System.lineSeparator();
				}
				--i;
				
				cv.addParagraph(sectionNum, addInfoParagraph.substring(0, addInfoParagraph.lastIndexOf(System.lineSeparator())));
			}
			else if (cvAsLatexLines[i].equals("\\section*{8. INTERESTS}")){
				int sectionNum = cv.addSection("8. INTERESTS");
				
				String interestsParagraph = "";
				while((++i) != cvAsLatexLines.length){
					if (cvAsLatexLines[i].equals("") || cvAsLatexLines[i].contains("\\end")){
						continue;
					}
					
					interestsParagraph += cvAsLatexLines[i].substring("\\tab ".length()) + System.lineSeparator();
				}
				--i;
				
				cv.addParagraph(sectionNum, interestsParagraph.substring(0, interestsParagraph.lastIndexOf(System.lineSeparator())));
			}else{
				return null;
			}
		}
		
		//System.out.println(cv.toPlainText());
		return cv;
	}
}
