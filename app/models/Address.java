package models;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "street", "city", "state", "zip" })
@Embedded
public class Address implements Serializable {

	@JsonProperty("street")
	@Property("street")
	private String street;
	@JsonProperty("city")
	@Property("city")
	private String city;
	@JsonProperty("state")
	@Property("state")
	private String state;
	@JsonProperty("zip")
	@Property("zip")
	private Long zip;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Address() {
	}

	/**
	 * 
	 * @param zip
	 * @param street
	 * @param state
	 * @param city
	 */
	public Address(String street, String city, String state, Long zip) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	/**
	 * 
	 * @return The street
	 */
	@JsonProperty("street")
	public String getStreet() {
		return street;
	}

	/**
	 * 
	 * @param street
	 *            The street
	 */
	@JsonProperty("street")
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * 
	 * @return The city
	 */
	@JsonProperty("city")
	public String getCity() {
		return city;
	}

	/**
	 * 
	 * @param city
	 *            The city
	 */
	@JsonProperty("city")
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * 
	 * @return The state
	 */
	@JsonProperty("state")
	public String getState() {
		return state;
	}

	/**
	 * 
	 * @param state
	 *            The state
	 */
	@JsonProperty("state")
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * 
	 * @return The zip
	 */
	@JsonProperty("zip")
	public Long getZip() {
		return zip;
	}

	/**
	 * 
	 * @param zip
	 *            The zip
	 */
	@JsonProperty("zip")
	public void setZip(Long zip) {
		this.zip = zip;
	}
}
