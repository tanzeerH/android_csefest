package com.buetcrt.model;

public class Cart {
	private String __type = "Pointer";
	private String className = "Cart";
	private String objectId;

	public Cart(String cartId) {
		objectId = cartId;
	}

	public String get__type() {
		return __type;
	}

	public void set__type(String __type) {
		this.__type = __type;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
}
