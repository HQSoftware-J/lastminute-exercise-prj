package it.hqsolutions.lastminute.exercise.persistence.dao.interfaces;

import java.util.List;

import it.hqsolutions.lastminute.exercise.exception.DuplicateIdException;
import it.hqsolutions.lastminute.exercise.persistence.entity.SalableItemType;

public interface SalableItemTypeDAO {
	public int insert(SalableItemType salableItemType) throws DuplicateIdException;

	public List<SalableItemType> loadAll();

	public SalableItemType load(String salableItemTypeId);

	public void delete();
}
