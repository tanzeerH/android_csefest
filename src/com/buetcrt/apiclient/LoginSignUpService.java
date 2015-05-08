package com.buetcrt.apiclient;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.QueryMap;

import com.buetcrt.model.SignUpCredentials;
import com.buetcrt.model.User;
import com.google.gson.JsonElement;

public interface LoginSignUpService {
	@GET("/login")
	void login(@QueryMap Map<String, String> body, Callback<User> callback);
	
	@POST("/users")
	void signup(@Body SignUpCredentials credentials, Callback<User> callback);
	
	@POST("/logout")
	void logout(Callback<JsonElement> callback);

}
