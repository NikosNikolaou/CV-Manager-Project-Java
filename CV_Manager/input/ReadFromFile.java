package input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadFromFile {

	public static String read(String fileName, boolean readFromResources){
		String data = "";
		
		try{
			InputStream inputStream = null;
			if (readFromResources){
				inputStream = ReadFromFile.class.getClassLoader().getResourceAsStream(fileName);
			}else{
				inputStream = new FileInputStream(new File(fileName));
			}
			
			BufferedReader reader = new BufferedReader(new InputStreamReader((inputStream), "UTF-8"));
			
			String line;
			while ((line = reader.readLine()) != null){
				data += line + System.lineSeparator();
			}
		}catch (Exception e){
			System.err.println("ReadFromFile.readData(): " + e.getMessage());
			System.exit(0);
		}
		
		return data;
	}
}