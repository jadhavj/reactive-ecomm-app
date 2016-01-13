package models;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import models.Address;
import models.CardDetails;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "_id", "username", "password", "role", "firstname", "lastname", "address", "email",
		"mobile_number", "card_details" })
@Entity("users")
public class User {
	/**
	 * (Required)
	 */
	@JsonProperty("_id")
	@Id
	private ObjectId id;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("username")
	@Property("username")
	private String username;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("password")
	@Property("password")
	private String password;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("role")
	@Property("role")
	private String role;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("firstname")
	@Property("firstname")
	private String firstname;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("lastname")
	@Property("lastname")
	private String lastname;

	@JsonProperty("address")
	@Embedded("address")
	private Address address;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("email")
	@Property("email")
	private String email;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("mobile_number")
	@Property("mobile_number")
	private Long mobileNumber;

	@JsonProperty("card_details")
	@Embedded("card_details")
	private CardDetails cardDetails;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public User() {
	}

	/**
	 * 
	 * @param username
	 * @param email
	 * @param address
	 * @param ID
	 * @param lastname
	 * @param role
	 * @param firstname
	 * @param mobileNumber
	 * @param password
	 * @param cardDetails
	 */
	public User(String username, String password, String role, String firstname, String lastname,
			Address address, String email, Long mobileNumber, CardDetails cardDetails) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.firstname = firstname;
		this.lastname = lastname;
		// this.address = address;
		this.email = email;
		this.mobileNumber = mobileNumber;
		// this.cardDetails = cardDetails;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The ID
	 */
	@JsonProperty("_id")
	public ObjectId getID() {
		return id;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param ID
	 *            The ID
	 */
	@JsonProperty("_id")
	public void setID(ObjectId id) {
		this.id = id;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The username
	 */
	@JsonProperty("username")
	public String getUsername() {
		return username;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param username
	 *            The username
	 */
	@JsonProperty("username")
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The password
	 */
	@JsonProperty("password")
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param password
	 *            The password
	 */
	@JsonProperty("password")
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The role
	 */
	@JsonProperty("role")
	public String getRole() {
		return role;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param role
	 *            The role
	 */
	@JsonProperty("role")
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The firstname
	 */
	@JsonProperty("firstname")
	public String getFirstname() {
		return firstname;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param firstname
	 *            The firstname
	 */
	@JsonProperty("firstname")
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The lastname
	 */
	@JsonProperty("lastname")
	public String getLastname() {
		return lastname;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param lastname
	 *            The lastname
	 */
	@JsonProperty("lastname")
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * 
	 * @return The address
	 */
	@JsonProperty("address")
	public Address getAddress() {
		return address;
	}

	/**
	 * 
	 * @param address
	 *            The address
	 */
	@JsonProperty("address")
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The email
	 */
	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param email
	 *            The email
	 */
	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The mobileNumber
	 */
	@JsonProperty("mobile_number")
	public Long getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param mobileNumber
	 *            The mobile_number
	 */
	@JsonProperty("mobile_number")
	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * 
	 * @return The cardDetails
	 */
	@JsonProperty("card_details")
	public CardDetails getCardDetails() {
		return cardDetails;
	}

	/**
	 * 
	 * @param cardDetails
	 *            The card_details
	 */
	@JsonProperty("card_details")
	public void setCardDetails(CardDetails cardDetails) {
		this.cardDetails = cardDetails;
	}
}
