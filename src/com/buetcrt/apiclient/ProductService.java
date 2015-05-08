package com.buetcrt.apiclient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.QueryMap;

import com.buetcrt.csefest.dao.Products;
import com.buetcrt.model.ProductsList;
import com.buetcrt.model.SearchQuery;
import com.buetcrt.model.User;
import com.google.gson.JsonElement;

public interface ProductService {
	@GET("/classes/Product")
	void getProducts(Callback<JsonElement> callback);
	
	
	@POST("/functions/search")
	void getSearchResult(@Body SearchQuery searchQuery,Callback<JsonElement> callback);

}
