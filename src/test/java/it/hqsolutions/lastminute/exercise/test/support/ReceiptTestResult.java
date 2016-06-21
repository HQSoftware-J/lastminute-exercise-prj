package it.hqsolutions.lastminute.exercise.test.support;

public class ReceiptTestResult {
	private double salesTaxes;
	private double total;
	private String id;

	public double getSalesTaxes() {
		return salesTaxes;
	}

	public void setSalesTaxes(double salesTaxes) {
		this.salesTaxes = salesTaxes;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
