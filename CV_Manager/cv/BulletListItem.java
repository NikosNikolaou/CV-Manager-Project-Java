package cv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BulletListItem {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private Date date;
	private String contents;
	private ArrayList<BulletListItem> subBulletListItems;
	
	
	public BulletListItem(String bulletListContents, String bulletListDate){
		if (bulletListDate != null){
			try {
				date = dateFormat.parse(bulletListDate);
			} catch (ParseException e) {}
		}else{
			date = null;
		}
		contents = bulletListContents.trim();
		
		subBulletListItems = new ArrayList<BulletListItem>();
	}
	
	public BulletListItem addSubBulletListItem(String bulletListContents, String bulletListDate){
		BulletListItem newSubBulletListItem = new BulletListItem (bulletListContents, bulletListDate);
		subBulletListItems.add(newSubBulletListItem);
		
		return newSubBulletListItem;
	}
	
	public ArrayList<BulletListItem> getSubBulletListItems(){
		return subBulletListItems;
	}
	
	public String getContentsWithDate(){
		if (date==null){
			return contents;
		}else{
			return contents + ", " + dateFormated();
		}
	}
	
	public String getContentsWithDateAsText(String tabsNeeded){
		String chars = "";
		
		if (date==null){
			chars += tabsNeeded+"* " + contents.replace(System.lineSeparator(), System.lineSeparator()+tabsNeeded+"  ") + System.lineSeparator();
		}else{
			chars += tabsNeeded+"* " + contents.replace(System.lineSeparator(), System.lineSeparator()+tabsNeeded+"  ") + ", " + dateFormated() + System.lineSeparator();
		}
		for (BulletListItem subBulletListItem : subBulletListItems){
			chars += subBulletListItem.getContentsWithDateAsText(tabsNeeded+"\t") + System.lineSeparator();
		}
		
		return chars.substring(0, chars.lastIndexOf(System.lineSeparator()));
	}
	
	public String getContentsWithDateAsLatex(String listType){
		String chars = "";
		
		if (listType!=null){
			chars += "\\begin{list}{$\\" + listType + "$}{}" + System.lineSeparator();
		}
		
		if (date==null){
			chars += "\\item " + contents.replace(System.lineSeparator(), "\\\\"+System.lineSeparator()) + System.lineSeparator();
		}else{
			chars += "\\item " + contents.replace(System.lineSeparator(), "\\\\"+System.lineSeparator()) + ", " + dateFormated() + System.lineSeparator();
		}
		
		for (BulletListItem subBulletListItem : subBulletListItems){
			if (listType == null){
				chars += subBulletListItem.getContentsWithDateAsLatex("circ") + System.lineSeparator();
			}
			else if (listType.equals("circ")){
				chars += subBulletListItem.getContentsWithDateAsLatex("diamond") + System.lineSeparator();
			}
		}
		
		if (listType!=null){
			chars += "\\end{list}" + System.lineSeparator();
		}

		return chars.substring(0, chars.lastIndexOf(System.lineSeparator()));
	}
	
	public Date getDate(){
		return date;
	}
	
	public String getContents(){
		return contents;
	}
	
	private String dateFormated(){
		if (date != null){
			return dateFormat.format(date);
		}
		
		return null;
	}
}
