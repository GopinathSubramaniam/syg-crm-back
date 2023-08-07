package com.syg.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.syg.crm.model.Product;

@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {

}
