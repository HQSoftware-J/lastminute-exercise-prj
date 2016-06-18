package it.hqsolutions.lastminute.exercise.persistence.dao.implementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

import it.hqsolutions.lastminute.exercise.persistence.dao.interfaces.SalableItemTypeDAO;
import it.hqsolutions.lastminute.exercise.persistence.entity.SalableItemType;

public class SalableItemTypeDAOHashMap implements SalableItemTypeDAO {
	private Map<String, SalableItemType> taxableItemTypeCatalogueHM;

	public SalableItemTypeDAOHashMap(Map<String, SalableItemType> taxableItemTypeCatalogueHM) {
		initHashMap();
		this.taxableItemTypeCatalogueHM = taxableItemTypeCatalogueHM;
	}

	public SalableItemTypeDAOHashMap() {
		initHashMap();
	}

	@Override
	public int insert(SalableItemType salableItemType) {
		if (taxableItemTypeCatalogueHM.get(salableItemType.getId()) != null) {
			throw new RuntimeException("Duplicate id in item type catalogue!");
		} else {
			taxableItemTypeCatalogueHM.put(salableItemType.getId(), salableItemType);
		}
		return taxableItemTypeCatalogueHM.size();
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Map.Entry<String, SalableItemType> entry : taxableItemTypeCatalogueHM.entrySet()) {
			SalableItemType salableItemType = entry.getValue();
			stringBuilder.append(salableItemType).append("\n");
		}
		return stringBuilder.toString();
	}

	@Override
	public List<SalableItemType> loadAll() {
		// TODO Implement it
		throw new NotImplementedException(" SalableItemTypeDAOHashMap List<SalableItemType> loadAll() nyi");
	}

	@Override
	public SalableItemType load(String salableItemTypeId) {
		return taxableItemTypeCatalogueHM.get(salableItemTypeId);
	}

	@Override
	public void delete() {
		initHashMap();
	}

	private void initHashMap() {
		this.taxableItemTypeCatalogueHM = new HashMap<String, SalableItemType>();
	}

}
