package it.hqsolutions.lastminute.exercise.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.type.TypeReference;

import it.hqsolutions.lastminute.exercise.bl.bo.TaxCalculator;
import it.hqsolutions.lastminute.exercise.bl.bo.interfaces.Receipt;
import it.hqsolutions.lastminute.exercise.datatransform.JsonMapper;
import it.hqsolutions.lastminute.exercise.persistence.dao.interfaces.SalableItemTypeDAO;
import it.hqsolutions.lastminute.exercise.persistence.entity.SalableItem;
import it.hqsolutions.lastminute.exercise.persistence.entity.SalableItemType;
import it.hqsolutions.lastminute.exercise.persistence.entity.dictionary.ItemTypeId;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:context/lastminute-exercise-context.xml")
public class ListItemsBOTest extends AbstractJUnit4SpringContextTests {
	private static SalableItemType taxable10;
	private static SalableItemType notaxable;
	@Autowired
	SalableItemTypeDAO salableItemTypeDAOHashMap;
	@Autowired
	JsonMapper jsonMapper;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		salableItemTypeDAOHashMap.delete();
		taxable10 = new SalableItemType(ItemTypeId.TAXABLE10, 10.0f);
		salableItemTypeDAOHashMap.insert(taxable10);
		notaxable = new SalableItemType(ItemTypeId.NOTAXABLE);
		salableItemTypeDAOHashMap.insert(notaxable);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void itemsReceiptTest() {
		InputStream input1 = this.getClass().getResourceAsStream("/mocks/input-1.json");
		assertNotNull("Mocks not found", input1);
		List<SalableItem> salableItems = jsonMapper.jsonToObjectList(SalableItem.class, input1);

		// SalableItem[] salableItems = jsonMapper.jsonToObject(input1,
		// SalableItem[].class);
		assertNotNull("Failed to create list of items", salableItems);
	}

	private void itemsReceiptTestChecks(Receipt receipt, String receiptId, Float receiptSalesTaxes, Float receiptTotal,
			int totalItems) {
		assertEquals("Receipt id check", receipt.getId(), receiptId);
		assertEquals("Receipt sales taxes", receipt.getSalesTaxes(), receiptSalesTaxes, 0.005f);
		assertEquals("Receipt total", receipt.getTotal(), receiptTotal, 0.005f);
		assertEquals("Total distinguished items on receipt", receipt.getTotalDistinguishedItems());
	}

}
