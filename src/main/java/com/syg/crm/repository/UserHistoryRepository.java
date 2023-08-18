package com.syg.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syg.crm.model.UserHistory;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {

	
}
