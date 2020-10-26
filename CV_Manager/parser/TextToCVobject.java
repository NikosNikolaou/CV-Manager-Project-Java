package parser;

import cv.BulletListItem;
import cv.CV;
import cv.Section;

public class TextToCVobject {

	public static CV convert(String cvAsText){
		CV cv = new CV();
		
		cvAsText = cvAsText.replace("\t", "");
		String cvAsTextLines[] = cvAsText.split(System.lineSeparator());

		//System.out.println(cvAsText);
		for (int i=0; i<cvAsTextLines.length; i++){
			if (cvAsTextLines[i].equals(""))
				continue;
			
			if (cvAsTextLines[i].equals("1. GENERAL INFORMATION")){
				String name = null;
				String address = null;
				String homeTelephone = null;
				String mobileTelephone = null;
				String email = null;
				
				if (cvAsTextLines[++i].contains("Name: ")){
					name = cvAsTextLines[i].substring("Name: ".length(), cvAsTextLines[i].length());
				}
				if (cvAsTextLines[++i].contains("Address: ")){
					address = cvAsTextLines[i].substring("Address: ".length(), cvAsTextLines[i].length());
				}
				if (!cvAsTextLines[++i].equals("Telephone")){
					return null;
				}
				if (cvAsTextLines[++i].contains("Home: ")){
					homeTelephone = cvAsTextLines[i].substring("Home: ".length(), cvAsTextLines[i].length());
				}
				if (cvAsTextLines[++i].contains("Mobile: ")){
					mobileTelephone = cvAsTextLines[i].substring("Mobile: ".length(), cvAsTextLines[i].length());
				}
				if (cvAsTextLines[++i].contains("Email: ")){
					email = cvAsTextLines[i].substring("Email: ".length(), cvAsTextLines[i].length());
				}
				
				if (name==null || address==null || homeTelephone==null || mobileTelephone==null || email==null){
					return null;
				}else{
					cv.setGeneralInformation(name, address, homeTelephone, mobileTelephone, email);
				}
			}
			else if (cvAsTextLines[i].equals("2. PROFESSIONAL PROFILE")){
				int sectionNum = cv.addSection("2. PROFESSIONAL PROFILE");
				
				String profProfileParagraph = "";
				while(!cvAsTextLines[++i].equals("3. SKILLS AND EXPERIENCE") || cvAsTextLines[i].equals("3. CORE STRENGTHS")){
					if (i==cvAsTextLines.length-1){
						return null;
					}
					if (cvAsTextLines[i].equals("")){
						continue;
					}
					
					profProfileParagraph += cvAsTextLines[i] + System.lineSeparator();
				}
				--i;
				
				cv.addParagraph(sectionNum, profProfileParagraph.substring(0, profProfileParagraph.lastIndexOf(System.lineSeparator())));
			}
			else if (cvAsTextLines[i].equals("3. SKILLS AND EXPERIENCE") || cvAsTextLines[i].equals("3. CORE STRENGTHS")){
				if (cvAsTextLines[i].equals("3. SKILLS AND EXPERIENCE")){
					int sectionNum = cv.addSection("3. SKILLS AND EXPERIENCE");
					while(!(cvAsTextLines[++i].equals("4. PROFESSIONAL EXPERIENCE") || cvAsTextLines[i].equals("4. CAREER SUMMARY"))){
						if (i==cvAsTextLines.length-1){
							return null;
						}
						if (cvAsTextLines[i].equals("")){
							continue;
						}
						
						if (cvAsTextLines[i].contains("SKILLS AND EXPERIENCE ON")){
							Section subSection = new Section(cvAsTextLines[i]);
							while(true){
								++i;
								if (cvAsTextLines[i].equals("")){
									continue;
								}
								
								if (cvAsTextLines[i].substring(0, 2).equals("* ")){
									subSection.addBulletListItem(cvAsTextLines[i].substring(2), null);
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
					while(!(cvAsTextLines[++i].equals("4. PROFESSIONAL EXPERIENCE") || cvAsTextLines[i].equals("4. CAREER SUMMARY"))){
						if (i==cvAsTextLines.length-1){
							return null;
						}
						if (cvAsTextLines[i].equals("")){
							continue;
						}
						
						coreStrengthParagraph += cvAsTextLines[i] + System.lineSeparator();
					}
					--i;
					
					cv.addParagraph(sectionNum, coreStrengthParagraph.substring(0, coreStrengthParagraph.lastIndexOf(System.lineSeparator())));
				}
			}
			else if (cvAsTextLines[i].equals("4. PROFESSIONAL EXPERIENCE") || cvAsTextLines[i].equals("4. CAREER SUMMARY")){
				if (cvAsTextLines[i].equals("4. PROFESSIONAL EXPERIENCE")){
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
					
					while (!cvAsTextLines[++i].equals("5. EDUCATION AND TRAINING")){
						if (i==cvAsTextLines.length-1){
							return null;
						}
						if (cvAsTextLines[i].equals("")){
							continue;
						}
						
						if (!cvAsTextLines[i].substring(0, 2).equals("* ")){
							return null;
						}else{
							String bulletListContents = cvAsTextLines[i].substring(2, cvAsTextLines[i].lastIndexOf(","));
							String date = cvAsTextLines[i].substring(cvAsTextLines[i].lastIndexOf(",")+1);
							BulletListItem b1 = new BulletListItem(bulletListContents, date);
							

							if (!cvAsTextLines[++i].substring(0, 2).equals("* ")){
								return null;
							}else{
								String responsibilities = cvAsTextLines[i].substring(2) + System.lineSeparator();
								
								while(cvAsTextLines[++i].substring(0, 2).equals("  ")){
									responsibilities += cvAsTextLines[i].substring(2) + System.lineSeparator();
								}
								--i;
								BulletListItem b2 = b1.addSubBulletListItem(responsibilities.substring(0, responsibilities.lastIndexOf(System.lineSeparator())), null);
								
								while((cvAsTextLines[++i].indexOf(",") >= cvAsTextLines[i].lastIndexOf(",") && cvAsTextLines[i].indexOf("/") >= cvAsTextLines[i].lastIndexOf("/")) && (!cvAsTextLines[i].equals("5. EDUCATION AND TRAINING"))){
									if (i==cvAsTextLines.length-1){
										return null;
									}
									if (cvAsTextLines[i].equals("")){
										continue;
									}
									
									b2.addSubBulletListItem(cvAsTextLines[i].substring(2), null);
									
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
					
					while(!cvAsTextLines[++i].equals("5. EDUCATION AND TRAINING")){
						if (i==cvAsTextLines.length-1){
							return null;
						}
						if (cvAsTextLines[i].equals("")){
							continue;
						}
						
						if (!cvAsTextLines[i].substring(0, 2).equals("* ")){
							return null;
						}else{
							String bulletListContents = cvAsTextLines[i].substring(2, cvAsTextLines[i].lastIndexOf(","));
							String date = cvAsTextLines[i].substring(cvAsTextLines[i].lastIndexOf(",")+1);
							cv.addBulletListItem(sectionNum, bulletListContents, date);
						}
					}
					--i;
				}
			}
			else if (cvAsTextLines[i].equals("5. EDUCATION AND TRAINING")){
				int sectionNum = cv.addSection("5. EDUCATION AND TRAINING");
				
				while(!cvAsTextLines[++i].equals("6. FURTHER COURSES")){
					if (i==cvAsTextLines.length-1){
						return null;
					}
					if (cvAsTextLines[i].equals("")){
						continue;
					}
					
					if (!cvAsTextLines[i].substring(0, 2).equals("* ")){
						return null;
					}else{
						String bulletListContents = cvAsTextLines[i].substring(2, cvAsTextLines[i].lastIndexOf(","));
						String date = cvAsTextLines[i].substring(cvAsTextLines[i].lastIndexOf(",")+1);
						cv.addBulletListItem(sectionNum, bulletListContents, date);
					}
				}
				--i;
			}
			else if (cvAsTextLines[i].equals("6. FURTHER COURSES")){
				int sectionNum = cv.addSection("6. FURTHER COURSES");
				
				while(!cvAsTextLines[++i].equals("7. ADDITIONAL INFORMATION")){
					if (i==cvAsTextLines.length-1){
						return null;
					}
					if (cvAsTextLines[i].equals("")){
						continue;
					}
					
					if (!cvAsTextLines[i].substring(0, 2).equals("* ")){
						return null;
					}else{
						String bulletListContents = cvAsTextLines[i].substring(2, cvAsTextLines[i].lastIndexOf(","));
						String date = cvAsTextLines[i].substring(cvAsTextLines[i].lastIndexOf(",")+1);
						cv.addBulletListItem(sectionNum, bulletListContents, date);
					}
				}
				--i;
			}
			else if (cvAsTextLines[i].equals("7. ADDITIONAL INFORMATION")){
				int sectionNum = cv.addSection("7. ADDITIONAL INFORMATION");
				
				String addInfoParagraph = "";
				while(!cvAsTextLines[++i].equals("8. INTERESTS")){
					if (i==cvAsTextLines.length-1){
						return null;
					}
					if (cvAsTextLines[i].equals("")){
						continue;
					}
					
					addInfoParagraph += cvAsTextLines[i] + System.lineSeparator();
				}
				--i;
				
				cv.addParagraph(sectionNum, addInfoParagraph.substring(0, addInfoParagraph.lastIndexOf(System.lineSeparator())));
			}
			else if (cvAsTextLines[i].equals("8. INTERESTS")){
				int sectionNum = cv.addSection("8. INTERESTS");
				
				String interestsParagraph = "";
				while((++i) != cvAsTextLines.length){
					if (cvAsTextLines[i].equals("")){
						continue;
					}
					
					interestsParagraph += cvAsTextLines[i] + System.lineSeparator();
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