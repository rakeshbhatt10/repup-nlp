package repup.nlp.utilties;

import org.apache.log4j.Logger;

import repup.co.model.reviews.SentimentAnalysis;
import repup.nlp.crawler.ReviewSentimentsCrawler;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

public class SentimentAnalyzerUtils {

	private Logger logger = Logger.getLogger(this.getClass());
	private StanfordCoreNLP stanfordCoreNLP;
	private ReviewSentimentsCrawler reviewSentimentsCrawler;
	
	public SentimentAnalyzerUtils(StanfordCoreNLP stanfordCoreNLP) {
		
		this.stanfordCoreNLP = stanfordCoreNLP;		
		this.reviewSentimentsCrawler = new ReviewSentimentsCrawler();
		
	}
	
	
	public String getSentiment(String text) {
		
		logger.info("Creating Sentiment for :"+ text);
		char lastChar = text.charAt(text.length()-1);
		if(lastChar != '.'){
			text = text + ".";
		}	
		SentimentAnalysis sentimentAnalysis = reviewSentimentsCrawler.analyzeSentiment(text);
		
		return getReviewSentiment(sentimentAnalysis.getReviewPolatity());
	}
	
	/*public String getSentiment(String text) {
		
		logger.info("Creating Sentiment for :"+ text);
		char lastChar = text.charAt(text.length()-1);
		if(lastChar != '.'){
			text = text + ".";
		}	
		Annotation annotation = stanfordCoreNLP.process(text);
		List<CoreMap> sentences = annotation
				.get(CoreAnnotations.SentencesAnnotation.class);
		
		String sentimentOutput = "";
		for (CoreMap sentence : sentences) {
			logger.info("Creating sentiment for sentence: "+ sentence);
			sentimentOutput = sentence
					.get(SentimentCoreAnnotations.SentimentClass.class);
		}
		return sentimentOutput;
	}*/
	
	
	private String getReviewSentiment(Double reviewPolatity) {
		
		if(reviewPolatity>0) {
			return "Positive";
		} else if(reviewPolatity<0) {
			return "Negative";
		} else {
			return "Neutral";
		}

	}


	public String getSentiment(CoreMap sentence) {
		
		logger.info("Creating Sentiment for :"+ sentence);
		String sentiment = "";
		System.out.println("Creating sentiment for sentence: "+ sentence);
		sentiment = sentence
					.get(SentimentCoreAnnotations.SentimentClass.class);
		return sentiment;
	}
	
	public static void main(String[] args) {
	/*	Properties props = new Properties();
		//logger.info("Analzing Text: " + reviewText);
	
		props.setProperty("annotators",
				"tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		SentimentAnalyzerUtils utis = new SentimentAnalyzerUtils(pipeline);
		System.out.println(utis.getSentiment("Wi-Fi service promised but not available."));
	*/
		String text  = "saying its in under maintenance.";
		char lastChar = text.charAt(text.length()-1);
		if(lastChar != '.'){
			text = text + ".";
		}	
		System.out.println(text);
		text = Character.toUpperCase(text.charAt(0)) + text.substring(1);
		System.out.println(text);
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		reviewSentimentsCrawler.closeHttpConnection();
	}
}
