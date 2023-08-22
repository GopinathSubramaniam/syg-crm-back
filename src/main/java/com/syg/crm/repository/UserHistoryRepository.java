package com.syg.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syg.crm.enums.Screen;
import com.syg.crm.model.UserHistory;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {

	public List<UserHistory> findAllByDataIdAndScreen(Long id, Screen screen);

}
