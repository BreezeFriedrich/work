package com.yishu.pojo;

public class CardInfo {
	private String cardNumber;
	private String name;
	private String dnCode;

	public CardInfo() {
	}

	public CardInfo(String cardNumber, String name, String dnCode) {
		this.cardNumber = cardNumber;
		this.name = name;
		this.dnCode = dnCode;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDnCode() {
		return dnCode;
	}

	public void setDnCode(String dnCode) {
		this.dnCode = dnCode;
	}
}
