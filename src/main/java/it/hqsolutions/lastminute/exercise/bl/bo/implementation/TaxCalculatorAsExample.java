package it.hqsolutions.lastminute.exercise.bl.bo.implementation;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;

import it.hqsolutions.lastminute.exercise.bl.bo.interfaces.TaxCalculator;
import it.hqsolutions.lastminute.exercise.persistence.dao.interfaces.SalableItemTypeDAO;

/**
 * Inheritance over composition? Both!
 * 
 * @author giorgio
 * @since v 0.1-SNAPSHOT
 *
 */

public class TaxCalculatorAsExample implements TaxCalculator {
	@Autowired
	SalableItemTypeDAO salableItemTypeDAOHashMap;

	/**
	 * 
	 * @param salableItemType
	 * @param grossPrice
	 */

	/**
	 * 
	 * @param salableItemType
	 * @param grossPrice
	 * @param imported
	 */
	public TaxCalculatorAsExample() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.hqsolutions.lastminute.exercise.bl.bo.TaxCalculatorInterface#
	 * calculateTaxAmount(java.lang.String, double)
	 */
	@Override
	public double calculateTaxAmount(String salableItemTypeId, double grossPrice, boolean imported) {
		return roundUp05(
				calculateGrossPriceTaxes(salableItemTypeId, grossPrice) + calculateFreightTaxes(grossPrice, imported));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.hqsolutions.lastminute.exercise.bl.bo.TaxCalculatorInterface#
	 * calculateEffectivePrice(boolean, double, double)
	 */
	@Override
	public double calculateEffectivePrice(double grossPrice, double taxAmount) {
		return Precision.round(taxAmount + grossPrice, 2, BigDecimal.ROUND_HALF_EVEN);
	}

	private double calculateFreightTaxes(double grossPrice, boolean imported) {
		return imported ? roundUp05(grossPrice * .05) : 0;
	}

	private double calculateGrossPriceTaxes(String salableItemTypeId, double grossPrice) {
		return roundUp05(salableItemTypeDAOHashMap.load(salableItemTypeId).getTaxPercentage() / 100 * grossPrice);
	}

	private double roundUp05(double amount) {
		return Math.ceil((amount) * 20) / 20.0;
	}

	private double round(double amount, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(amount);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

}
