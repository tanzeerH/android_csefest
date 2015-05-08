package com.buetcrt.model;

public class User {
	private String username;
	private String email;
	private String sessionToken;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", email=" + email
				+ ", sessionToken=" + sessionToken + "]";
	}

}
