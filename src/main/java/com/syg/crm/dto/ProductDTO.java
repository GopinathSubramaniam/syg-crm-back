package com.syg.crm.dto;

import java.util.ArrayList;
import java.util.List;

import com.syg.crm.model.Product;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDTO {

	private Product product;

	private List<Long> deletedMediaIds = new ArrayList<>();

}
