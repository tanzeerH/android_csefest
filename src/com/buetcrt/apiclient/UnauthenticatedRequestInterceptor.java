package com.buetcrt.apiclient;

import retrofit.RequestInterceptor;

public class UnauthenticatedRequestInterceptor implements RequestInterceptor {

	@Override
	public void intercept(RequestFacade request) {
		request.addHeader("X-Parse-Application-Id", "R6kBbmhFNsPv44ekZbLlC6hq7JZ7b4fWT5G3H3GN");
		request.addHeader("Content-Type", "application/json");
		request.addHeader("X-Parse-REST-API-Key", "QHh6SwA97ioIo8ZkmEczrpFr8jZB5G5rYybrlbpO");
	}

}
