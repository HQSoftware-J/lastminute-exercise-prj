package it.hqsolutions.lastminute.exercise.bl.bo;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;

import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;

import it.hqsolutions.lastminute.exercise.persistence.dao.interfaces.SalableItemTypeDAO;
import it.hqsolutions.lastminute.exercise.persistence.entity.SalableItem;

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

public class SalableItemBO extends SalableItem {
	private float taxPercentage;
	private float netPrice;
	private float effectivePrice;
	private float taxAmount;
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
	public SalableItemBO(SalableItem salableItem) {
		this(salableItem.getSalableItemTypeId(), salableItem.getDescription(), salableItem.getGrossPrice(),
				salableItem.isImported());
	}

	private SalableItemBO(String salableItemTypeId, String description, float grossPrice, boolean imported) {
		super(salableItemTypeId, description, grossPrice, imported);
	}

	private void setNetPrice() {
		this.netPrice = Precision.round(this.grossPrice + taxAmount, 2);
	}

	public float getNetPrice() {
		return netPrice;
	}

	private void setEffectivePrice() {
		this.effectivePrice = imported ? Precision.round(netPrice + grossPrice * .05f, 2)
				: Precision.round(netPrice, 2);
	}

	public float getEffectivePrice() {
		return effectivePrice;
	}

	private void setTaxAmount() {
		taxAmount = Precision.round(taxPercentage / 100 * this.grossPrice, 2, BigDecimal.ROUND_HALF_EVEN);
	}

	public float getTaxAmount() {
		return taxAmount;
	}

	@PostConstruct
	void computeAll() {
		taxPercentage = salableItemTypeDAOHashMap.load(salableItemTypeId).getTaxPercentage();
		setTaxAmount();
		setNetPrice();
		setEffectivePrice();
	}
}
