package it.hqsolutions.lastminute.exercise.datastructure;

import org.apache.commons.math3.util.Precision;

/**
 * Item thaht could be sale. Doubtful whether setPrice make sense, unless
 * unlikely realtime changes (well, if you were on Zimbabwe some times ago...).
 * 
 * A set for itemType it'd be clearly a non sense (a book can't turn into a
 * pill)
 * 
 * @author giorgio
 * @since v 0.1-SNAPSHOT
 *
 */

public class SalableItem {
	private SalableItemType salableItemType;
	private float grossPrice;
	private boolean imported;
	private float netPrice;
	private float effectivePrice;

	/**
	 * 
	 * @param salableItemType
	 * @param grossPrice
	 */
	public SalableItem(SalableItemType salableItemType, float grossPrice) {
		this(salableItemType, grossPrice, false);
	}

	/**
	 * 
	 * @param salableItemType
	 * @param grossPrice
	 * @param imported
	 */
	public SalableItem(SalableItemType salableItemType, float grossPrice, boolean imported) {
		this.salableItemType = salableItemType;
		this.imported = imported;
		setGrossPrice(grossPrice);
	}

	public boolean isImported() {
		return imported;
	}

	public void setImported(boolean imported) {
		this.imported = imported;
	}

	public float getGrossPrice() {
		return grossPrice;
	}

	public void setGrossPrice(float grossPrice) {
		this.grossPrice = grossPrice;
		setNetPrice();
		setEffectivePrice();
	}

	public SalableItemType getTaxableItemType() {
		return salableItemType;
	}

	private void setNetPrice() {
		this.netPrice = Precision.round(this.grossPrice + roundTax(), 2);
	}

	public float getNetPrice() {
		return netPrice;
	}

	private void setEffectivePrice() {
		this.effectivePrice = imported ? Precision.round(netPrice + grossPrice * .05f, 2) : netPrice;
	}

	public float getEffectivePrice() {
		return Precision.round(effectivePrice, 2);
	}

	private float roundTax() {
		float netPriceToRound = this.getTaxableItemType().getTaxPercentage() / 100 * this.grossPrice * 20;
		float result = Math.round(netPriceToRound) / 20f;
		return result;
	}

	public float retrieveTaxAmount() {
		return Precision.round(netPrice, 2) - Precision.round(grossPrice, 2);
	}

}
