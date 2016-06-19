package it.hqsolutions.lastminute.exercise.bl.bo.implementation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

	public ReceiptList(String id, List<SalableItem> salableItems) {
		this.id = id;
		this.salableItems = salableItems;
		salesTaxes = new BigDecimal(salableItems.stream().mapToDouble(SalableItem::getTaxAmount).sum()).floatValue();
		total = new BigDecimal(salableItems.stream().mapToDouble(SalableItem::getEffectivePrice).sum()).floatValue();
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
	public String printItSimple() {
		StringBuilder printedReceipt = stringHeader();
		salableItems.forEach(salableItem -> printedReceipt.append(salableItem.toStringForReceipt(1)).append("\n"));
		stringFooter(printedReceipt);
		return printedReceipt.toString();
	}

	@Override
	public String printItGrouped() {
		StringBuilder printedReceipt = stringHeader();
		Map<Integer, List<SalableItem>> groupedSalableItem = salableItems.stream()
				.collect(Collectors.groupingBy(si -> si.getHashCode()));
		groupedSalableItem.forEach(
				(getHashCode, si) -> printedReceipt.append(si.get(0).toStringForReceipt(si.size())).append("\n"));
		stringFooter(printedReceipt);
		return printedReceipt.toString();
	}

	private StringBuilder stringHeader() {
		StringBuilder printedReceipt = new StringBuilder(id).append(":\n");
		return printedReceipt;
	}

	private void stringFooter(StringBuilder printedReceipt) {
		printedReceipt.append("Sales Taxes: " + salesTaxes).append("\n").append("Total: ").append(total);
	}

}
