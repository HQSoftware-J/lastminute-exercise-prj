package it.hqsolutions.lastminute.exercise.persistence.entity;

public class SalableItemType {
	private String id;
	private double taxPercentage;

	public SalableItemType() {

	}

	public SalableItemType(String id, double taxPercentage) {
		this.id = id;
		this.taxPercentage = taxPercentage;
	}

	public SalableItemType(String id) {
		this.id = id;
	}

	public double getTaxPercentage() {
		return this.taxPercentage;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "SalableItemType [id=" + id + ", taxPercentage=" + taxPercentage + "]";
	}

}
