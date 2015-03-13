package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.*;

public abstract class CommandLine {

	protected Map<File, List<String>> result;
	protected Map<File, Integer> lineCount;
	Pattern pattern;
	protected Pattern subPattern;
	protected List<String> list;
		
	public void process(File directory, String fileSelectionPattern, boolean recursive) {
		
		pattern = Pattern.compile(fileSelectionPattern);
		
		File[] files = directory.listFiles();
		
		if (files == null)
			return;
		
		for (File file : files) {
			if (file.isFile()) {
				Matcher fileMatcher = pattern.matcher(file.getName());
				if (fileMatcher.matches()) {
					//now open the file and read it
					Scanner scanner;
					
					try {
						scanner = new Scanner(file);
					} catch (FileNotFoundException e) {
						return; //if file can't be opened, just return and try the next file
					}
					
					beginProcess(file);
					
					while (scanner.hasNextLine()) {						
						String currentLine = scanner.nextLine();
						processLine(currentLine);
						
					}
					
					endProcess();
					
					scanner.close();
				}
			}
		}
		
		if (recursive) {
			for (File file : files) {
				if (file.isDirectory()) {
					process(file, fileSelectionPattern, recursive);
				}
			}
		}
	}
	
	
	public abstract void beginProcess(File file);
	public abstract void processLine(String currentLine);
	public abstract void endProcess();
	
}
