package models;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "cost_price", "discount", "selling_price" })
@Embedded
public class Pricing {

	@JsonProperty("cost_price")
	@Property("cost_price")
	private Double costPrice;
	@JsonProperty("discount")
	@Property("discount")
	private Double discount;
	@JsonProperty("selling_price")
	@Property("selling_price")
	private Double sellingPrice;

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
	public Pricing(Double costPrice, Double discount, Double sellingPrice) {
		this.costPrice = costPrice;
		this.discount = discount;
		this.sellingPrice = sellingPrice;
	}

	/**
	 * 
	 * @return The CostPrice
	 */
	@JsonProperty("cost_price")
	public Double getCostPrice() {
		return costPrice;
	}

	/**
	 * 
	 * @param CostPrice
	 *            The Cost_price
	 */
	@JsonProperty("cost_price")
	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	/**
	 * 
	 * @return The Discount
	 */
	@JsonProperty("discount")
	public Double getDiscount() {
		return discount;
	}

	/**
	 * 
	 * @param Discount
	 *            The Discount
	 */
	@JsonProperty("discount")
	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	/**
	 * 
	 * @return The SellingPrice
	 */
	@JsonProperty("selling_price")
	public Double getSellingPrice() {
		return sellingPrice;
	}

	/**
	 * 
	 * @param SellingPrice
	 *            The Selling_price
	 */
	@JsonProperty("selling_price")
	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

}