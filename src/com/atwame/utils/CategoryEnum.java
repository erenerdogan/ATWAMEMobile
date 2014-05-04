package com.atwame.utils;

public enum CategoryEnum {
	GENEL(1),DUYURU(2),EGLENCE(3),HABER(4),KISISEL(5),REKLAM(6);
	
	int value;
	private CategoryEnum(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}
