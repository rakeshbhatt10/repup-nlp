package repup.co.model;

import java.util.ArrayList;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "sentence", "sentiment", "data"  })
public class ReviewData {

	@JsonProperty("sentence")
	private String sentence;
	@JsonProperty("data")
	private ArrayList<SentenceData> data;
	@JsonProperty("sentiment")
	private String sentiment;

	@JsonProperty("sentence")
	public String getSentence() {
		return sentence;
	}

	@JsonProperty("sentence")
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	@JsonProperty("data")
	public ArrayList<SentenceData> getData() {
		return data;
	}

	@JsonProperty("data")
	public void setData(ArrayList<SentenceData> data) {
		this.data = data;
	}

	@JsonProperty("sentiment")
	public String getSentiment() {
		return sentiment;
	}

	@JsonProperty("sentiment")
	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
