package repup.nlp.utilties;

import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.util.CoreMap;

public class SentenceGrammar {
	
	private String sentence;
	
	private GrammaticalStructure grammer;
	
	private CoreMap sentenceNlp;
	
	public SentenceGrammar(String sentence, GrammaticalStructure grammer, CoreMap sentenceNlp) {
		super();
		this.sentence = sentence;
		this.grammer = grammer;
		this.sentenceNlp = sentenceNlp;
	}
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	public GrammaticalStructure getGrammer() {
		return grammer;
	}
	public void setGrammer(GrammaticalStructure grammer) {
		this.grammer = grammer;
	}
	
	public CoreMap getSentenceNlp() {
		return sentenceNlp;
	}
	public void setSentenceNlp(CoreMap sentenceNlp) {
		this.sentenceNlp = sentenceNlp;
	}
	
	@Override
	public String toString() {
		return "SentenceGrammar [sentence=" + sentence + ", grammer=" + grammer
				+ ", sentenceNlp=" + sentenceNlp + "]";
	}
	
	
}
