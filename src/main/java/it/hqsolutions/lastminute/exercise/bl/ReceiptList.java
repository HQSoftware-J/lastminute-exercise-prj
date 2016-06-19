package it.hqsolutions.lastminute.exercise.bl;

import java.util.ArrayList;
import java.util.List;

import it.hqsolutions.lastminute.exercise.bl.bo.interfaces.Receipt;
import it.hqsolutions.lastminute.exercise.persistence.entity.SalableItem;

public class ReceiptList implements Receipt {
	private String id;
	private List<SalableItem> salableItems;
	private float salesTaxes;
	private float total;
	private float totalDistinguishedItems;

	public ReceiptList(String id) {
		this.id = id;
		this.salableItems = new ArrayList<SalableItem>();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public List<SalableItem> getSalableItems() {
		return salableItems;
	}

	@Override
	public float getSalesTaxes() {
		return salesTaxes;
	}

	@Override
	public float getTotal() {
		return total;
	}

	@Override
	public float getTotalDistinguishedItems() {
		return totalDistinguishedItems;
	}

	@Override
	public int addSalableItem(SalableItem salableItem) {
		salableItems.add(salableItem);
		salesTaxes += salableItem.getTaxAmount();
		total += salableItem.getEffectivePrice();
		return salableItems.size();
	}

	@Override
	public String printSimpleReceipt() {
		StringBuilder printedReceipt = new StringBuilder(id);
		salableItems
				.forEach(salableItem -> printedReceipt.append("1 ").append(salableItem.toSimpleString()).append("\n"));
		printedReceipt.append("Sales Taxes: " + salesTaxes).append("\n").append("Total: ").append(total);
		return printedReceipt.toString();
	}
}
