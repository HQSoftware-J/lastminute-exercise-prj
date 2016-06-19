package it.hqsolutions.lastminute.exercise.bl.bo;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;

import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;

import it.hqsolutions.lastminute.exercise.persistence.dao.interfaces.SalableItemTypeDAO;
import it.hqsolutions.lastminute.exercise.persistence.entity.SalableItem;

/**
 * Inheritance over composition? Both!
 * 
 * @author giorgio
 * @since v 0.1-SNAPSHOT
 *
 */

public class TaxCalculator {
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
	public TaxCalculator() {

	}

	public float calculateTaxAmount(String salableItemTypeId, float grossPrice) {
		return Precision.round(salableItemTypeDAOHashMap.load(salableItemTypeId).getTaxPercentage() / 100 * grossPrice,
				2, BigDecimal.ROUND_HALF_EVEN);
	}

	public float calculateNetPrice(float grossPrice, float taxAmount) {
		return Precision.round(grossPrice + taxAmount, 2);
	}

	public float calculateEffectivePrice(boolean imported, float netPrice, float grossPrice) {
		return imported ? Precision.round(netPrice + grossPrice * .05f, 2) : Precision.round(netPrice, 2);
	}

}
