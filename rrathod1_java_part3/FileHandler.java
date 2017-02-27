import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class FileHandler {

	String directoryPath = null;
	ArrayList<File> fileList = null;
	int fileNumber = 0;
	FileReader fileReader = null;
	BufferedReader bufferedReader = null;
	FileReader dictionaryReader = null;
	BufferedReader dictionaryBuffer = null;
	FileReader docReader = null;
	BufferedReader docBuffer = null;
	FileReader postingsReader = null;
	BufferedReader postingsBuffer = null;
	
	public FileHandler() {
		// TODO Auto-generated constructor stub
		File file = new File("result.txt");
		if(file.exists())
			file.delete();
	}
	public FileHandler(String directoryPathIn) {
		directoryPath = directoryPathIn;
		fileList = new ArrayList<>();
	}
	
	
	public String populateDictionary() {
		String line = null;
		try {
			if(dictionaryReader == null) {
				dictionaryReader = new FileReader("rrathod1_java_part2/dictionary.csv");
				dictionaryBuffer = new BufferedReader(dictionaryReader);
			}
			line = dictionaryBuffer.readLine();
			if(line == null) {
				dictionaryBuffer.close();
				dictionaryReader.close();
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("IO exception occurred");
			System.exit(1);
		}
		
		return line;
	}
	
	
	public String populateDocsTable() {
		String line = null;
		
		try {
			if(docReader == null) {
				docReader = new FileReader("rrathod1_java_part2/docsTable.csv");
				docBuffer = new BufferedReader(docReader);
			}
			line = docBuffer.readLine();
			
			if(line == null) {
				docBuffer.close();
				docReader.close();
			}
		} catch (IOException e) {
			System.err.println("IO exception occurred");
			System.exit(1);
		}
		
		return line;
	}
	
	public String populatePostingList() {
		String line = null;
		
		try {
			if(postingsReader == null) {
				postingsReader = new FileReader("rrathod1_java_part2/postings.csv");
				postingsBuffer = new BufferedReader(postingsReader);
			}
			line = postingsBuffer.readLine();
			
			if(line == null) {
				postingsBuffer.close();
				postingsReader.close();
			}
		} catch (IOException e) {
			System.err.println("IO exception occurred");
			System.exit(1);
		}
		
		return line;
	}
	
	public Output populateTotalWords(Output o) {
		String line = null;
		try {
			FileReader file = new FileReader("rrathod1_java_part2/total.txt");
			BufferedReader br = new BufferedReader(file);
			line = br.readLine();
			o.totalWords = Integer.parseInt(line);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("File not found");
			System.exit(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("IO exception occurred");
			System.exit(1);
		}
		return o;
	}
	
	public void createResultFile(StringBuffer sb) {
		try {
			FileWriter fw = new FileWriter("result.txt",true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(sb.toString());
			bw.close();
			fw.close();
		} catch (IOException e) {
			System.err.println("IO exception occurred");
			System.exit(1);
		}
	}
}
