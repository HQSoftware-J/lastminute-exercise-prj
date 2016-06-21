package it.hqsolutions.lastminute.exercise.persistence.entity;

import java.text.DecimalFormat;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.hqsolutions.lastminute.exercise.bl.bo.interfaces.TaxCalculator;
import it.hqsolutions.lastminute.exercise.decorator.implementations.PrettyPrinterZeroZeroDot;
import it.hqsolutions.lastminute.exercise.decorator.interfaces.PrettyPrinter;

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
	@Autowired
	PrettyPrinter prettyPrinter;
	protected String salableItemTypeId;
	protected double grossPrice;
	protected boolean imported;
	protected String description;
	protected double effectivePrice;
	protected double taxAmount;

	/**
	 * 
	 * @param salableItemType
	 * @param grossPrice
	 */
	public SalableItem(@JsonProperty("salableItemTypeId") String salableItemTypeId,
			@JsonProperty("description") String description, @JsonProperty("grossPrice") double grossPrice) {
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
			@JsonProperty("description") String description, @JsonProperty("grossPrice") double grossPrice,
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

	public double getGrossPrice() {
		return grossPrice;
	}

	public void setGrossPrice(double grossPrice) {
		this.grossPrice = grossPrice;
	}

	public String getSalableItemTypeId() {
		return salableItemTypeId;
	}

	public String getDescription() {
		return description;
	}

	public double getEffectivePrice() {
		return effectivePrice;
	}

	public double getTaxAmount() {
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
		long temp;
		temp = Double.doubleToLongBits(effectivePrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(grossPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (imported ? 1231 : 1237);
		result = prime * result + ((salableItemTypeId == null) ? 0 : salableItemTypeId.hashCode());
		temp = Double.doubleToLongBits(taxAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((taxCalculator == null) ? 0 : taxCalculator.hashCode());
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
		if (Double.doubleToLongBits(effectivePrice) != Double.doubleToLongBits(other.effectivePrice))
			return false;
		if (Double.doubleToLongBits(grossPrice) != Double.doubleToLongBits(other.grossPrice))
			return false;
		if (imported != other.imported)
			return false;
		if (salableItemTypeId == null) {
			if (other.salableItemTypeId != null)
				return false;
		} else if (!salableItemTypeId.equals(other.salableItemTypeId))
			return false;
		if (Double.doubleToLongBits(taxAmount) != Double.doubleToLongBits(other.taxAmount))
			return false;
		if (taxCalculator == null) {
			if (other.taxCalculator != null)
				return false;
		} else if (!taxCalculator.equals(other.taxCalculator))
			return false;
		return true;
	}

	public String toStringForReceipt(int howMany) {
		return new StringBuilder(Integer.toString(howMany)).append(imported ? " imported " : " ").append(description)
				.append(": ").append(prettyPrinter.prettyDouble(effectivePrice * howMany)).toString();
	}

	@PostConstruct
	private void calculateTax() {
		this.taxAmount = taxCalculator.calculateTaxAmount(salableItemTypeId, grossPrice, imported);
		this.effectivePrice = taxCalculator.calculateEffectivePrice(grossPrice, taxAmount);
	}

}
