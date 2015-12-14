package repup.co.model;

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
@JsonPropertyOrder({ "value", "sentiment", "index", "keyword" })
public class Pharase {

	@JsonProperty("value")
	private String value;
	@JsonProperty("sentiment")
	private String sentiment;
	@JsonProperty("index")
	private List<Integer> index;
	@JsonProperty("keyword")
	private String keyword;
	
	@JsonProperty("index")
	public List<Integer> getIndex() {
		return index;
	}
	
	@JsonProperty("index")
	public void setIndex(List<Integer> index) {
		this.index = index;
	}
	
	@JsonProperty("keyword")
	public String getKeyword() {
		return keyword;
	}
	
	@JsonProperty("keyword")
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The value
	 */
	@JsonProperty("value")
	public String getValue() {
		return value;
	}

	/**
	 * 
	 * @param value
	 *            The value
	 */
	@JsonProperty("value")
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 
	 * @return The sentiment
	 */
	@JsonProperty("sentiment")
	public String getSentiment() {
		return sentiment;
	}

	/**
	 * 
	 * @param sentiment
	 *            The sentiment
	 */
	@JsonProperty("sentiment")
	public void setSentiment(String sentiment) {
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
