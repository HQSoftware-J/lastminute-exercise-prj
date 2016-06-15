package it.hqsolutions.lastminute.exercise.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.hqsolutions.lastminute.exercise.datastructure.SalableItem;
import it.hqsolutions.lastminute.exercise.datastructure.SalableItemType;
import it.hqsolutions.lastminute.exercise.datastructure.TaxableItemTypeCatalogue;
import it.hqsolutions.lastminute.exercise.datastructure.TaxableItemTypeCatalogueHashMap;
import it.hqsolutions.lastminute.exercise.datastructure.keyconstants.ItemTypeKeyConstants;

public class DataStructureTest {
	private static TaxableItemTypeCatalogue taxableItemTypeCatalogue;
	private static SalableItemType book;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		book = new SalableItemType(ItemTypeKeyConstants.BOOK, 10.0f);
		taxableItemTypeCatalogue = new TaxableItemTypeCatalogueHashMap();
		taxableItemTypeCatalogue.insert(book);
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void taxableItemPriceTest() {
		SalableItem salableItem = new SalableItem(book, 14.99f);
		float effectivePrice = salableItem.getEffectivePrice();
		float taxAmount = salableItem.retrieveTaxAmount();
		System.out.println("Effective price: " + effectivePrice + " taxAmount: " + taxAmount);
		assertEquals("Effective price", 16.49, effectivePrice, 0.05);
		assertEquals("Tax amount", 1.50, taxAmount, 0.05);
	}

}
