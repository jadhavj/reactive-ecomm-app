
package models;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
@Embedded
public class ShippingAddress implements Serializable {

	@JsonProperty("firstname")
	@Property("firstname")
    private String firstname;
    @JsonProperty("lastname")
	@Property("lastname")
    private String lastname;
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
    @JsonProperty("mobile_number")
	@Property("mobile_number")
    private Long mobileNumber;
    
    public ShippingAddress() {
    	
    }

    public ShippingAddress(String firstname, String lastname, String street,
			String city, String state, Long zip, Long mobileNumber) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.mobileNumber = mobileNumber;
	}
    
    /**
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
     * @param firstname
     *     The firstname
     */
    @JsonProperty("firstname")
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
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
     *     The street
     */
    @JsonProperty("street")
    public String getStreet() {
        return street;
    }

    /**
     * 
     * @param street
     *     The street
     */
    @JsonProperty("street")
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * 
     * @return
     *     The city
     */
    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param city
     *     The city
     */
    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 
     * @return
     *     The state
     */
    @JsonProperty("state")
    public String getState() {
        return state;
    }

    /**
     * 
     * @param state
     *     The state
     */
    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 
     * @return
     *     The zip
     */
    @JsonProperty("zip")
    public Long getZip() {
        return zip;
    }

    /**
     * 
     * @param zip
     *     The zip
     */
    @JsonProperty("zip")
    public void setZip(Long zip) {
        this.zip = zip;
    }

    /**
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
     * @param mobileNumber
     *     The mobile_number
     */
    @JsonProperty("mobile_number")
    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

}
