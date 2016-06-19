package it.hqsolutions.lastminute.exercise.persistence.dao.implementation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;

import it.hqsolutions.lastminute.exercise.persistence.dao.interfaces.SalableItemTypeDAO;
import it.hqsolutions.lastminute.exercise.persistence.entity.SalableItemType;

/**
 * Description: it was intended as a showcase for java8 stream, but you cna find
 * such a showcase in it.hqsolutions.lastminute.exercise.bl.ReceiptList class
 * 
 * @author giorgio
 *
 */
public class SalableItemTypeDAOList implements SalableItemTypeDAO {
	private List<SalableItemType> taxableItemTypeCatalogueList = new ArrayList<SalableItemType>();

	public SalableItemTypeDAOList(List<SalableItemType> taxableItemTypeCatalogueList) {
		this.taxableItemTypeCatalogueList = taxableItemTypeCatalogueList;
	}

	public SalableItemTypeDAOList() {
	}

	@Override
	public int insert(SalableItemType salableItemType) {
		// TODO Implement it
		throw new NotImplementedException(" SalableItemTypeDAOList int insert(SalableItemType salableItemType) nyi");
	}

	@Override
	public String toString() {
		// TODO Implement it
		throw new NotImplementedException(" SalableItemTypeDAOList int insert(SalableItemType salableItemType) nyi");
	}

	@Override
	public List<SalableItemType> loadAll() {
		// TODO Implement it
		throw new NotImplementedException("SalableItemTypeDAOList List<SalableItemType> loadAll() nyi");
	}

	@Override
	public SalableItemType load(String salableItemTypeId) {
		// TODO Implement it
		throw new NotImplementedException("SalableItemTypeDAOList SalableItemType load(String SalableItemTypeId) nyi");
	}

	@Override
	public void delete() {
		// TODO Implement it
		throw new NotImplementedException("SalableItemTypeDAOList SalableItemType delete() nyi");
	}

}
