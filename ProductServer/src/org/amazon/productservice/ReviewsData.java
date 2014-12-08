package org.amazon.productservice;

import java.util.ArrayList;

public class ReviewsData
{

	private String productId;
	private String productName;
	private double positivePercentage;
	private ArrayList<String> tag;
	private ArrayList<String> occurences;

	// Must have no-argument constructor
	public ReviewsData()
	{

	}

	public ReviewsData(String product_id, String product_name,
			double positive_percentage, ArrayList<String> tag,
			ArrayList<String> count)
	{
		this.productId = product_id;
		this.productName = product_name;
		this.positivePercentage = positive_percentage;
		this.tag = tag;
		this.occurences = count;
	}

	public double getPositivePercentage()
	{
		return this.positivePercentage;
	}

	public String getProductId()
	{
		return productId;
	}

	public String getProductName()
	{
		return productName;
	}

	public ArrayList<String> getTag()
	{
		return tag;
	}

	public ArrayList<String> getOccurences()
	{
		return occurences;
	}

	@Override
	public String toString()
	{
		return new StringBuffer(" Product Id : ").append(this.productId)
				.append(" Product Name : ").append(this.productName)
				.append(" Positive % : ").append(this.positivePercentage)
				.append(" Negative % : ").append(this.tag)
				.append(" Top K words : ").append(this.occurences).toString();
	}

}
