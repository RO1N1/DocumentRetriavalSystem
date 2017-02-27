import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class assignmentP3 {
	public static void main(String[] args) {
		String str = null;
		String tokens[] = null;
		String headline = null;
		String snippet = null;
		String line = null;
		
		FileHandler fileHandler = new FileHandler();
		Parser parser = new Parser();
		Output output = new Output();
		
		Populate populate = new Populate();
		DataStructures dataStructures = new DataStructures();
		
		line = fileHandler.populateDictionary();
		while((line = fileHandler.populateDictionary()) != null) {
			Dictionary dictionary = populate.populateDictionary(line);
			dataStructures.dictionaryList.add(dictionary);
		}
		
		line = fileHandler.populateDocsTable();
		while((line = fileHandler.populateDocsTable()) != null) {
			DocInfo docInfo = populate.populateDocInfo(line);
			dataStructures.docList.add(docInfo);
		}
		
		line = fileHandler.populatePostingList();
		while((line = fileHandler.populatePostingList()) != null) {
			Postings postings = populate.populatePostingList(line);
			dataStructures.postingsList.add(postings);
		}
		output = fileHandler.populateTotalWords(output);
		
		dataStructures.totalWords = output.totalWords;
		while(true) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter Query:");
			String input = scanner.nextLine();
			if(input.equals("EXIT"))
				System.exit(0);
			
			StringBuilder sb = new StringBuilder(input);
			sb = parser.removeWhiteSpaces(sb);
			String tok[] = parser.tokenize(sb.toString());
			tok = parser.toLowerCase(tok);
			tok = parser.trim(tok);
			tok = parser.removeSpecialCharacters(tok);
			tok = parser.checkStopWords(tok);
			tok = parser.removeQuotesAndBrackets(tok);
			tok = parser.removePunctuation(tok);
			tok = parser.removeApostrophe(tok);
			tok = parser.checkStemming(tok);
			tok = parser.checkSingleChar(tok);
			tok = parser.checkStopWords(tok);
			tok = parser.checkStemming(tok);
			tok = parser.checkSingleChar(tok);
	
			ArrayList<Integer> rankedDocuments = output.calculateProbability(dataStructures, tok);
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("The query is: " + input + "\n");
			if(rankedDocuments.size()==0) {
				stringBuffer.append("NO RESULTS\n\n");
			}
			else {
				for (int i = 0; i < rankedDocuments.size(); i++) {
					int docID = rankedDocuments.get(i);
					for (int j = 0; j < dataStructures.docList.size(); j++) {
						DocInfo di = dataStructures.docList.get(j);
						if (docID == di.docID) {
							stringBuffer.append(di.headline + "\n");
							stringBuffer.append(di.docPath + "\n");
							stringBuffer.append("Computed probablity: " + output.documentProbability.get(docID) + "\n");
							stringBuffer.append(di.snippet.substring(1) + "\n\n");
						}
					}
				}
			}
			fileHandler.createResultFile(stringBuffer);
			stringBuffer.setLength(0);
			output.documentProbability.clear();
		}
	}
}
