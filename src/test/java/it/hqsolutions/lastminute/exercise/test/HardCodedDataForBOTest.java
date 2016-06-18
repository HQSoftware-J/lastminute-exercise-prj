package it.hqsolutions.lastminute.exercise.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.hqsolutions.lastminute.exercise.bl.bo.SalableItemBO;
import it.hqsolutions.lastminute.exercise.persistence.dao.interfaces.SalableItemTypeDAO;
import it.hqsolutions.lastminute.exercise.persistence.entity.SalableItem;
import it.hqsolutions.lastminute.exercise.persistence.entity.SalableItemType;
import it.hqsolutions.lastminute.exercise.persistence.entity.dictionary.ItemTypeId;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:context/lastminute-exercise-context.xml")
public class HardCodedDataForBOTest extends AbstractJUnit4SpringContextTests {

	private static SalableItemType taxable10;
	private static SalableItemType notaxable;
	@Autowired
	SalableItemTypeDAO salableItemTypeDAOHashMap;

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
	public final void taxableItemNoFreightTest() {
		boTest(ItemTypeId.TAXABLE10, "taxableItemNoFreight", 14.99f, false, 16.49f, 1.50f);
	}

	@Test
	public final void taxableItemFreightTest() {
		boTest(ItemTypeId.TAXABLE10, "taxableItemFreight", 27.99f, true, 32.19f, 2.80f);
	}

	@Test
	public final void noTaxableItemNoFreightTest() {
		boTest(ItemTypeId.NOTAXABLE, "noTaxableItemNoFreight", 0.85f, false, 0.85f, 0);
	}

	@Test
	public final void noTaxableItemFreightTest() {
		boTest(ItemTypeId.NOTAXABLE, "noTaxableItemFreight", 11.25f, true, 11.85f, 0);
	}

	private void boTest(String salableItemTypeId, String description, float grossPrice, boolean imported,
			float effectivePrice, float taxAmount) {
		SalableItem salableItem = new SalableItem(salableItemTypeId, description, grossPrice, imported);
		SalableItemBO salableItemBO = (SalableItemBO) applicationContext.getBean("salableItemBO", salableItem);
		assertEquals(description + " effective price", effectivePrice, salableItemBO.getEffectivePrice(), 0.05);
		assertEquals(description + " tax amount", salableItemBO.getTaxAmount(), taxAmount, 0.05);
		System.out.println(new StringBuilder(description).append(" test case passed").append(salableItem));
	}

}
