
public class assignmentP2 {

	public static void main(String[] args) {
		String directoryPath = null;
		directoryPath = args[0];
		String str = null;
		String tokens[] = null;
		String headline = null;
		String snippet = null;
		
		if(args.length != 1) {
			System.err.println("Please specify the directory path");
			System.exit(1);
		}
		FileHandler fileHandler = new FileHandler(directoryPath);
		Parser parser = new Parser();
		Output output = new Output();
		
		
		fileHandler.getFileNames();
		
		for(int i=0; i<fileHandler.fileList.size(); i++) {
			if(fileHandler.fileList.get(i).isFile()) {
				StringBuilder fileContents = new StringBuilder();
				fileContents = fileHandler.read(fileHandler.fileList.get(i));
					fileContents = parser.removeWhiteSpaces(fileContents);
					headline = parser.getHeadline(fileContents);
					snippet = parser.getSnippet(fileContents);
					DocInfo docInfo = new DocInfo(headline, snippet, fileHandler.fileList.get(i).getAbsolutePath());
					docInfo.docID = i + 1;
					output.docInfo.put(i + 1, docInfo);

					str = parser.checkTags(fileContents.toString());
					tokens = parser.tokenize(str);
					tokens = parser.toLowerCase(tokens);
					tokens = parser.trim(tokens);
					tokens = parser.removeSpecialCharacters(tokens);
					tokens = parser.checkStopWords(tokens);
					tokens = parser.removeQuotesAndBrackets(tokens);
					tokens = parser.removePunctuation(tokens);
					tokens = parser.removeApostrophe(tokens);
					tokens = parser.checkStemming(tokens);
					tokens = parser.checkSingleChar(tokens);
					tokens = parser.checkStopWords(tokens);
					tokens = parser.checkStemming(tokens);
					tokens = parser.checkSingleChar(tokens);
					output.addTerm(tokens, i + 1);
			}
		}
		
		output.generatePostingList();
		fileHandler.createDocsTable(output);
		fileHandler.createPostingFile(output);
		fileHandler.createDictionary(output);
		fileHandler.createTotal(output);
	}
}