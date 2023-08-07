package com.syg.crm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.syg.crm.model.Branch;


public interface BranchRepository extends JpaRepository<Branch, Long> {

	Page<Branch> findAllByCompanyId(Long companyId, Pageable page);

	Long countByCompanyId(Long companyId);

}
