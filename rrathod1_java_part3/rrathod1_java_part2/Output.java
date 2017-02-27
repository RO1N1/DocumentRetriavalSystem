import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Output {
	
	public HashMap<String, Dictionary> dictionaryList = null;
	public HashMap<Integer, String> wordOrder = null;
	public HashMap<Integer, DocInfo> docInfo = null;
	public HashMap<String, Integer> offset = null;
	public ArrayList<Postings> postingList = null;
	public StringBuffer headline = null;
	public StringBuffer snippet = null;
	public int snippetLength = 0;
	public int totalWords = 0;
	int id=1;
	
	public Output() {
		dictionaryList = new HashMap<>();
		headline = new StringBuffer();
		snippet = new StringBuffer();
		docInfo = new HashMap<>();
		postingList = new ArrayList<>();
		offset = new HashMap<>();
		wordOrder = new HashMap<>();
	}
	
	public void addTerm(String tokens[], int docID) {
		for(String str:tokens) {
			if(str != null && !str.isEmpty()) {
				DocInfo doc = docInfo.get(docID);
				doc.docLength = doc.docLength + 1;
				docInfo.put(docID, doc);
				totalWords++;
					
				if (!dictionaryList.containsKey(str)) {
					Dictionary dictionary = new Dictionary();
					dictionary.indexTerm = str;
					dictionary.documentFrequency = 1;
					dictionary.termFrequency = 1;
					dictionary.lastDocID = docID;
					dictionary.documentList.put(docID, 1);
					dictionaryList.put(str, dictionary);
				} else {
					Dictionary dict = dictionaryList.get(str);
					dict.termFrequency++;
					if (docID != dict.lastDocID) {
						dict.documentFrequency++;
						dict.lastDocID = docID;
						dict.documentList.put(docID, 1);
					} else {
						int count = dict.documentList.get(docID);
						count++;
						dict.documentList.put(docID, count);
					}
					dictionaryList.put(str, dict);
				}
			}
		}
	}
	
	public void generatePostingList() {
		
		ArrayList<String> words = new ArrayList<>();
		for(Map.Entry<String, Dictionary> entry : dictionaryList.entrySet())
			words.add(entry.getKey());
		
		Collections.sort(words);
		for(int i=0; i<words.size(); i++) {
			wordOrder.put(id, words.get(i));
			id++;
		}
		
		ArrayList<Integer> list = new ArrayList<>();
		for(Map.Entry<Integer, String> entry : wordOrder.entrySet()) {
			list.add(entry.getKey());
		}
		
		Collections.sort(list);
		for(int i=0; i<list.size(); i++) {
			String term = wordOrder.get(list.get(i));
			Dictionary dict = dictionaryList.get(term);
			ArrayList<Integer> docList = new ArrayList<>();
			for(Map.Entry<Integer, Integer> entry : dict.documentList.entrySet())
				docList.add(entry.getKey());
			
			Collections.sort(docList);
			dict.pointer = postingList.size();
			for(int j=0; j<docList.size(); j++) {
				Postings postings = new Postings(docList.get(j), dict.documentList.get(docList.get(j)));
				postingList.add(postings);
			}
			dictionaryList.put(term, dict);
		}
	}
}
