package com.inso.Ecommerce.wrappers;

import java.util.List;

import com.inso.Ecommerce.beans.ProductSellBean;

public class ProductWrapper {
	private List<ProductSellBean> products;
	
	public List<ProductSellBean> getProducts(){
		return products;
	}
	public void setProducts(List<ProductSellBean> prods){
		this.products = prods;
	}
}
