package com.syg.crm.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Product extends MasterEntity {

	private String name;
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expiryDate;

	@Column(scale = 2)
	private Double price;

	private Integer active;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
	private Branch branch;
	

}
