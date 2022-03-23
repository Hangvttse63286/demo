package com.example.demo.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "products", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private String product_status_id;
	private String product_name;
	private String description_list;
	private String description_details;
	private String search_word;
	private float price;
	private int sold;

//	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(name = "product_categories",
//        joinColumns = @JoinColumn(name = "id", referencedColumnName = "id"),
//        inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
//	private Set<Category> categories = new HashSet<>();


	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "product_categories",
        joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
	private Collection<Category> categories = new ArrayList();

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Product_SKU> productSKUs= new HashSet<Product_SKU>();

	@OneToMany(mappedBy = "product" , cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Product_Image> product_Image= new HashSet<Product_Image>();


	public Product() {
		// TODO Auto-generated constructor stub
	}

	public Product(Long id, String description_details, String description_list, String product_name, String product_status_id, float price, String search_word, Collection<Category> categories, Set<Product_SKU> product_SKUs, int sold ) {
		this.id= id;
		this.description_details= description_details;
		this.description_list= description_list;
		this.product_name= product_name;
		this.product_status_id= product_status_id;
		this.search_word= search_word;
		this.price= price;
		this.categories= categories;
		this.productSKUs= product_SKUs;
		this.sold = sold;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	public float getPrice() {
		return price;
	}

	public Collection<Category> getCategories() {
		return categories;
	}

	public void setProductSKUs(Set<Product_SKU> productSKUs) {
		this.productSKUs = productSKUs;
	}

	public Set<Product_SKU> getProductSKUs() {
		return productSKUs;
	}

	public void setCategories(Collection<Category> categories) {
		this.categories = categories;
	}

	public void setDescription_details(String description_details) {
		this.description_details = description_details;
	}
	public void setDescription_list(String description_list) {
		this.description_list = description_list;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}public void setProduct_status_id(String product_status_id) {
		this.product_status_id = product_status_id;
	}public void setSearch_word(String search_word) {
		this.search_word = search_word;
	}

//	public Set<Category> getCategories() {
//		return categories;
//	}
	public String getDescription_details() {
		return description_details;
	}
	public String getDescription_list() {
		return description_list;
	}
	public Long getId() {
		return id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public String getProduct_status_id() {
		return product_status_id;
	}
	public String getSearch_word() {
		return search_word;
	}

	public void setProduct_Image(Set<Product_Image> product_Image) {
		this.product_Image = product_Image;
	}

	public Set<Product_Image> getProduct_Image() {
		return product_Image;
	}

	public int getSold() {
		return sold;
	}

	public void setSold(int sold) {
		this.sold = sold;
	}

}
