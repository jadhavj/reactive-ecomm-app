package models;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "_id",
    "items",
    "total",
    "status",
    "username",
    "shipping_address",
    "payment_mode"
})
@Entity("cart")
public class Cart implements Serializable {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("_id")
    @Id
    private ObjectId id;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("items")
    @Embedded("items")
    private List<Product> items = new ArrayList<Product>();
    
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("total")
    @Property("total")
   private Double total;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("status")
    @Property("status")
   private String status;
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
    @JsonProperty("shipping_address")
	@Embedded("shipping_address")
    private ShippingAddress shippingAddress;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("payment_mode")
    @Embedded("payment_mode")
    private CardDetails paymentMode;
    
    public Cart() {
    }
    
	/**
    * 
    * (Required)
    * 
    * @return
    *     The orderID
    */
   @JsonProperty("_id")
   public ObjectId getId() {
       return id;
   }

   /**
    * 
    * (Required)
    * 
    * @param orderID
    *     The orderID
    */
   @JsonProperty("_id")
   public void setId(ObjectId id) {
       this.id = id;
   }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The orderItems
     */
    @JsonProperty("items")
    public List<Product> getItems() {
        return items;
    }

    /**
     * 
     * (Required)
     * 
     * @param orderItems
     *     The orderItems
     */
    @JsonProperty("items")
    public void setItems(List<Product> items) {
        this.items = items;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The total
     */
    @JsonProperty("total")
    public Double getTotal() {
        return total;
    }

    /**
     * 
     * (Required)
     * 
     * @param total
     *     The total
     */
    @JsonProperty("total")
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The status
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * 
     * (Required)
     * 
     * @param status
     *     The status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The username
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
     *     The username
     */
    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The shippingAddress
     */
    @JsonProperty("shipping_address")
    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    /**
     * 
     * (Required)
     * 
     * @param shippingAddress
     *     The shippingAddress
     */
    @JsonProperty("shipping_address")
    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The paymentMode
     */
    @JsonProperty("payment_mode")
    public CardDetails getPaymentMode() {
        return paymentMode;
    }

    /**
     * 
     * (Required)
     * 
     * @param paymentMode
     *     The paymentMode
     */
    @JsonProperty("payment_mode")
    public void setPaymentMode(CardDetails paymentMode) {
        this.paymentMode = paymentMode;
    }
}
