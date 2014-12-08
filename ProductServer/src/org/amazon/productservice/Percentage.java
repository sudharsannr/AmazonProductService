package org.amazon.productservice;

public class Percentage
{
	private double positivePercentage;
	private double negativePercentage;

	public Percentage(double pos, double neg)
	{
		this.positivePercentage = pos;
		this.negativePercentage = neg;
	}

	public double getPositivePercentage()
	{
		return positivePercentage;
	}

	public double getNegativePercentage()
	{
		return negativePercentage;
	}

}
