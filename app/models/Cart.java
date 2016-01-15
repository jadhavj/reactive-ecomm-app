
package models;

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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "_id",
    "orderID",
    "orderItems",
    "orderDate",
    "total",
    "status",
    "username",
    "shippingAddress",
    "paymentMode"
})
@Entity("Cart")
public class Cart {

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
    @JsonProperty("orderID")
    @Property("orderID")
    private String orderID;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("orderItems")
    @Property("orderItems")
    private List<String> orderItems = new ArrayList<String>();
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("orderDate")
    @Property("orderDate")
    private String orderDate;
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
    @JsonProperty("shippingAddress")
	@Embedded("shippingAddress")
    private ShippingAddress shippingAddress;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("paymentMode")
    @Embedded("paymentMode")
    private CardDetails paymentMode;
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
   *     The orderID
   */
  @JsonProperty("orderID")
  public String getOrderID() {
      return orderID;
  }

  /**
   * 
   * (Required)
   * 
   * @param orderID
   *     The orderID
   */
  @JsonProperty("orderID")
  public void setOrderID(String orderID) {
      this.orderID = orderID;
  }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The orderItems
     */
    @JsonProperty("orderItems")
    public List<String> getOrderItems() {
        return orderItems;
    }

    /**
     * 
     * (Required)
     * 
     * @param orderItems
     *     The orderItems
     */
    @JsonProperty("orderItems")
    public void setOrderItems(List<String> orderItems) {
        this.orderItems = orderItems;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The orderDate
     */
    @JsonProperty("orderDate")
    public String getOrderDate() {
        return orderDate;
    }

    /**
     * 
     * (Required)
     * 
     * @param orderDate
     *     The orderDate
     */
    @JsonProperty("orderDate")
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
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
    @JsonProperty("shippingAddress")
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
    @JsonProperty("shippingAddress")
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
    @JsonProperty("paymentMode")
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
    @JsonProperty("paymentMode")
    public void setPaymentMode(CardDetails paymentMode) {
        this.paymentMode = paymentMode;
    }

 
}
