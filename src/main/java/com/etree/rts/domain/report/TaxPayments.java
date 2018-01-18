package com.etree.rts.domain.report;

import java.io.Serializable;

public class TaxPayments implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -260254379952332093L;

	private String amount;

    private String grossAmount;

    private String currentTaxRate;

    private String netAmount;

    private String salesTax;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(String grossAmount) {
		this.grossAmount = grossAmount;
	}

	public String getCurrentTaxRate() {
		return currentTaxRate;
	}

	public void setCurrentTaxRate(String currentTaxRate) {
		this.currentTaxRate = currentTaxRate;
	}

	public String getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}

	public String getSalesTax() {
		return salesTax;
	}

	public void setSalesTax(String salesTax) {
		this.salesTax = salesTax;
	}

}
