package com.syg.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syg.crm.enums.Screen;
import com.syg.crm.model.UserHistory;
import com.syg.crm.repository.UserHistoryRepository;

@Service
public class UserHistoryService {

	@Autowired
	private UserHistoryRepository userHistoryRepository;

	public UserHistory create(UserHistory userHistory) {
		return userHistoryRepository.saveAndFlush(userHistory);
	}

	public List<UserHistory> findAll(Long dataId, Screen screen) {
		return userHistoryRepository.findAllByDataIdAndScreen(dataId, screen);
	}

}
