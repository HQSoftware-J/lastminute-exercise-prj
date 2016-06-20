package it.hqsolutions.lastminute.exercise.test.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.hqsolutions.lastminute.exercise.bl.bo.implementation.ReceiptList;
import it.hqsolutions.lastminute.exercise.bl.bo.interfaces.Receipt;
import it.hqsolutions.lastminute.exercise.datatransform.JsonMapper;
import it.hqsolutions.lastminute.exercise.persistence.dao.interfaces.SalableItemTypeDAO;
import it.hqsolutions.lastminute.exercise.persistence.entity.SalableItem;
import it.hqsolutions.lastminute.exercise.persistence.entity.SalableItemType;
import it.hqsolutions.lastminute.exercise.persistence.entity.dictionary.ItemTypeId;
import it.hqsolutions.lastminute.exercise.test.support.ReceiptTestResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:context/lastminute-exercise-context.xml")
public class ReceiptTest extends AbstractJUnit4SpringContextTests {
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
		ClassLoader cl = this.getClass().getClassLoader();
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
		Resource[] resources;
		try {
			resources = resolver.getResources("classpath*:/mocks/test-input*.json");
			for (Resource resource : resources) {
				String fileName = new String(resource.getFilename());
				itemsReceiptTestLauncher(fileName, "result-" + fileName);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void itemsReceiptTestLauncher(String mockClasspathLocation, String resultMockClasspathLocation) {
		InputStream mock = this.getClass().getResourceAsStream("/mocks/" + mockClasspathLocation);
		List<SalableItem> salableItems = Arrays.asList(jsonMapper.jsonToObject(mock, SalableItem[].class));
		assertNotNull("Failed to create list of items", salableItems);

		InputStream mockResult = this.getClass().getResourceAsStream("/mocks/" + resultMockClasspathLocation);
		ReceiptTestResult receiptTestResult = jsonMapper.jsonToObject(mockResult, ReceiptTestResult.class);
		assertNotNull("Failed to create result", salableItems);

		Receipt receipt = new ReceiptList(receiptTestResult.getId(), salableItems);
		printTestResutlHeader(mockClasspathLocation, receipt);
		itemsReceiptTestChecks(receipt, receiptTestResult);
		printTestResultFooter(mockClasspathLocation);
	}

	private void printTestResultFooter(String mockClasspathLocation) {
		logger.info(new StringBuilder("\nTest with mock: ").append(mockClasspathLocation)
				.append(" succesfully completed\n\n").toString());
	}

	private void printTestResutlHeader(String mockClasspathLocation, Receipt receipt) {
		logger.info(new StringBuilder("\nTest with mock: ").append(mockClasspathLocation).toString());
		logger.info("Simple print");
		logger.info(receipt.printItSimple());
		logger.info("=================================");
		logger.info("Grouped print");
		logger.info(receipt.printItGrouped());
	}

	private void itemsReceiptTestChecks(Receipt receipt, ReceiptTestResult receiptTestResult) {
		// It can never fail :)
		assertEquals("Receipt id check", receiptTestResult.getId(), receipt.getId());
		assertEquals("Receipt sales taxes", receiptTestResult.getSalesTaxes(), receipt.getSalesTaxes(), 0.005f);
		assertEquals("Receipt total", receiptTestResult.getTotal(), receipt.getTotal(), 0.005f);
	}

}
