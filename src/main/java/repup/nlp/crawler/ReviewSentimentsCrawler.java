package repup.nlp.crawler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import repup.co.model.reviews.SentimentAnalysis;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReviewSentimentsCrawler {

	private Logger logger = Logger.getLogger(this.getClass());
	private String URL_PREFIX = "http://52.5.115.29";
	private String review_url = URL_PREFIX + "/review_analyzer/analyze/";
	private DefaultHttpClient httpClient;
	private SentimentAnalysis sentimentAnalysis;

	public ReviewSentimentsCrawler() {

		httpClient = new DefaultHttpClient();
	}

	public SentimentAnalysis analyzeSentiment(String reviewContent) {

		logger.debug("Fetching Sentiment Analysis from :" + review_url);

		sentimentAnalysis = new SentimentAnalysis();

		try {

			logger.debug("Api hit for getting getting review analyiss"
					+ review_url);

			logger.debug("Analzing review text: " + reviewContent);

			HttpPost postRequest = new HttpPost(review_url);

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("review", reviewContent
					.trim()));
			postRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			postRequest.addHeader("accept", "application/json");

			HttpResponse response = httpClient.execute(postRequest);
			StringBuilder stringBuilder = new StringBuilder();

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());

			} else {
				InputStream stream = response.getEntity().getContent();

				int b;
				while ((b = stream.read()) != -1) {
					stringBuilder.append((char) b);
				}

				String jsonResponse = stringBuilder.toString();

				System.out.println(jsonResponse);

				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(
						DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
						false);

				sentimentAnalysis = objectMapper.readValue(
						stringBuilder.toString(), SentimentAnalysis.class);

			}

		} catch (Exception ex) {

			logger.debug(
					"Exception at connecting to Sentiments Analysis  API:", ex);

		}

		return sentimentAnalysis;

	}

	public void closeHttpConnection() {
		logger.info("Response Recieved for URL: " + review_url);
		httpClient.getConnectionManager().shutdown();

	}

	public static void main(String[] args) {
		
		ReviewSentimentsCrawler reviewSentimentCrawler = new ReviewSentimentsCrawler();
		SentimentAnalysis sentimentAnalysis = reviewSentimentCrawler.analyzeSentiment("Staff in the restaurant is very helpful.");
		System.out.println(sentimentAnalysis);
		reviewSentimentCrawler.closeHttpConnection();
		
	}
}
