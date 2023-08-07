package com.syg.crm.model;

import com.syg.crm.enums.SeqType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Seq {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	@Enumerated(EnumType.STRING)
	private SeqType type;

	@Column(unique = true)
	private String prefix;

	private Long value;

	public Seq() {
	}

	public Seq(SeqType type) {
		this.type = type;
		this.value = 0L;

		if (SeqType.A.equals(type))
			this.prefix = "AD";
		else
			this.prefix = type.toString();

	}

}
