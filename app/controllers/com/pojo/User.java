
package controllers.com.pojo;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import controllers.com.pojo.UserRole;
import controllers.com.pojo.Address;
import controllers.com.pojo.CardDetails;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "ID",
    "username",
    "password",
    "role",
    "firstname",
    "lastname",
    "address",
    "email",
    "mobile_number",
    "card_details"
})
public class User {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("ID")
    private String ID;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("username")
    private Object username;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("password")
    private String password;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("role")
    private UserRole role;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("firstname")
    private String firstname;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("address")
    private Address address;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("email")
    private String email;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("mobile_number")
    private Long mobileNumber;
    @JsonProperty("card_details")
    private CardDetails cardDetails;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
    public User(String ID, Object username, String password, UserRole role, String firstname, String lastname, Address address, String email, Long mobileNumber, CardDetails cardDetails) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.cardDetails = cardDetails;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The ID
     */
    @JsonProperty("ID")
    public String getID() {
        return ID;
    }

    /**
     * 
     * (Required)
     * 
     * @param ID
     *     The ID
     */
    @JsonProperty("ID")
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The username
     */
    @JsonProperty("username")
    public Object getUsername() {
        return username;
    }

    /**
     * 
     * (Required)
     * 
     * @param username
     *     The username
     */
    @JsonProperty("username")
    public void setUsername(Object username) {
        this.username = username;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The password
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
     *     The password
     */
    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The role
     */
    @JsonProperty("role")
    public UserRole getRole() {
        return role;
    }

    /**
     * 
     * (Required)
     * 
     * @param role
     *     The role
     */
    @JsonProperty("role")
    public void setRole(UserRole role) {
        this.role = role;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The firstname
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
     *     The firstname
     */
    @JsonProperty("firstname")
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The lastname
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
     *     The lastname
     */
    @JsonProperty("lastname")
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * 
     * @return
     *     The address
     */
    @JsonProperty("address")
    public Address getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    @JsonProperty("address")
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The email
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
     *     The email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The mobileNumber
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
     *     The mobile_number
     */
    @JsonProperty("mobile_number")
    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * 
     * @return
     *     The cardDetails
     */
    @JsonProperty("card_details")
    public CardDetails getCardDetails() {
        return cardDetails;
    }

    /**
     * 
     * @param cardDetails
     *     The card_details
     */
    @JsonProperty("card_details")
    public void setCardDetails(CardDetails cardDetails) {
        this.cardDetails = cardDetails;
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
