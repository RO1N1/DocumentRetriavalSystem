public class DocInfo {

	int docID;
	String headline = null;
	int docLength=0;
	String snippet = null;
	String docPath = null;
	
	public DocInfo(String headlineIn, String snippetIn, String docPathIn) {
		if(headlineIn != null)
			headline = new String(headlineIn);
		if(snippetIn != null)
			snippet = new String(snippetIn);
		docPath = docPathIn;
	}
}
