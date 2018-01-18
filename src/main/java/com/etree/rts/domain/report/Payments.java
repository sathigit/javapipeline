package com.etree.rts.domain.report;

import java.io.Serializable;

public class Payments implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2486812427540214687L;

	private String transactionId;

	private String receipt;

	private String paymentMethodName;

	private String currency;

	private String pos;

	private String amount;

	private String revision;

	private String credit;

	private Double postAuthAmount;

	private String roundingError;

	private String transportKey;

	private String cancelTransactionId;

	private String inputAmount;

	private String finalized;

	private String currencyKey;

	private String transactionToken;

	private String revenueAccount;

	private String receiptIndex;

	private String deleted;

	private String cardHolder;

	private String transactionTime;

	private Double preAuthAmount;

	private String paymentMethodNr;

	private String voucherNumber;

	private String uuid;

	private String cashier;

	private String paymentMethod;

	private String cardNumber;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public Double getPostAuthAmount() {
		return postAuthAmount;
	}

	public void setPostAuthAmount(Double postAuthAmount) {
		this.postAuthAmount = postAuthAmount;
	}

	public String getRoundingError() {
		return roundingError;
	}

	public void setRoundingError(String roundingError) {
		this.roundingError = roundingError;
	}

	public String getTransportKey() {
		return transportKey;
	}

	public void setTransportKey(String transportKey) {
		this.transportKey = transportKey;
	}

	public String getCancelTransactionId() {
		return cancelTransactionId;
	}

	public void setCancelTransactionId(String cancelTransactionId) {
		this.cancelTransactionId = cancelTransactionId;
	}

	public String getInputAmount() {
		return inputAmount;
	}

	public void setInputAmount(String inputAmount) {
		this.inputAmount = inputAmount;
	}

	public String getFinalized() {
		return finalized;
	}

	public void setFinalized(String finalized) {
		this.finalized = finalized;
	}

	public String getCurrencyKey() {
		return currencyKey;
	}

	public void setCurrencyKey(String currencyKey) {
		this.currencyKey = currencyKey;
	}

	public String getTransactionToken() {
		return transactionToken;
	}

	public void setTransactionToken(String transactionToken) {
		this.transactionToken = transactionToken;
	}

	public String getRevenueAccount() {
		return revenueAccount;
	}

	public void setRevenueAccount(String revenueAccount) {
		this.revenueAccount = revenueAccount;
	}

	public String getReceiptIndex() {
		return receiptIndex;
	}

	public void setReceiptIndex(String receiptIndex) {
		this.receiptIndex = receiptIndex;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public String getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

	public Double getPreAuthAmount() {
		return preAuthAmount;
	}

	public void setPreAuthAmount(Double preAuthAmount) {
		this.preAuthAmount = preAuthAmount;
	}

	public String getPaymentMethodNr() {
		return paymentMethodNr;
	}

	public void setPaymentMethodNr(String paymentMethodNr) {
		this.paymentMethodNr = paymentMethodNr;
	}

	public String getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCashier() {
		return cashier;
	}

	public void setCashier(String cashier) {
		this.cashier = cashier;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
}
