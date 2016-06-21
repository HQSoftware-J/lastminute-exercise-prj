package it.hqsolutions.lastminute.exercise.bl.bo.implementation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import it.hqsolutions.lastminute.exercise.bl.bo.interfaces.Receipt;
import it.hqsolutions.lastminute.exercise.decorator.implementations.PrettyPrinterZeroZeroDot;
import it.hqsolutions.lastminute.exercise.persistence.entity.SalableItem;

/*
 * BO to handle a receipt.
 * It'd be nice if it is an injected bean, but some work to do...
 */
// TODO Make it a bean
public class ReceiptList implements Receipt {
	private String id;
	private List<SalableItem> salableItems;
	private double salesTaxes;
	private double total;
	private double totalDistinguishedItems;

	public ReceiptList(String id) {
		this.id = id;
		this.salableItems = new ArrayList<SalableItem>();
	}

	public ReceiptList(String id, List<SalableItem> salableItems) {
		this.id = id;
		this.salableItems = salableItems;
		salesTaxes = new BigDecimal(salableItems.stream().mapToDouble(SalableItem::getTaxAmount).sum()).doubleValue();
		total = new BigDecimal(salableItems.stream().mapToDouble(SalableItem::getEffectivePrice).sum()).doubleValue();
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
	public double getSalesTaxes() {
		return salesTaxes;
	}

	@Override
	public double getTotal() {
		return total;
	}

	@Override
	public double getTotalDistinguishedItems() {
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
		return new StringBuilder(id).append(":\n");
	}

	private void stringFooter(StringBuilder printedReceipt) {
		PrettyPrinterZeroZeroDot prettyPrinterZeroZeroDot = new PrettyPrinterZeroZeroDot();
		printedReceipt.append("Sales Taxes: " + prettyPrinterZeroZeroDot.prettyDouble(salesTaxes)).append("\n")
				.append("Total: ").append(prettyPrinterZeroZeroDot.prettyDouble(total));
	}

}
