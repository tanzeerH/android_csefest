package com.buetcrt.apiclient;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

import com.buetcrt.model.User;

public interface GitHubService {
	@GET("/users/{user}")
	void getUser(@Path("user") String userName, Callback<User> callback);
}
