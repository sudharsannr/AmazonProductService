package org.amazon.productservice;

import java.util.ArrayList;

public class ReviewsData
{

	private String productId;
	private String productName;
	private Percentage percentage;
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
		percentage = new Percentage(positive_percentage,
				100 - positive_percentage);
		this.tag = tag;
		this.occurences = count;
	}

	public Percentage getPercentage()
	{
		return this.percentage;
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

}
