package it.hqsolutions.lastminute.exercise.decorator.implementations;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import it.hqsolutions.lastminute.exercise.decorator.interfaces.PrettyPrinter;

public class PrettyPrinterZeroZeroDot implements PrettyPrinter {
	@Override
	public final String prettyDouble(final double value) {
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");
		DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
		decimalFormatSymbols.setDecimalSeparator('.');
		decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
		return decimalFormat.format(value);
	}
}
