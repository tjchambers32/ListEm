package listem.linecount;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import listem.CommandLine;

public class LineCounter extends CommandLine implements ILineCounter{

	File currentFile;
	int fileLineCount;
	public LineCounter() {
		lineCount = new TreeMap<File, Integer>();
		fileLineCount = 0;
	}
	
	@Override
	public Map<File, Integer> countLines(File directory,
			String fileSelectionPattern, boolean recursive) {
		
		lineCount.clear();
		
		process(directory, fileSelectionPattern, recursive);
		
		return lineCount;
	}

	@Override
	public void processLine(String currentLine) {
		fileLineCount++;
	}

	@Override
	public void beginProcess(File file) {
		fileLineCount = 0;
		currentFile = file;
	}

	@Override
	public void endProcess() {
		lineCount.put(currentFile, fileLineCount);		
	}
}
