import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

	private ArrayList<String> stopWords = null;
	
	public Parser() {
		stopWords = new ArrayList<>();
		stopWords.add("and");
		stopWords.add("a");
		stopWords.add("the");
		stopWords.add("an");
		stopWords.add("by");
		stopWords.add("from");
		stopWords.add("for");
		stopWords.add("hence");
		stopWords.add("of");
		stopWords.add("with");
		stopWords.add("in");
		stopWords.add("within");
		stopWords.add("who");
		stopWords.add("when");
		stopWords.add("where");
		stopWords.add("why");
		stopWords.add("how");
		stopWords.add("whom");
		stopWords.add("have");
		stopWords.add("had");
		stopWords.add("has");
		stopWords.add("not");
		stopWords.add("for");
		stopWords.add("but");
		stopWords.add("do");
		stopWords.add("does");
		stopWords.add("done");
	}
	
	public StringBuilder replaceComma(StringBuilder fileContents){
		
		for(int i=0; i<fileContents.length(); i++) {
			if(fileContents.charAt(i) == ',')
				fileContents.setCharAt(i, ' ');
		}
		return fileContents;
	}
	
	public StringBuilder removeWhiteSpaces(StringBuilder fileContents) {
		String contents = fileContents.toString();
		contents = contents.replaceAll("\\s{2,}", " ");
		StringBuilder sb = new StringBuilder(contents);
		return sb;
	}
	
	public String getHeadline(StringBuilder str) {
		int headlineStart = 0;
		int headlineEnd = 0;
		String headline = null;
		String file = str.toString();
		
		String contents = new String(file);
		file = file.toLowerCase();
		if(file.contains("<headline>") && file.contains("</headline>")) {
			headlineStart = file.indexOf("<headline>");
			headlineEnd = file.indexOf("</headline>");
			
			headline = contents.substring(headlineStart, headlineEnd);
			headline = headline.replaceAll("\\<.*?>", "");
			headline = headline.replaceAll("\n", "");
			headline = headline.replaceAll(",", " ");
		}
		
		return headline;
	}
	
	public String getSnippet(StringBuilder str) {
		int wordCount = 0;
		int textStart = 0, textEnd = 0;
		String text = null;
		StringBuffer snippet = new StringBuffer();
		String file = str.toString();
		
		String contents = new String(file);
		file = file.toLowerCase();
		if(file.contains("<text>") && file.contains("</text>")) {
			textStart = file.indexOf("<text>");
			textEnd = file.indexOf("</text>");
			text = contents.substring(textStart, textEnd);
			text = text.replaceAll("\\<.*?>", "");
			text.replaceAll("\n", "");
			text = text.replaceAll(",", " ");		
			String tokens[] = text.split("\\s+");
			for(String s: tokens) {
				if(wordCount>40)
					break;
				if(!s.startsWith("<")) {
					snippet.append(s+" ");
					wordCount++;
				}
			}
		}
		
		return snippet.toString();
	}
	
	
	public String checkTags(String str) {
		String tokens = str.replaceAll("\\<.*?>", " ");
		return tokens;
	}
	
	public String[] toLowerCase(String str[]) {
		for(int i=0; i<str.length; i++) 
			if(str[i] != null)
				str[i] = str[i].toLowerCase();
		return str;
	}
	
	public String[] tokenize(String str) {
		String tokens[] = str.split("\\s+");
		return tokens;
	}
	
	
	public String[] trim(String str[]) {
		for(int i=0; i<str.length; i++) {
			str[i] = str[i].trim();
		}
		return str;
	}
	
	public String[] checkStopWords(String str[]) {
		
		for(int i=0; i<str.length; i++) {
			if(stopWords.contains(str[i]))
				str[i] = null;
		}
		return str;
	}
	
	
	public String[] checkSingleChar(String str[]) {
		for(int i=0; i<str.length; i++) {
			if(str[i] != null) {
				str[i] = str[i].trim();
				if(str[i].length() == 1 && Character.isLetter(str[i].charAt(0)))
					str[i] = null;
			}
		}
		return str;
	}
	
	public String[] removeQuotesAndBrackets(String str[]) {
		for(int i=0; i<str.length; i++) {
			if(str[i] != null) {
				if (str[i].startsWith("\"") || str[i].startsWith("\'") || str[i].startsWith("(")
						|| str[i].startsWith("[") || str[i].startsWith(")") || str[i].startsWith("]")) {
					str[i] = str[i].substring(1);
				}

				if (str[i].endsWith("\"") || str[i].endsWith("\'") || str[i].endsWith(")") || str[i].endsWith("]") || str[i].startsWith("(") 
						|| str[i].startsWith("[")) {
					str[i] = str[i].substring(0, str[i].length() - 1);
				}
			}
		}
		
		return str;
	}
	
	
	public String[] removePunctuation(String str[]) {
		for(int i=0; i<str.length; i++) {
			if(str[i] != null)
				if(str[i].endsWith(",") || str[i].endsWith(".") || str[i].endsWith("?") || str[i].endsWith(":") || str[i].endsWith(";") || str[i].endsWith("!"))
				str[i] = str[i].substring(0, str[i].length()-1);
		}
		
		return str;
	}
	
	
	public String[] removeApostrophe(String str[]) {
		for(int i=0; i<str.length; i++) {
			if(str[i] != null) {
				if(str[i].startsWith("'") && str[i].length()==1)
					str[i] = null;
				else if(str[i].length() > 1) {
					if (str[i].endsWith("'") && str[i].charAt(str[i].length() - 2) == 's')
						str[i] = str[i].substring(0, str[i].length() - 1);

					else if (str[i].endsWith("s") && str[i].charAt(str[i].length() - 2) == '\'')
						str[i] = str[i].substring(0, str[i].length() - 2) + str[i].charAt(str[i].length() - 1);
				}
			}
		}
		
		return str;
	}
	
	
	public String[] checkStemming(String tokens[]) {
		
		for(int i=0; i<tokens.length; i++) {
			if(tokens[i] != null) {
				if (tokens[i].endsWith("ies") && !tokens[i].endsWith("eies") && !tokens[i].endsWith("aies"))
					tokens[i] = tokens[i].substring(0, tokens[i].length() - 3) + 'y';

				else if (tokens[i].endsWith("es") && !tokens[i].endsWith("aes") && !tokens[i].endsWith("ees")
						&& !tokens[i].endsWith("oes"))
					tokens[i] = tokens[i].substring(0, tokens[i].length() - 2) + 'e';

				else if (tokens[i].endsWith("s") && !tokens[i].endsWith("us") && !tokens[i].endsWith("ss"))
					tokens[i] = tokens[i].substring(0, tokens[i].length() - 1);
			}
		}
		return tokens;
	}
	
	public String[] removeSpecialCharacters(String tokens[]) {
		
		for(int i=0; i<tokens.length; i++) {
			if(tokens[i] != null) {
				Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
				Matcher match = pattern.matcher(tokens[i]);
				while(match.find()) {
					String str = match.group();
					tokens[i] = tokens[i].replaceAll("\\"+str, "");
				}
			}
		}
		
		return tokens;
	}
}
