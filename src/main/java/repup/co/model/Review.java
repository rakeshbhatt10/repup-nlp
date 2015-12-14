package repup.co.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "review", "reviewData", "sentiment" })
public class Review {

	@JsonProperty("review")
	private String review;
	@JsonProperty("reviewData")
	private List<ReviewData> reviewData = new ArrayList<ReviewData>();
	@JsonProperty("sentiment")
	private HashMap<String,String> sentiment;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The review
	 */
	@JsonProperty("review")
	public String getReview() {
		return review;
	}

	/**
	 * 
	 * @param review
	 *            The review
	 */
	@JsonProperty("review")
	public void setReview(String review) {
		this.review = review;
	}

	/**
	 * 
	 * @return The reviewData
	 */
	@JsonProperty("reviewData")
	public List<ReviewData> getReviewData() {
		return reviewData;
	}

	/**
	 * 
	 * @param reviewData
	 *            The reviewData
	 */
	@JsonProperty("reviewData")
	public void setReviewData(List<ReviewData> reviewData) {
		this.reviewData = reviewData;
	}

	/**
	 * 
	 * @return The sentiment
	 */
	@JsonProperty("sentiment")
	public HashMap<String,String> getSentiment() {
		return sentiment;
	}

	/**
	 * 
	 * @param sentiment
	 *            The sentiment
	 */
	@JsonProperty("sentiment")
	public void setSentiment(HashMap<String,String> sentiment) {
		this.sentiment = sentiment;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
