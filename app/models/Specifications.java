package models;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "brand", "model_no", "color", "size" })
@Embedded
public class Specifications {

	@JsonProperty("brand")
	@Property("brand")
	private String brand;
	@JsonProperty("model_no")
	@Property("model_no")
	private Object modelNo;
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
	 * @param Brand
	 * @param ModelNo
	 * @param Size
	 * @param Color
	 */
	public Specifications(String brand, Object modelNo, String color,
			String size) {
		this.brand = brand;
		this.modelNo = modelNo;
		this.color = color;
		this.size = size;
	}

	/**
	 * 
	 * @return The Brand
	 */
	@JsonProperty("brand")
	public String getBrand() {
		return brand;
	}

	/**
	 * 
	 * @param Brand
	 *            The Brand
	 */
	@JsonProperty("brand")
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * 
	 * @return The ModelNo
	 */
	@JsonProperty("model_no")
	public Object getModelNo() {
		return modelNo;
	}

	/**
	 * 
	 * @param ModelNo
	 *            The ModelNo
	 */
	@JsonProperty("model_no")
	public void setModelNo(Object modelNo) {
		this.modelNo = modelNo;
	}

	/**
	 * 
	 * @return The Color
	 */
	@JsonProperty("color")
	public String getColor() {
		return color;
	}

	/**
	 * 
	 * @param Color
	 *            The Color
	 */
	@JsonProperty("Color")
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * 
	 * @return The Size
	 */
	@JsonProperty("size")
	public String getSize() {
		return size;
	}

	/**
	 * 
	 * @param Size
	 *            The Size
	 */
	@JsonProperty("size")
	public void setSize(String size) {
		this.size = size;
	}

}
