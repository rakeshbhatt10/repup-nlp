package repup.co.model.reviews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "review_sentiments", "review_polatity" })
public class SentimentAnalysis {

	@JsonProperty("review_sentiments")
	private List<ReviewSentiment> reviewSentiments = new ArrayList<ReviewSentiment>();
	@JsonProperty("review_polatity")
	private Double reviewPolatity;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The reviewSentiments
	 */
	@JsonProperty("review_sentiments")
	public List<ReviewSentiment> getReviewSentiments() {
		return reviewSentiments;
	}

	/**
	 * 
	 * @param reviewSentiments
	 *            The review_sentiments
	 */
	@JsonProperty("review_sentiments")
	public void setReviewSentiments(List<ReviewSentiment> reviewSentiments) {
		this.reviewSentiments = reviewSentiments;
	}

	/**
	 * 
	 * @return The reviewPolatity
	 */
	@JsonProperty("review_polatity")
	public Double getReviewPolatity() {
		return reviewPolatity;
	}

	/**
	 * 
	 * @param reviewPolatity
	 *            The review_polatity
	 */
	@JsonProperty("review_polatity")
	public void setReviewPolatity(Double reviewPolatity) {
		this.reviewPolatity = reviewPolatity;
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
