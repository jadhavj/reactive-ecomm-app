
package controllers.com.pojo;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "card_number",
    "cardholder_name",
    "expiry",
    "card_type"
})
public class CardDetails {

    @JsonProperty("card_number")
    private Long cardNumber;
    @JsonProperty("cardholder_name")
    private String cardholderName;
    @JsonProperty("expiry")
    private Long expiry;
    @JsonProperty("card_type")
    private String cardType;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
    public CardDetails(Long cardNumber, String cardholderName, Long expiry, String cardType) {
        this.cardNumber = cardNumber;
        this.cardholderName = cardholderName;
        this.expiry = expiry;
        this.cardType = cardType;
    }

    /**
     * 
     * @return
     *     The cardNumber
     */
    @JsonProperty("card_number")
    public Long getCardNumber() {
        return cardNumber;
    }

    /**
     * 
     * @param cardNumber
     *     The card_number
     */
    @JsonProperty("card_number")
    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * 
     * @return
     *     The cardholderName
     */
    @JsonProperty("cardholder_name")
    public String getCardholderName() {
        return cardholderName;
    }

    /**
     * 
     * @param cardholderName
     *     The cardholder_name
     */
    @JsonProperty("cardholder_name")
    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    /**
     * 
     * @return
     *     The expiry
     */
    @JsonProperty("expiry")
    public Long getExpiry() {
        return expiry;
    }

    /**
     * 
     * @param expiry
     *     The expiry
     */
    @JsonProperty("expiry")
    public void setExpiry(Long expiry) {
        this.expiry = expiry;
    }

    /**
     * 
     * @return
     *     The cardType
     */
    @JsonProperty("card_type")
    public String getCardType() {
        return cardType;
    }

    /**
     * 
     * @param cardType
     *     The card_type
     */
    @JsonProperty("card_type")
    public void setCardType(String cardType) {
        this.cardType = cardType;
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
