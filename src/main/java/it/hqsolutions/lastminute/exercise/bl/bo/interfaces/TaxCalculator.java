package it.hqsolutions.lastminute.exercise.bl.bo.interfaces;

public interface TaxCalculator {

	double calculateEffectivePrice(double grossPrice, double taxAmount);

	double calculateTaxAmount(String salableItemTypeId, double grossPrice, boolean imported);

}