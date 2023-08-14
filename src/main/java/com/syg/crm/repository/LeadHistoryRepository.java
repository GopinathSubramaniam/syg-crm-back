package com.syg.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syg.crm.model.LeadHistory;

import jakarta.transaction.Transactional;

@Transactional
public interface LeadHistoryRepository extends JpaRepository<LeadHistory, Long> {

	List<LeadHistory> findAllByLeadId(Long leadId);

	void deleteAllByLeadIdIn(List<Long> ids);
}
