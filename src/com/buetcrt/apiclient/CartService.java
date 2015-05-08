package com.buetcrt.apiclient;

import retrofit.Callback;
import retrofit.http.GET;

import com.google.gson.JsonElement;

public interface CartService {
	@GET("/classes/Cart?where={\"isOrdered\":false}")
	void getActiveCart(Callback<JsonElement> cart);
}
