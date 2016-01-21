package models;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "brand", "model_no", "color", "size" })
@Embedded
public class Specifications implements Serializable {

	@JsonProperty("brand")
	@Property("brand")
	private String brand;
	@JsonProperty("model_no")
	@Property("model_no")
	private String modelNo;
	@JsonProperty("color")
	@Property("color")
	private String color;
	@JsonProperty("size")
	@Property("size")
	private String size;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Specifications() {
	}

	/**
	 * 
	 * @param brand
	 * @param model_no
	 * @param size
	 * @param color
	 */
	public Specifications(String brand, String model_no, String color,
			String size) {
		this.brand = brand;
		this.modelNo = model_no;
		this.color = color;
		this.size = size;
	}

	/**
	 * 
	 * @return The brand
	 */
	@JsonProperty("brand")
	public String getBrand() {
		return brand;
	}

	/**
	 * 
	 * @param brand
	 *            The brand
	 */
	@JsonProperty("brand")
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * 
	 * @return The model_no
	 */
	@JsonProperty("model_no")
	public String getModelNo() {
		return modelNo;
	}

	/**
	 * 
	 * @param model_no
	 *            The model_no
	 */
	@JsonProperty("model_no")
	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	/**
	 * 
	 * @return The color
	 */
	@JsonProperty("color")
	public String getColor() {
		return color;
	}

	/**
	 * 
	 * @param color
	 *            The color
	 */
	@JsonProperty("color")
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * 
	 * @return The size
	 */
	@JsonProperty("size")
	public String getSize() {
		return size;
	}

	/**
	 * 
	 * @param size
	 *            The size
	 */
	@JsonProperty("size")
	public void setSize(String size) {
		this.size = size;
	}
}
