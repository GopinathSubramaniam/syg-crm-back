package com.syg.crm.model;

import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Media extends MasterEntity {

	private String name;
	private String description;
	private String mediaPath;
	private String mediaUrl;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
	private UserDetail userDetail;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
	private Product product;

}
