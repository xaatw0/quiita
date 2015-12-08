package fxfxml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;

public class FXMLBean {

	private String name;
	public String getName(){
		return name;
	}
	public void setName(String value){
		name = value;
	}

	private String address;

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	private boolean flag;

	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	private int count;

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	private Color foreground;

	public Color getForeground() {
		return foreground;
	}
	public void setForeground(Color foreground) {
		this.foreground = foreground;
	}

	private Color background;

	public Color getBackground() {
		return background;
	}
	public void setBackground(Color background) {
		this.background = background;
	}

	private double price;

	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	private double price2;

	public double getPrice2() {
		return price2;
	}
	public void setPrice2(double price2) {
		this.price2 = price2;
	}

	private List<Integer> sizes;

	public List<Integer> getSizes() {
		return sizes;
	}

	public void setSizes(List<Integer> sizes) {
		this.sizes = sizes;
	}

	private Double discounts;

	public Double getDiscounts() {
		return discounts;
	}

	public void setDiscounts(Double discounts) {
		this.discounts = discounts;
	}

	private Map<String, String> profits;

	public Map<String, String> getProfits() {
		return profits;
	}

	public void setProfits(Map<String, String> profits) {
		this.profits = profits;
	}
	private List<String> products = new ArrayList<String>();

	public List<String> getProducts() {
		return products;
	}
	/**
	public void setProducts(List<String> products) {
		this.products = products;
	}*/

	public Long getInventory() {
		return inventory;
	}

	public void setInventory(Long inventory) {
		this.inventory = inventory;
	}

	private Long inventory;

	private Map<String,String> abbreviation = new HashMap<>();

	public Map<String,String> getAbbreviation() {
		return abbreviation;
	}
	/**
	public void setAbbreviation(Map<String,String> abbreviation) {
		this.abbreviation = abbreviation;
	}**/

}
