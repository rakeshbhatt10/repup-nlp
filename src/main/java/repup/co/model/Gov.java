package repup.co.model;

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
@JsonPropertyOrder({ "value", "index" })
public class Gov {

	@JsonProperty("value")
	private String value;
	@JsonProperty("index")
	private long index;
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
	 * @return The index
	 */
	@JsonProperty("index")
	public long getIndex() {
		return index;
	}

	/**
	 * 
	 * @param index
	 *            The index
	 */
	@JsonProperty("index")
	public void setIndex(long index) {
		this.index = index;
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
