package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "ID", "Name", "Category", "Sub-category", "Pricing",
		"Features", "Image", "Specifications", "items_in_stock", "Seller",
		"cities_for_delivery" })
@Entity("Product")
public class Product {

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("ID")
	@Property("ID")
	private String ID;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("Name")
	@Property("Name")
	private String Name;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("Category")
	@Property("Category")
	private String Category;
	@JsonProperty("Sub-category")
	@Property("Sub-category")
	private String SubCategory;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("Pricing")
	@Embedded("Pricing")
	private Pricing Pricing;
	@JsonProperty("Features")
	@Property("Features")
	private List<Object> Features = new ArrayList<Object>();
	@JsonProperty("Image")
	@Property("Image")
	private byte[] Image;
	@JsonProperty("Specifications")
	@Embedded("Specifications")
	private Specifications Specifications;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("items_in_stock")
	@Property("items_in_stock")
	private Integer itemsInStock;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("Seller")
	@Embedded("Seller")
	private Seller Seller;
	@JsonProperty("cities_for_delivery")
	@Property("cities_for_delivery")
	private List<Object> citiesForDelivery = new ArrayList<Object>();
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Product() {
	}

	/**
	 * 
	 * @param Name
	 * @param Pricing
	 * @param Category
	 * @param Image
	 * @param SubCategory
	 * @param ID
	 * @param citiesForDelivery
	 * @param Seller
	 * @param Specifications
	 * @param itemsInStock
	 * @param Features
	 */
	public Product(String ID, String Name, String Category, String SubCategory,
			Pricing Pricing, List<Object> Features, byte[] Image,
			Specifications Specifications, Integer itemsInStock, Seller Seller,
			List<Object> citiesForDelivery) {
		this.ID = ID;
		this.Name = Name;
		this.Category = Category;
		this.SubCategory = SubCategory;
		this.Pricing = Pricing;
		this.Features = Features;
		this.Image = Image;
		this.Specifications = Specifications;
		this.itemsInStock = itemsInStock;
		this.Seller = Seller;
		this.citiesForDelivery = citiesForDelivery;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The ID
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
	 *            The ID
	 */
	@JsonProperty("ID")
	public void setID(String ID) {
		this.ID = ID;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The Name
	 */
	@JsonProperty("Name")
	public String getName() {
		return Name;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param Name
	 *            The Name
	 */
	@JsonProperty("Name")
	public void setName(String Name) {
		this.Name = Name;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The Category
	 */
	@JsonProperty("Category")
	public String getCategory() {
		return Category;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param Category
	 *            The Category
	 */
	@JsonProperty("Category")
	public void setCategory(String Category) {
		this.Category = Category;
	}

	/**
	 * 
	 * @return The SubCategory
	 */
	@JsonProperty("Sub-category")
	public String getSubCategory() {
		return SubCategory;
	}

	/**
	 * 
	 * @param SubCategory
	 *            The Sub-category
	 */
	@JsonProperty("Sub-category")
	public void setSubCategory(String SubCategory) {
		this.SubCategory = SubCategory;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The Pricing
	 */
	@JsonProperty("Pricing")
	public Pricing getPricing() {
		return Pricing;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param Pricing
	 *            The Pricing
	 */
	@JsonProperty("Pricing")
	public void setPricing(Pricing Pricing) {
		this.Pricing = Pricing;
	}

	/**
	 * 
	 * @return The Features
	 */
	@JsonProperty("Features")
	public List<Object> getFeatures() {
		return Features;
	}

	/**
	 * 
	 * @param Features
	 *            The Features
	 */
	@JsonProperty("Features")
	public void setFeatures(List<Object> Features) {
		this.Features = Features;
	}

	/**
	 * 
	 * @return The Image
	 */
	@JsonProperty("Image")
	public byte[] getImage() {
		return Image;
	}

	/**
	 * 
	 * @param Image
	 *            The Image
	 */
	@JsonProperty("Image")
	public void setImage(byte[] Image) {
		this.Image = Image;
	}

	/**
	 * 
	 * @return The Specifications
	 */
	@JsonProperty("Specifications")
	public Specifications getSpecifications() {
		return Specifications;
	}

	/**
	 * 
	 * @param Specifications
	 *            The Specifications
	 */
	@JsonProperty("Specifications")
	public void setSpecifications(Specifications Specifications) {
		this.Specifications = Specifications;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The itemsInStock
	 */
	@JsonProperty("items_in_stock")
	public Integer getItemsInStock() {
		return itemsInStock;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param itemsInStock
	 *            The items_in_stock
	 */
	@JsonProperty("items_in_stock")
	public void setItemsInStock(Integer itemsInStock) {
		this.itemsInStock = itemsInStock;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @return The Seller
	 */
	@JsonProperty("Seller")
	public Seller getSeller() {
		return Seller;
	}

	/**
	 * 
	 * (Required)
	 * 
	 * @param Seller
	 *            The Seller
	 */
	@JsonProperty("Seller")
	public void setSeller(Seller Seller) {
		this.Seller = Seller;
	}

	/**
	 * 
	 * @return The citiesForDelivery
	 */
	@JsonProperty("cities_for_delivery")
	public List<Object> getCitiesForDelivery() {
		return citiesForDelivery;
	}

	/**
	 * 
	 * @param citiesForDelivery
	 *            The cities_for_delivery
	 */
	@JsonProperty("cities_for_delivery")
	public void setCitiesForDelivery(List<Object> citiesForDelivery) {
		this.citiesForDelivery = citiesForDelivery;
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