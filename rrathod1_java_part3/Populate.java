
public class Populate {
	
	public Dictionary populateDictionary(String line) {
		Dictionary dictionary = new Dictionary();
		String tokens[] = line.split(",");
		
		dictionary.setIndexTerm(tokens[0]);
		dictionary.termFrequency = Integer.parseInt(tokens[1]);
		dictionary.documentFrequency = Integer.parseInt(tokens[2]);
		dictionary.pointer = Integer.parseInt(tokens[3]);
		return dictionary;
	}
	
	public DocInfo populateDocInfo(String line) {
		DocInfo docInfo = new DocInfo();
		String tokens[] = line.split(",");
		docInfo.docID = Integer.parseInt(tokens[0]);
		docInfo.headline = tokens[1];
		docInfo.docLength = Integer.parseInt(tokens[2]);
		docInfo.snippet = tokens[3];
		docInfo.docPath = tokens[4];
		
		return docInfo;
	}
	
	public Postings populatePostingList(String line) {
		Postings p = new Postings();
		String tokens[] = line.split(",");
		p.docID = Integer.parseInt(tokens[0]);
		p.termFrequency = Integer.parseInt(tokens[1]);
		return p;
	}
}
