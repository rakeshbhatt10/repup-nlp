package repup.co.model.reviews;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "review_polarity", "review_json", "review_sentence" })
public class ReviewSentiment {

	@JsonProperty("review_polarity")
	private Double reviewPolarity;
	@JsonProperty("review_json")
	private String reviewJson;
	@JsonProperty("review_sentence")
	private String reviewSentence;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The reviewPolarity
	 */
	@JsonProperty("review_polarity")
	public Double getReviewPolarity() {
		return reviewPolarity;
	}

	/**
	 * 
	 * @param reviewPolarity
	 *            The review_polarity
	 */
	@JsonProperty("review_polarity")
	public void setReviewPolarity(Double reviewPolarity) {
		this.reviewPolarity = reviewPolarity;
	}

	/**
	 * 
	 * @return The reviewJson
	 */
	@JsonProperty("review_json")
	public String getReviewJson() {
		return reviewJson;
	}

	/**
	 * 
	 * @param reviewJson
	 *            The review_json
	 */
	@JsonProperty("review_json")
	public void setReviewJson(String reviewJson) {
		this.reviewJson = reviewJson;
	}

	/**
	 * 
	 * @return The reviewSentence
	 */
	@JsonProperty("review_sentence")
	public String getReviewSentence() {
		return reviewSentence;
	}

	/**
	 * 
	 * @param reviewSentence
	 *            The review_sentence
	 */
	@JsonProperty("review_sentence")
	public void setReviewSentence(String reviewSentence) {
		this.reviewSentence = reviewSentence;

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
