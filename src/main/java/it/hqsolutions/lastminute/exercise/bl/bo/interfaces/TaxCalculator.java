package it.hqsolutions.lastminute.exercise.bl.bo.interfaces;

public interface TaxCalculator {

	float calculateTaxAmount(String salableItemTypeId, float grossPrice);

	float calculateNetPrice(float grossPrice, float taxAmount);

	float calculateEffectivePrice(boolean imported, float netPrice, float grossPrice);

}