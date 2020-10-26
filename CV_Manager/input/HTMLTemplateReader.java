package input;

public class HTMLTemplateReader {

	public static String readTemplateFile(String templateType) {
		String templateFile = null;
		switch (templateType){
			case "COMBINED":
				templateFile = "templates/combined_template.html";
				break;
			case "FUNCTIONAL":
				templateFile = "templates/functional_template.html";
				break;
			case "CHRONOLOGICAL":
				templateFile = "templates/chronological_template.html";
				break;
			default:
				System.err.println("HTMLTemplateReader-Class, readTemplateFile-Static-Method must be called given the templateType which is one of the following: [ COMBINED | FUNCTIONAL | CHRONOLOGICAL ]");
				System.exit(0);
		}
		
		return ReadFromFile.read(templateFile, true);
	}
}