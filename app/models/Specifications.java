package models;

import java.util.HashMap;
import java.util.Map;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Brand", "ModelNo", "Color", "Size" })
@Embedded
public class Specifications {

	@JsonProperty("Brand")
	@Property("Brand")
	private String Brand;
	@JsonProperty("ModelNo")
	@Property("ModelNo")
	private Object ModelNo;
	@JsonProperty("Color")
	@Property("Color")
	private String Color;
	@JsonProperty("Size")
	@Property("Size")
	private Object Size;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Specifications() {
	}

	/**
	 * 
	 * @param Brand
	 * @param ModelNo
	 * @param Size
	 * @param Color
	 */
	public Specifications(String Brand, Object ModelNo, String Color,
			Object Size) {
		this.Brand = Brand;
		this.ModelNo = ModelNo;
		this.Color = Color;
		this.Size = Size;
	}

	/**
	 * 
	 * @return The Brand
	 */
	@JsonProperty("Brand")
	public String getBrand() {
		return Brand;
	}

	/**
	 * 
	 * @param Brand
	 *            The Brand
	 */
	@JsonProperty("Brand")
	public void setBrand(String Brand) {
		this.Brand = Brand;
	}

	/**
	 * 
	 * @return The ModelNo
	 */
	@JsonProperty("ModelNo")
	public Object getModelNo() {
		return ModelNo;
	}

	/**
	 * 
	 * @param ModelNo
	 *            The ModelNo
	 */
	@JsonProperty("ModelNo")
	public void setModelNo(Object ModelNo) {
		this.ModelNo = ModelNo;
	}

	/**
	 * 
	 * @return The Color
	 */
	@JsonProperty("Color")
	public String getColor() {
		return Color;
	}

	/**
	 * 
	 * @param Color
	 *            The Color
	 */
	@JsonProperty("Color")
	public void setColor(String Color) {
		this.Color = Color;
	}

	/**
	 * 
	 * @return The Size
	 */
	@JsonProperty("Size")
	public Object getSize() {
		return Size;
	}

	/**
	 * 
	 * @param Size
	 *            The Size
	 */
	@JsonProperty("Size")
	public void setSize(Object Size) {
		this.Size = Size;
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
