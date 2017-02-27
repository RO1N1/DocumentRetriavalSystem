import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class Output {

	public HashMap<Integer, Double> documentProbability = null;
	public int totalWords = 0;
	
	public Output() {
		// TODO Auto-generated constructor stub
		documentProbability = new HashMap<>();
	}

	public ArrayList<Integer> calculateProbability(DataStructures ds, String tokens[]) {
		
		ArrayList<Integer> resultDocIDs = new ArrayList<>();
		for(String tok: tokens) {
			for(int i=0; i<ds.dictionaryList.size(); i++) {
				Dictionary d = ds.dictionaryList.get(i);
				if(d.getIndexTerm().equals(tok)) {
					int startOffset = d.pointer;
					int endOffset = startOffset + d.documentFrequency;
					for(int j=startOffset; j<endOffset; j++) {
						Postings p = ds.postingsList.get(j);
						int docID = p.docID;
						if(!resultDocIDs.contains(docID)) {
							resultDocIDs.add(docID);
						}
					}
				}
			}
		}
		
		for(String tok: tokens) {
			if (tok != null) {
				for (int i = 0; i < resultDocIDs.size(); i++) {
					int cf = 0;
					int tf = 0;
					int docLength = 0;
					for (int j = 0; j < ds.dictionaryList.size(); j++) {
						Dictionary d = ds.dictionaryList.get(j);
						if (d.getIndexTerm().equals(tok)) {
							cf = d.getTermFrequency();
							int startOffset = d.pointer;
							int endOffset = startOffset + d.documentFrequency;
							for (int k = startOffset; k < endOffset; k++) {
								Postings p = ds.postingsList.get(k);
								if (p.docID == resultDocIDs.get(i)) {
									tf = p.termFrequency;
									break;
								}
							}
							break;
						}
					}
					for (int l = 0; l < ds.docList.size(); l++) {
						DocInfo di = ds.docList.get(l);
						if (di.docID == resultDocIDs.get(i)) {
							docLength = di.docLength;
						}
					}
					double value = (0.9 * ((double) tf / docLength) + 0.1 * ((double) cf / ds.totalWords));
					value = Math.log(value) / Math.log(2);
					if (!documentProbability.containsKey(resultDocIDs.get(i))) {
						documentProbability.put(resultDocIDs.get(i), value);
					} else {
						Double prob = documentProbability.get(resultDocIDs.get(i));
						value = value + prob;
						documentProbability.put(resultDocIDs.get(i), value);
					}
				}
			}
		}
		ArrayList<Integer> rankedDocuments = rankDocuments();
		return rankedDocuments;
	}
	
	public ArrayList<Integer> rankDocuments() {
		ArrayList<Double> list = new ArrayList<>();
		ArrayList<Integer> rankedDocuments = new ArrayList<>();
		
		for(Map.Entry<Integer, Double> entry : documentProbability.entrySet())
			if(!entry.getValue().toString().equals("-Infinity"))
				list.add(entry.getValue());
		
		Collections.sort(list);
		
		for(int i=list.size()-1; i>=0; i--) {
			Double prob = list.get(i);
			for(Map.Entry<Integer, Double> entry: documentProbability.entrySet())
				if(prob == entry.getValue()) {
					rankedDocuments.add(entry.getKey());
					if(rankedDocuments.size() == 5)
						break;
				}
			if(rankedDocuments.size() == 5)
				break;
		}
		
		return rankedDocuments;
	}
}