package models;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "card_number", "cardholder_name", "expiry", "card_type" })
@Embedded
public class CardDetails implements Serializable {

	@JsonProperty("card_number")
	@Property("card_number")
	private String cardNumber;
	@JsonProperty("cardholder_name")
	@Property("cardholder_name")
	private String cardholderName;
	@JsonProperty("expiry")
	@Property("expiry")
	private String expiry;
	@JsonProperty("card_type")
	@Property("card_type")
	private String cardType;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public CardDetails() {
	}

	/**
	 * 
	 * @param cardholderName
	 * @param cardType
	 * @param expiry
	 * @param cardNumber
	 */
	public CardDetails(String cardNumber, String cardholderName, String expiry, String cardType) {
		this.cardNumber = cardNumber;
		this.cardholderName = cardholderName;
		this.expiry = expiry;
		this.cardType = cardType;
	}

	/**
	 * 
	 * @return The cardNumber
	 */
	@JsonProperty("card_number")
	public String getCardNumber() {
		return cardNumber;
	}

	/**
	 * 
	 * @param cardNumber
	 *            The card_number
	 */
	@JsonProperty("card_number")
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
	 * 
	 * @return The cardholderName
	 */
	@JsonProperty("cardholder_name")
	public String getCardholderName() {
		return cardholderName;
	}

	/**
	 * 
	 * @param cardholderName
	 *            The cardholder_name
	 */
	@JsonProperty("cardholder_name")
	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}

	/**
	 * 
	 * @return The expiry
	 */
	@JsonProperty("expiry")
	public String getExpiry() {
		return expiry;
	}

	/**
	 * 
	 * @param expiry
	 *            The expiry
	 */
	@JsonProperty("expiry")
	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	/**
	 * 
	 * @return The cardType
	 */
	@JsonProperty("card_type")
	public String getCardType() {
		return cardType;
	}

	/**
	 * 
	 * @param cardType
	 *            The card_type
	 */
	@JsonProperty("card_type")
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
}
