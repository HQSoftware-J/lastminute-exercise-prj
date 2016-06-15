package it.hqsolutions.lastminute.exercise.datastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class TaxableItemTypeCatalogueHashMap implements TaxableItemTypeCatalogue {
	private Map<String, Float> taxableItemTypeCatalogueHM = new HashMap<String, Float>();

	public TaxableItemTypeCatalogueHashMap(HashMap<String, Float> taxableItemTypeCatalogueHM) {
		this.taxableItemTypeCatalogueHM = taxableItemTypeCatalogueHM;
	}

	public TaxableItemTypeCatalogueHashMap() {
	}

	public int insert(SalableItemType salableItemType) {
		if (taxableItemTypeCatalogueHM.get(salableItemType.getId()) != null) {
			throw new RuntimeException("Duplicate id in item type catalogue!");
		} else {
			taxableItemTypeCatalogueHM.put(salableItemType.getId(), salableItemType.getTaxPercentage());
		}
		return taxableItemTypeCatalogueHM.size();
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (String id : taxableItemTypeCatalogueHM.keySet()) {
			Float percentage = taxableItemTypeCatalogueHM.get(id);
			stringBuilder.append(id).append(":").append(percentage).append("%");
		}
		return stringBuilder.toString();
	}

}
