package cv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BulletList {

	private ArrayList<BulletListItem> bulletListItemArray;
	
	
	public BulletList(){
		bulletListItemArray = new ArrayList<BulletListItem>();
	}
	
	public void addBulletListItem(String bulletListContents, String bulletListDate){
		bulletListItemArray.add(new BulletListItem(bulletListContents, bulletListDate));
		Collections.sort(bulletListItemArray, new Comparator<BulletListItem>() {
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
	
	public void addBulletListItem(BulletListItem bListItem){
		bulletListItemArray.add(bListItem);
	}
	
	public ArrayList<BulletListItem> getBulletListItemsArray(){
		 return bulletListItemArray;
	}
	
	public String getBulletListItems(){
		String chars = "";
		for (BulletListItem bulletListItem : bulletListItemArray){
			chars += bulletListItem.getContentsWithDate() + System.lineSeparator();
		}
		
		if (chars.equals("")){
			return null;
		}
		return chars.substring(0, chars.lastIndexOf(System.lineSeparator()));
	}
	
	public String getBulletListItemsAsText(){
		String chars = "";
		for (BulletListItem bulletListItem : bulletListItemArray){
			chars += bulletListItem.getContentsWithDateAsText("\t") + System.lineSeparator();
		}
		
		if (chars.equals("")){
			return null;
		}
		return chars.substring(0, chars.lastIndexOf(System.lineSeparator()));
	}
	
	public String getBulletListItemsAsLatex(){
		String latexHeader = "\\begin{itemize}[leftmargin=2.0cm]" + System.lineSeparator();
		
		String chars = "";
		for (BulletListItem bulletListItem : bulletListItemArray){
			chars += bulletListItem.getContentsWithDateAsLatex(null) + System.lineSeparator();
		}
		
		String latexFooter = "\\end{itemize}" + System.lineSeparator();
		
		if (chars.equals("")){
			return null;
		}
		return (latexHeader+chars+latexFooter).substring(0, (latexHeader+chars+latexFooter).lastIndexOf(System.lineSeparator()));
	}
}
