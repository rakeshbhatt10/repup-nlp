package repup.nlp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import repup.co.model.Review;
import repup.co.model.reviews.ReviewList;
import repup.nlp.services.ReviewSentimentService;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

@Controller
public class NlpController {

	private Logger logger = Logger.getLogger(this.getClass());

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String indexController() {

		System.out.println("Rendering Index");
		return "index";
	}

	@RequestMapping(value = "/reviews/sentiment", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ArrayList<Review> analyzeSentiments(HttpServletRequest request,
			HttpServletResponse response) {

		// logger.info("Review " + reviewList);
		ArrayList<String> reviewList = new ArrayList<>();
		String body = "";
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(
						inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			logger.error("Exception ", ex);
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					logger.error("Exception ", ex);
				}
			}
		}

		body = stringBuilder.toString();
		System.out.println("Body: " + body);
		try {
			
			JSONObject reviewObj = new JSONObject(body);
			JSONArray reviewArray = reviewObj.getJSONArray("reviews");
			for(int counter=0;counter<reviewArray.length();counter++) {
				reviewList.add(reviewArray.getString(counter));
			}
			
		}catch(Exception ex) {
			logger.error("Exception ", ex);
		}
		
		ReviewSentimentService reviewSentimentService = new ReviewSentimentService(reviewList);
		  
		return reviewSentimentService.getReviewSentiment();

	}

}
