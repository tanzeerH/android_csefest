package com.buetcrt.model;

import java.util.ArrayList;

import com.buetcrt.csefest.dao.Products;
import com.google.gson.annotations.SerializedName;

public class ProductsList {
	
	@SerializedName("results")
	private ArrayList<Products> productList;

	public ArrayList<Products> getProductList() {
		return productList;
	}

	public void setProductList(ArrayList<Products> productList) {
		this.productList = productList;
	}

	public ProductsList(ArrayList<Products> productList) {
		super();
		this.productList = productList;
	}

	@Override
	public String toString() {
		return "ProductsList [productList=" + productList + "]";
	}

}
