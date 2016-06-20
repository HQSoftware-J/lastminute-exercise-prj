package it.hqsolutions.lastminute.exercise.bl.bo.implementation;

import java.math.BigDecimal;

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

	/* (non-Javadoc)
	 * @see it.hqsolutions.lastminute.exercise.bl.bo.TaxCalculatorInterface#calculateTaxAmount(java.lang.String, float)
	 */
	@Override
	public float calculateTaxAmount(String salableItemTypeId, float grossPrice) {
		return Precision.round(salableItemTypeDAOHashMap.load(salableItemTypeId).getTaxPercentage() / 100 * grossPrice,
				2, BigDecimal.ROUND_HALF_EVEN);
	}

	/* (non-Javadoc)
	 * @see it.hqsolutions.lastminute.exercise.bl.bo.TaxCalculatorInterface#calculateNetPrice(float, float)
	 */
	@Override
	public float calculateNetPrice(float grossPrice, float taxAmount) {
		return Precision.round(grossPrice + taxAmount, 2);
	}

	/* (non-Javadoc)
	 * @see it.hqsolutions.lastminute.exercise.bl.bo.TaxCalculatorInterface#calculateEffectivePrice(boolean, float, float)
	 */
	@Override
	public float calculateEffectivePrice(boolean imported, float netPrice, float grossPrice) {
		return imported ? Precision.round(netPrice + grossPrice * .05f, 2) : Precision.round(netPrice, 2);
	}

}
