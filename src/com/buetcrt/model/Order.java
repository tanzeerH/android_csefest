package com.buetcrt.model;

public class Order {

	private Cart cart;
	private Product product;
	private int quantity;
	private int subTotal;

	public Order(String cartId, String productId, int quantity) {
		cart = new Cart(cartId);
		product = new Product(productId);
		this.quantity = quantity;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(int subTotal) {
		this.subTotal = subTotal;
	}



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

	public class Product {
		private String __type = "Pointer";
		private String className = "Product";
		private String objectId;

		public Product(String productId) {
			objectId = productId;
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
}
