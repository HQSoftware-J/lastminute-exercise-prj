package it.hqsolutions.lastminute.exercise.persistence.entity;

public class SalableItemType {
	private String id;
	private Float taxPercentage;

	public SalableItemType(String id, Float taxPercentage) {
		this.id = id;
		this.taxPercentage = taxPercentage;
	}

	public SalableItemType(String id) {
		this.id = id;
	}

	public Float getTaxPercentage() {
		return this.taxPercentage == null ? 0f : this.taxPercentage;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "SalableItemType [id=" + id + ", taxPercentage=" + taxPercentage + "]";
	}

}
