package models;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
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
"Cost_price",
"Discount",
"Selling_price"
})
@Embedded 
public class Pricing {

@JsonProperty("Cost_price")
@Property("Cost_price")
private Double CostPrice;
@JsonProperty("Discount")
@Property("Discount")
private Double Discount;
@JsonProperty("Selling_price")
@Property("Selling_price")
private Double SellingPrice;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

/**
* No args constructor for use in serialization
* 
*/
public Pricing() {
}

/**
* 
* @param SellingPrice
* @param Discount
* @param CostPrice
*/
public Pricing(Double CostPrice, Double Discount, Double SellingPrice) {
this.CostPrice = CostPrice;
this.Discount = Discount;
this.SellingPrice = SellingPrice;
}

/**
* 
* @return
* The CostPrice
*/
@JsonProperty("Cost_price")
public Double getCostPrice() {
return CostPrice;
}

/**
* 
* @param CostPrice
* The Cost_price
*/
@JsonProperty("Cost_price")
public void setCostPrice(Double CostPrice) {
this.CostPrice = CostPrice;
}

/**
* 
* @return
* The Discount
*/
@JsonProperty("Discount")
public Double getDiscount() {
return Discount;
}

/**
* 
* @param Discount
* The Discount
*/
@JsonProperty("Discount")
public void setDiscount(Double Discount) {
this.Discount = Discount;
}

/**
* 
* @return
* The SellingPrice
*/
@JsonProperty("Selling_price")
public Double getSellingPrice() {
return SellingPrice;
}

/**
* 
* @param SellingPrice
* The Selling_price
*/
@JsonProperty("Selling_price")
public void setSellingPrice(Double SellingPrice) {
this.SellingPrice = SellingPrice;
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