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
	
	public FileHandler(String directoryPathIn) {
		directoryPath = directoryPathIn;
		fileList = new ArrayList<>();
	}
	
	public void getFileNames() {
		File file = new File(directoryPath);
		if(file.exists())
			recuresiveGet(file);
		else {
			System.err.println("Directory not found");
			System.exit(1);
		}
	}

	private void recuresiveGet(File file) {
		
		if(file.isFile())
			fileList.add(file.getAbsoluteFile());
		
		if(file.isDirectory()) {
			String[] list = file.list();
			for(String filename : list) {
				recuresiveGet(new File(file, filename));
			}
		}
	}
	
	public StringBuilder read(File file) {
		StringBuilder fileContent = new StringBuilder();
		try {
			if(file.isFile()) {
				
				fileReader = new FileReader(file);
				bufferedReader = new BufferedReader(fileReader);
				String line = null;
				
				while((line = bufferedReader.readLine()) != null) {
					fileContent.append(line);
					fileContent.append("\n");
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("IO exception occurred");
			System.exit(1);
		}
		
		return fileContent;
	}
	
	public void createDocsTable(Output output) {
		try {
			FileWriter fileWriter = new FileWriter("docsTable.csv");
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			StringBuffer sb = new StringBuffer();
			ArrayList<Integer> list = new ArrayList<>();
			
			for(Map.Entry<Integer, DocInfo> entry : output.docInfo.entrySet()) {
				list.add(entry.getKey());
			}
			Collections.sort(list);
			bufferedWriter.write("doc number,headline,doc length |D|,snippet,Doc path\n");
			for(int i=0; i<list.size(); i++) {
				sb.append(list.get(i)+",");
				DocInfo di = output.docInfo.get(list.get(i));
				sb.append(di.headline+",");
				sb.append(di.docLength+",");
				sb.append(di.snippet+",");
				sb.append(di.docPath+"\n");
				bufferedWriter.write(sb.toString());
				sb.setLength(0);
			}
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			System.err.println("IO exception occurred");
			System.exit(1);
		}
	}
	
	public void createPostingFile(Output output) {
		try {
			FileWriter fw = new FileWriter("postings.csv");
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write("docID,tf\n");
			for(int i=0; i<output.postingList.size(); i++) {
				Postings p = output.postingList.get(i);
				bw.write(p.docID+","+p.termFrequency+"\n");
			}
			
			bw.close();
			fw.close();
		} catch (IOException e) {
			System.err.println("IO exception occurred");
			System.exit(1);
		}
	}
	
	public void createDictionary(Output output) {
		FileWriter fw;
		try {
			fw = new FileWriter("dictionary.csv");
			BufferedWriter bw = new BufferedWriter(fw);
			ArrayList<Integer> list = new ArrayList<>();
			
			for(Map.Entry<Integer, String> entry : output.wordOrder.entrySet())
				list.add(entry.getKey());
			
			Collections.sort(list);
			
			bw.write("term,cf,df,offset\n");
			for(int i=0; i<list.size(); i++) {
				String term = output.wordOrder.get(list.get(i));
				Dictionary dict = output.dictionaryList.get(term);
				bw.write(dict.getIndexTerm()+","+dict.getTermFrequency()+","+dict.getDocumentFrequency()+","+dict.pointer+"\n");
			}
			
			bw.close();
			fw.close();
		} catch (IOException e) {
			System.err.println("IO exception occurred");
			System.exit(1);
		}
	}
	
	
	public void createTotal(Output output) {
		try {
			FileWriter fw= new FileWriter("total.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			int total = output.totalWords;
			bw.write(""+total);
			bw.close();
			fw.close();
		} catch (IOException e) {
			System.err.println("IO exception occurred");
			System.exit(1);
		}
	}
}