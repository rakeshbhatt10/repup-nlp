package repup.nlp.services;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.stream.IntStream;

import org.apache.log4j.Logger;

import repup.co.model.Dep;
import repup.co.model.Dependency;
import repup.co.model.Gov;
import repup.co.model.Pharase;
import repup.co.model.Review;
import repup.co.model.ReviewData;
import repup.co.model.SentenceData;
import repup.nlp.utilties.DependencyParserUtil;
import repup.nlp.utilties.SentenceGrammar;
import repup.nlp.utilties.SentimentAnalyzerUtils;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.TypedDependency;

public class ReviewSentimentService {

	private Logger logger = Logger.getLogger(this.getClass());
	private Properties props;
	private StanfordCoreNLP pipeline;
	private SentimentAnalyzerUtils sentimentCalc;
	private DependencyParserUtil dependencyCalc;
	private ArrayList<String> reviewList;
	
	public ReviewSentimentService(ArrayList<String> reviewList) {
		
		props = new Properties();
		
		props.setProperty("annotators",
				"tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");
		pipeline = new StanfordCoreNLP(props);
		sentimentCalc = new SentimentAnalyzerUtils(pipeline);
		dependencyCalc = new DependencyParserUtil(pipeline);
		this.reviewList = reviewList;
	}
	
	public ArrayList<Review> getReviewSentiment() {
		
		ArrayList<Review> reviews = new ArrayList<Review>();
		for(String review:reviewList) {
			logger.info("Analzing Review: " + review);
			reviews.add(analyzeReviewSentiment(review));
		}
		
		logger.info("Review Analysis is complete");
		return reviews;
	}
	
	private  Review analyzeReviewSentiment(String reviewText) {

		Review review = new Review();
		review.setReview(reviewText);
		ArrayList<ReviewData> reviewDataList = new ArrayList<ReviewData>();

		//List<String> wordList = createWordListFromReview();
		
		//logger.info("Word List: "+ wordList);
		// Calculating gramatical struture
		ArrayList<SentenceGrammar> gramaticalStructures = dependencyCalc.doDependencyParsing(reviewText);
		
		for (SentenceGrammar gs : gramaticalStructures) {
			
			//create review data object
			ReviewData reviewData = new ReviewData();
			String sentenceText = gs.getSentence();
			sentenceText = Character.toUpperCase(sentenceText.charAt(0)) + sentenceText.substring(1);
			reviewData.setSentence(sentenceText);
			reviewData.setSentiment(sentimentCalc.getSentiment(sentenceText));
			ArrayList<SentenceData> dataList = new ArrayList<SentenceData>();
			List<String> wordList = createWordListFromSentence(gs.getSentence());
			logger.info("Calculating Typed Dependencies for sentence: "+ gs.getSentence());
			logger.info("Word list created for sentence: "+ wordList);
			List<TypedDependency> depdencyList = (List<TypedDependency>) gs.getGrammer()
					.typedDependenciesCCprocessed(GrammaticalStructure.Extras.MAXIMAL);

			logger.info("Calculated dependency list " + depdencyList);
			HashMap<String, ArrayList<TypedDependency>> dependencyStructure = new HashMap<>();
			for (TypedDependency typedDependency : depdencyList) {
				
				String gramaticalRel = typedDependency.reln().getShortName();
				
				if (gramaticalRel.equals("nsubj")
						|| gramaticalRel.equals("nmod")
						|| gramaticalRel.equals("amod") || gramaticalRel.equals("nsubjpass")) {
					if (dependencyStructure.containsKey(gramaticalRel)) {
						ArrayList<TypedDependency> depList = dependencyStructure
								.get(gramaticalRel);
						depList.add(typedDependency);
						dependencyStructure.put(gramaticalRel, depList);
					} else {
						ArrayList<TypedDependency> depList = new ArrayList<>();
						depList.add(typedDependency);
						dependencyStructure.put(gramaticalRel, depList);
					}
				}
			}

			for (Entry<String, ArrayList<TypedDependency>> wordSet : dependencyStructure
					.entrySet()) {
				String key = wordSet.getKey();
				logger.info("Calculating depdencyList for " + wordSet.getKey());
//				List<Dependency> dependencyList = new ArrayList<Dependency>();
				List<Pharase> phraseList = new ArrayList<Pharase>();
				SentenceData sentenceData = new SentenceData();
				sentenceData.setRel(key);
				for (TypedDependency typedDep : wordSet.getValue()) {
					
					IndexedWord tdep = typedDep.dep();
					IndexedWord tgov = typedDep.gov();
					//logger.info("Creating Category");
					/*Dependency dependency = createTypedDependency(tdep,tgov);
					dependencyList.add(dependency);*/
					logger.info("Creating Phraselist");
					Pharase phrase = createPhraseList(wordList, tdep, tgov);
					phraseList.add(phrase);
				}
			
				sentenceData.setPharase(phraseList);
				dataList.add(sentenceData);
			}
			reviewData.setData(dataList);
			reviewDataList.add(reviewData);
		}
		review.setReviewData(reviewDataList);
		logger.info("Review Analysis Created: " + review);
		return review;
	}

