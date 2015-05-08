package com.buetcrt.apiclient;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

import com.buetcrt.model.Order;
import com.google.gson.JsonElement;

public interface CartService {
	@GET("/classes/Cart?where={\"isOrdered\":false}")
	void getActiveCart(Callback<JsonElement> cart);
	
	@POST("/classes/Order")
	void addToCart(@Body Order order, Callback<JsonElement> response);
	
//	@GET("/classes/Order?where={\"cart\":{\"__type\":\"Pointer\",\"className\":\"Cart\",\"objectId\":{}}")
	@GET("/classes/Order")
	void getOrders(@Query("where") String cartId, Callback<JsonElement> response);
	
	@POST("/functions/checkout")
	void checkout(Callback<JsonElement> response);
	
	@DELETE(" /classes/Order/{orderId}")
	void deleteFromCart(@Path("orderId") String orderId, Callback<JsonElement> callback);
}
