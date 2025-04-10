package com.app.challenge.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Phone {
	private String number;
	private String cityCode;
	private String countryCode;

}
