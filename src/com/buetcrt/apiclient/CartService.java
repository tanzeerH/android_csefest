package com.buetcrt.apiclient;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;

public interface CartService {
	@GET("/classes/Cart?where={'isOrdered':false}")
	void getActiveCart(Callback<Response> cart);
}
