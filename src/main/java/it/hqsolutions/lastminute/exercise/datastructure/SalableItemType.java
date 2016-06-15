package it.hqsolutions.lastminute.exercise.datastructure;

public class SalableItemType {
	private String id;
	private Float taxPercentage;

	public SalableItemType(String id, Float percentage) {
		this.id = id;
		setTaxPercentage(percentage);
	}

	public SalableItemType(String id) {
		this.id = id;
	}

	public Float getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(Float percentage) {
		this.taxPercentage = percentage == null ? 0f : percentage;
	}

	public String getId() {
		return id;
	}

}
