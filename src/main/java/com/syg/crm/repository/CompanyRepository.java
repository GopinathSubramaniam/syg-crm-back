package com.syg.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syg.crm.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{

}
