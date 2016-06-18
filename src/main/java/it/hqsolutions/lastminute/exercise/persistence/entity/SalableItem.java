package it.hqsolutions.lastminute.exercise.persistence.entity;

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
	protected String salableItemTypeId;
	protected float grossPrice;
	protected boolean imported;
	protected String description;

	/**
	 * 
	 * @param salableItemType
	 * @param grossPrice
	 */
	public SalableItem(String salableItemTypeId, String description, float grossPrice) {
		this(salableItemTypeId, description, grossPrice, false);
	}

	/**
	 * 
	 * @param salableItemType
	 * @param grossPrice
	 * @param imported
	 */
	public SalableItem(String salableItemTypeId, String description, float grossPrice, boolean imported) {
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

}
