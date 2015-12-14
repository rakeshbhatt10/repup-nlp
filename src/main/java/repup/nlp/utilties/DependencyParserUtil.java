package repup.nlp.utilties;

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class DependencyParserUtil {
	
	private StanfordCoreNLP pipeline;
	
	public DependencyParserUtil(StanfordCoreNLP pipeline) {
		this.pipeline = pipeline;
		
	}

	public ArrayList<SentenceGrammar> doDependencyParsing(String text) {

		Annotation annotation = pipeline.process(text);
		List<CoreMap> sentences = annotation
				.get(CoreAnnotations.SentencesAnnotation.class);
		ArrayList<SentenceGrammar> grammaticalStructures = new ArrayList<SentenceGrammar>();
		for (CoreMap sentence : sentences) {
			grammaticalStructures.add(new SentenceGrammar(sentence.toString(), getDepndencies(sentence), sentence));
		}

		return grammaticalStructures;
	}

	private GrammaticalStructure getDepndencies(CoreMap sentence) {

		Tree tree = sentence.get(TreeAnnotation.class);
	
		TreebankLanguagePack tlp = new PennTreebankLanguagePack();
		GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
		GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);

		return gs;
	}
}
