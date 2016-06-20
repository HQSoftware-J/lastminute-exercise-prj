package it.hqsolutions.lastminute.exercise.persistence.entity;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.hqsolutions.lastminute.exercise.bl.bo.interfaces.TaxCalculator;

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

@Configurable
public class SalableItem {
	@Autowired
	TaxCalculator taxCalculator;
	protected String salableItemTypeId;
	protected float grossPrice;
	protected boolean imported;
	protected String description;
	protected float netPrice;
	protected float effectivePrice;
	protected float taxAmount;

	/**
	 * 
	 * @param salableItemType
	 * @param grossPrice
	 */
	public SalableItem(@JsonProperty("salableItemTypeId") String salableItemTypeId,
			@JsonProperty("description") String description, @JsonProperty("grossPrice") float grossPrice) {
		this(salableItemTypeId, description, grossPrice, false);
	}

	/**
	 * 
	 * @param salableItemType
	 * @param grossPrice
	 * @param imported
	 */
	@JsonCreator
	public SalableItem(@JsonProperty("salableItemTypeId") String salableItemTypeId,
			@JsonProperty("description") String description, @JsonProperty("grossPrice") float grossPrice,
			@JsonProperty("imported") boolean imported) {
		this.salableItemTypeId = salableItemTypeId;
		this.description = description;
		this.grossPrice = grossPrice;
		this.imported = imported;
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
	}

	public String getSalableItemTypeId() {
		return salableItemTypeId;
	}

	public String getDescription() {
		return description;
	}

	public float getNetPrice() {
		return netPrice;
	}

	public float getEffectivePrice() {
		return effectivePrice;
	}

	public float getTaxAmount() {
		return taxAmount;
	}

	public Integer getHashCode() {
		return new Integer(hashCode());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + Float.floatToIntBits(effectivePrice);
		result = prime * result + Float.floatToIntBits(grossPrice);
		result = prime * result + (imported ? 1231 : 1237);
		result = prime * result + Float.floatToIntBits(netPrice);
		result = prime * result + ((salableItemTypeId == null) ? 0 : salableItemTypeId.hashCode());
		result = prime * result + Float.floatToIntBits(taxAmount);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalableItem other = (SalableItem) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (Float.floatToIntBits(effectivePrice) != Float.floatToIntBits(other.effectivePrice))
			return false;
		if (Float.floatToIntBits(grossPrice) != Float.floatToIntBits(other.grossPrice))
			return false;
		if (imported != other.imported)
			return false;
		if (Float.floatToIntBits(netPrice) != Float.floatToIntBits(other.netPrice))
			return false;
		if (salableItemTypeId == null) {
			if (other.salableItemTypeId != null)
				return false;
		} else if (!salableItemTypeId.equals(other.salableItemTypeId))
			return false;
		if (Float.floatToIntBits(taxAmount) != Float.floatToIntBits(other.taxAmount))
			return false;
		return true;
	}

	public String toStringForReceipt(int howMany) {
		return new StringBuilder(Integer.toString(howMany)).append(imported ? " imported " : " ").append(description)
				.append(":").append(effectivePrice * howMany).toString();
	}

	@PostConstruct
	private void calculateTax() {
		this.taxAmount = taxCalculator.calculateTaxAmount(salableItemTypeId, grossPrice);
		this.netPrice = taxCalculator.calculateNetPrice(grossPrice, taxAmount);
		this.effectivePrice = taxCalculator.calculateEffectivePrice(imported, netPrice, grossPrice);
	}

}
