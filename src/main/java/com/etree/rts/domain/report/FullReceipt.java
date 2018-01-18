package com.etree.rts.domain.report;

import java.io.Serializable;

public class FullReceipt implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3223373813635741566L;

	private Sales[] sales;

	private Payments[] payments;

	private Receipt receipt;

	public Sales[] getSales() {
		return sales;
	}

	public void setSales(Sales[] sales) {
		this.sales = sales;
	}

	public Payments[] getPayments() {
		return payments;
	}

	public void setPayments(Payments[] payments) {
		this.payments = payments;
	}

	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

}
