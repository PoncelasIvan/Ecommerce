package com.inso.Ecommerce.beans;

public class ProductSellBean {

	private int productId;
	private int cantidad;
	
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}	
	
	public ProductSellBean(){};
	
	public ProductSellBean(int id, int cant){
		this.productId = id;
		this.cantidad = cant;
	}
}