	private Pharase createPhraseList(List<String> wordList, IndexedWord tdep,
			IndexedWord tgov) {
		
		Pharase phrase = new Pharase();
		
		List<Integer> indexes = new ArrayList<Integer>();
		indexes.add(Math.min(tdep.index(), tgov.index())-1);
		indexes.add(Math.max(tdep.index(), tgov.index())-1);
		
		phrase.setIndex(indexes);
		String keyword = "";
		if(tdep.index()>tgov.index()) {
			keyword = tgov.value() +" "+ tdep.value();
		} else {
			keyword = tdep.value() +" "+ tgov.value();
		}
		phrase.setKeyword(keyword);
		logger.info("Creating Dev index: "+tdep.index()+" Range: "+tgov.index());
		int[] ranges = IntStream
				.rangeClosed(
						Math.min(tdep.index(), tgov
								.index()),
						Math.max(tdep.index(), tgov
								.index())).toArray();
		logger.info("Total range length : " + ranges.length);
		String phraseSentence = "";
		for (int range : ranges) {
			if (phraseSentence.equals("")) {
				
				phraseSentence = wordList.get(range - 1);
				logger.info("Phrase sentence: "+(range-1)+" Adding word "+ wordList.get(range-1));
			} else {
				phraseSentence = phraseSentence + " "
						+ wordList.get(range - 1);
				logger.info("Phrase sentence: "+(range-1)+" Adding word " + wordList.get(range-1));
			}
		}
		logger.info("Created Phrase:" + phraseSentence);
		phraseSentence = Character.toUpperCase(phraseSentence.charAt(0)) + phraseSentence.substring(1);
		phrase.setValue(phraseSentence);
		phrase.setSentiment(sentimentCalc.getSentiment(phraseSentence));
		return phrase;
	}

	private Dependency createTypedDependency(IndexedWord tdep,IndexedWord tgov) {
		
		Dep dep = new Dep();
		dep.setIndex(tdep.index());
		dep.setValue(tdep.value());
		
		Gov gov = new Gov();
		gov.setIndex(tgov.index());
		gov.setValue(tgov.value());
		
		Dependency dependency = new Dependency();
		dependency.setDep(dep);
		dependency.setGov(gov);
		return dependency;
	}

	
	
	private List<String> createWordListFromSentence(String sentence) {
	
		PTBTokenizer<CoreLabel> ptbt = new PTBTokenizer<>(new StringReader(
				sentence), new CoreLabelTokenFactory(), "");
		List<String> wordList = new ArrayList<>();
		
		CoreLabel coreLabel;
		while (ptbt.hasNext()) {
			coreLabel = ptbt.next();
			String word = coreLabel.word();
			wordList.add(word);
		}
		
		return wordList;
		
	}

	public static void main(String[] args) {

	/*	String reviewText = "We conducted a conference here for two days, ";
		ReviewSentimentService reviewSentimentService = new ReviewSentimentService(
				reviewText);
		System.out.println(reviewSentimentService.analyzeReviewSentiment());*/
	}

}
