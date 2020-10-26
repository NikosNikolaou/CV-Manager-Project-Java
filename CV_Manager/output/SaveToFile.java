package output;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class SaveToFile {
	
	public static void save(String chars, String fileName){
		try (PrintWriter out = new PrintWriter(fileName, "UTF-8")) {
            out.write(chars);
        } catch (FileNotFoundException e) {
			System.err.println("ReadFromFile.readData(): " + e.getMessage());
			System.exit(0);
		} catch (UnsupportedEncodingException e) {
			System.err.println("ReadFromFile.readData(): " + e.getMessage());
			System.exit(0);
		}
	}
}
