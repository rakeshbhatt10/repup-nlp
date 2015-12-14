package repup.nlp.services;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.TypedDependency;

public class ReviewSentimentServiceBackup {
	
	private String reviewText;
	private Logger logger = Logger.getLogger(this.getClass());
	
	public ReviewSentimentServiceBackup(String reviewText) {
		this.reviewText = reviewText;
		this.reviewText = reviewText.replaceAll("but|and", ". ");
	}

	public void analyzeReviewSentiment() {
		
		Properties props = new Properties();
		logger.info("Modified Text: " + reviewText);
		props.setProperty("annotators",
				"tokenize, ssplit, pos, lemma, parse, sentiment");
		ArrayList<GrammaticalStructure> gramaticalStructures = doDependencyParsing(reviewText);
		HashMap<String, ArrayList<IndexedWord>> dependencyStructure = new HashMap<>();
		
		
		for (GrammaticalStructure gs : gramaticalStructures) {
			// gs.toString()
			List<TypedDependency> depdencyList = (List<TypedDependency>) gs
					.typedDependencies();
			for (TypedDependency typedDependency : depdencyList) {
				String gramaticalRel = typedDependency.reln().getShortName();
				if (dependencyStructure.containsKey(gramaticalRel)) {
					ArrayList<IndexedWord> indexWordList = dependencyStructure
							.get(gramaticalRel);
					indexWordList.add(typedDependency.dep());
					indexWordList.add(typedDependency.gov());
					dependencyStructure.put(gramaticalRel, indexWordList);
				} else {
					ArrayList<IndexedWord> indexWordList = new ArrayList<>();
					indexWordList.add(typedDependency.dep());
					indexWordList.add(typedDependency.gov());
					dependencyStructure.put(gramaticalRel, indexWordList);
				}
			}
		}

		for (Entry<String, ArrayList<IndexedWord>> wordSet : dependencyStructure
				.entrySet()) {
			String key = wordSet.getKey();
			if (key.equalsIgnoreCase("nsubj") | key.equalsIgnoreCase("nmod")
					| key.equalsIgnoreCase("amod")) {
				logger.info("______________________" + wordSet.getKey()
						+ "_________________________");
				for (IndexedWord indexWord : wordSet.getValue()) {
					logger.info(" Value: " + indexWord.value()
							+ " Index: " + indexWord.index());
				}
			}
		}

	}

	public ArrayList<GrammaticalStructure> doDependencyParsing(String text) {
		
		String modelPath = DependencyParser.DEFAULT_MODEL;
		String taggerPath = "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger";
		
		logger.info("Parsing Text: " + text);
		MaxentTagger tagger = new MaxentTagger(taggerPath);
		//tagger.getTag(index)
		DependencyParser parser = DependencyParser.loadFromModelFile(modelPath);
		ArrayList<GrammaticalStructure> grammaticalStructures = new ArrayList<GrammaticalStructure>();
		DocumentPreprocessor tokenizer = new DocumentPreprocessor(
				new StringReader(text));
		for (List<HasWord> sentence : tokenizer) {
			List<TaggedWord> tagged = tagger.tagSentence(sentence);
			GrammaticalStructure gs = parser.predict(tagged);
			grammaticalStructures.add(gs);
		}
		return grammaticalStructures;
	}
	
	public static void main(String[] args) {
		
		String reviewText = "We conducted a conference here for two days, ";
    			/*+ "It was a great experience conducting our sessions here. "
    			+ "everything was smooth and great help from staff. "
    			+ "Would like to thank Ram and Sumit for addressing to our needs on real time "
    			+ "basis and sehhttp://marketplace.eclipse.org/marketplace-client-intro?mpc_install=2177882ttp://marketplace.eclipse.org/marketplace-client-intro?mpc_install=2177882rhttp://marketplace.eclipse.org/marketplace-client-intro?mpc_install=2177882ving a great hospitality, Also food was nice with a good spread. "
    			+ "Kudos to chef http://marketplace.eclipse.org/marketplace-client-intro?mpc_install=2177882and team.";*/
		
		ReviewSentimentServiceBackup reviewSentimentService = new ReviewSentimentServiceBackup(reviewText);
		reviewSentimentService.analyzeReviewSentiment();
	}
}
