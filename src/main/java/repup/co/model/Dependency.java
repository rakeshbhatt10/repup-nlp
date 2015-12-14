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
@JsonPropertyOrder({ "gov", "dep" })
public class Dependency {

	@JsonProperty("gov")
	private Gov gov = new Gov();
	@JsonProperty("dep")
	private Dep dep = new Dep();
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The gov
	 */
	@JsonProperty("gov")
	public Gov getGov() {
		return gov;
	}

	/**
	 * 
	 * @param gov
	 *            The gov
	 */
	@JsonProperty("gov")
	public void setGov(Gov gov) {
		this.gov = gov;
	}

	/**
	 * 
	 * @return The dep
	 */
	@JsonProperty("dep")
	public Dep getDep() {
		return dep;
	}

	/**
	 * 
	 * @param dep
	 *            The dep
	 */
	@JsonProperty("dep")
	public void setDep(Dep dep) {
		this.dep = dep;
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