package it.hqsolutions.lastminute.exercise.test.support;

public class ReceiptTestResult {
	private float salesTaxes;
	private float total;
	private String id;

	public float getSalesTaxes() {
		return salesTaxes;
	}

	public void setSalesTaxes(float salesTaxes) {
		this.salesTaxes = salesTaxes;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
