package com.cromecast.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product implements Serializable  {
	@Id
 	@Column(nullable = false)
    private Integer id;	
	@Column(nullable = false)
    private String productname;
	@Column(nullable = false)
    private String productype;
	@Column(nullable = false)
    private Integer quantity;
	@Column(nullable = false)
    private Integer promotion;
	
	protected Product() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
    }
        
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getProductype() {
		return productype;
	}

	public void setProductype(String productype) {
		this.productype = productype;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getPromotion() {
		return promotion;
	}

	public void setPromotion(Integer promotion) {
		this.promotion = promotion;
	}

	private static final long serialVersionUID = -6551584908286785959L;
	
	
    
}