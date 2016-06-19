package it.hqsolutions.lastminute.exercise.bl.bo;

import it.hqsolutions.lastminute.exercise.persistence.entity.SalableItem;

public class SalableItemInReceipt extends SalableItem {
	int qty;

	public SalableItemInReceipt(String salableItemTypeId, String description, float grossPrice) {
		this(salableItemTypeId, description, grossPrice, false);
	}

	public SalableItemInReceipt(String salableItemTypeId, String description, float grossPrice, boolean imported) {
		super(salableItemTypeId, description, grossPrice, imported);
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

}
