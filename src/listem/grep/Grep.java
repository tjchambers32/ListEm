package listem.grep;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import listem.CommandLine;

public class Grep extends CommandLine implements IGrep {
	
	File currentFile;
	
	public Grep() {
		result = new TreeMap<File, List<String>>();
	}
	
	@Override
	public Map<File, List<String>> grep(File directory,
			String fileSelectionPattern, String substringSelectionPattern,
			boolean recursive) {

		result.clear();
		
		subPattern = Pattern.compile(substringSelectionPattern);
		
		process(directory, fileSelectionPattern, recursive);		
		
		return result;
	}

	@Override
	public void processLine(String currentLine) {
		Matcher textMatcher = subPattern.matcher(currentLine);
		if (textMatcher.find()) {
			list.add(currentLine);
		}
	}

	@Override
	public void beginProcess(File file) {
		list = new ArrayList<String>();
		currentFile = file;
	}

	@Override
	public void endProcess() {
		if (!list.isEmpty())
			result.put(currentFile, list);		
	}

}
