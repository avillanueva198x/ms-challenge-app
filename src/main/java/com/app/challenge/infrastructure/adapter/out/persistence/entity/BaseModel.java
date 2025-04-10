package com.app.challenge.infrastructure.adapter.out.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.Data;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.util.Locale;

@MappedSuperclass
@Data
public abstract class BaseModel {

	@Transient
	@JsonIgnore
	protected static final DecimalFormat integerFormat = new DecimalFormat("###,###", DecimalFormatSymbols.getInstance(Locale.forLanguageTag("es_PE")));

	@Transient
	@JsonIgnore
	protected static final DecimalFormat decimalFormat = new DecimalFormat("###,###.00", DecimalFormatSymbols.getInstance(Locale.forLanguageTag("es_PE")));

	private LocalDateTime created;
	private LocalDateTime modified;
	private LocalDateTime lastLogin;

}
