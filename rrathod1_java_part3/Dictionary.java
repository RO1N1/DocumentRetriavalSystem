import java.util.HashMap;

public class Dictionary {
	String indexTerm = null;
	int termFrequency = 0;
	int documentFrequency = 0;
	int lastDocID = 0;
	int pointer = 0;
	HashMap<Integer, Integer> documentList = new HashMap<>();
	
	
	public Dictionary() {
		
	}
	public void setIndexTerm(String str) {
		indexTerm = str;
	}
	
	public String getIndexTerm() {
		return indexTerm;
	}
	
	public void setTermFrequency() {
		termFrequency++;
	}
	
	public int getTermFrequency() {
		return termFrequency;
	}
	
	public void setDocumentFrequency(int docID) {
		documentFrequency++;
	}
	
	public int getDocumentFrequency() {
		return documentFrequency;
	}
}
