package models;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import models.Address;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"Name",
"Address"
})
@Embedded   
public class Seller {

@JsonProperty("Name")
@Property("Name")
private String Name;
@JsonProperty("Address")
@Embedded("address")
private Address Address;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
public Seller(String Name,Address Address) {
this.Name = Name;
this.Address = Address;
}

/**
* 
* @return
* The Name
*/
@JsonProperty("Name")
public String getName() {
return Name;
}

/**
* 
* @param Name
* The Name
*/
@JsonProperty("Name")
public void setName(String Name) {
this.Name = Name;
}

/**
* 
* @return
* The Address
*/
@JsonProperty("Address")
public Address getAddress() {
return Address;
}

/**
* 
* @param Address
* The Address
*/
@JsonProperty("Address")
public void setAddress(Address Address) {
this.Address = Address;
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