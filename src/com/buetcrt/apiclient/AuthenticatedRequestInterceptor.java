package com.buetcrt.apiclient;

import android.content.Context;

import com.buetcrt.utils.AppUtility;

public class AuthenticatedRequestInterceptor extends UnauthenticatedRequestInterceptor {
	
	private String sessionToken;
	
	public AuthenticatedRequestInterceptor(Context context) {
		sessionToken = AppUtility.getSessionToken(context);
	}
	
	@Override
	public void intercept(RequestFacade request) {
		super.intercept(request);
		request.addHeader("X-Parse-Session-Token", sessionToken);
	}
}
