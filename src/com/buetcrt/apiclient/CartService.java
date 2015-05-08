package com.buetcrt.apiclient;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

import com.buetcrt.model.Order;
import com.google.gson.JsonElement;

public interface CartService {
	@GET("/classes/Cart?where={\"isOrdered\":false}")
	void getActiveCart(Callback<JsonElement> cart);
	
	@POST("/classes/Order")
	void addToCart(@Body Order order, Callback<JsonElement> response);
	
	@GET("/classes/Order")
	void getOrders(Callback<JsonElement> response);
	
	@POST("/functions/checkout")
	void checkout(Callback<JsonElement> response);
}
