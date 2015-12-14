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
@JsonPropertyOrder({ "rel", "pharase", "keywords" })
public class SentenceData {

	@JsonProperty("rel")
	private String rel;
	@JsonProperty("pharase")
	private List<Pharase> pharase = new ArrayList<Pharase>();
	
	/*@JsonProperty("dependency")
	private List<Dependency> dependency = new ArrayList<Dependency>();*/
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The rel
	 */
	@JsonProperty("rel")
	public String getRel() {
		return rel;
	}

	/**
	 * 
	 * @param rel
	 *            The rel
	 */
	@JsonProperty("rel")
	public void setRel(String rel) {
		this.rel = rel;
	}

	/**
	 * 
	 * @return The pharase
	 */
	@JsonProperty("pharase")
	public List<Pharase> getPharase() {
		return pharase;
	}

	/**
	 * 
	 * @param pharase
	 *            The pharase
	 */
	@JsonProperty("pharase")
	public void setPharase(List<Pharase> pharase) {
		this.pharase = pharase;
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
