package models;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "address" })
@Embedded
public class Seller {

	@JsonProperty("name")
	@Property("name")
	private String name;
	@JsonProperty("address")
	@Embedded("address")
	private Address address;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Seller() {
	}

	/**
	 * 
	 * @param Name
	 * @param Address
	 */
	public Seller(String name, Address address) {
		this.name = name;
		this.address = address;
	}

	/**
	 * 
	 * @return The Name
	 */
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param Name
	 *            The Name
	 */
	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return The Address
	 */
	@JsonProperty("address")
	public Address getAddress() {
		return address;
	}

	/**
	 * 
	 * @param Address
	 *            The Address
	 */
	@JsonProperty("address")
	public void setAddress(Address address) {
		this.address = address;
	}
}