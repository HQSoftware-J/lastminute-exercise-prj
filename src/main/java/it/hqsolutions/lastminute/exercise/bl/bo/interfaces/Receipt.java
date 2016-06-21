package it.hqsolutions.lastminute.exercise.bl.bo.interfaces;

import java.util.List;

import it.hqsolutions.lastminute.exercise.persistence.entity.SalableItem;

public interface Receipt {
	public String getId();

	public List<SalableItem> getSalableItems();

	public double getSalesTaxes();

	public double getTotal();

	public double getTotalDistinguishedItems();

	public int addSalableItem(SalableItem salableItem);

	public String printItSimple();

	public String printItGrouped();

}
