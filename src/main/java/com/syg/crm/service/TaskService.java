package com.syg.crm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.syg.crm.dto.AppDTO;
import com.syg.crm.enums.SeqType;
import com.syg.crm.enums.UserType;
import com.syg.crm.model.Task;
import com.syg.crm.repository.TaskRepository;
import com.syg.crm.util.PageRes;
import com.syg.crm.util.Util;

@Service
public class TaskService {

	@Autowired
	private AppService appService;

	@Autowired
	private TaskRepository taskRepository;

	public Task create(AppDTO appDTO, Task task) {
		Task t = new Task();
		if (task.getId() != null) {

			t = taskRepository.findById(task.getId()).get();
			t.setSubject(task.getSubject());
			t.setDescription(task.getDescription());
			t.setStartDate(task.getStartDate());
			t.setDueDate(task.getDueDate());
			t.setEndDate(task.getEndDate());
			t.setPriority(task.getPriority());
			t.setStatus(task.getStatus());
			t.setLeadIds(task.getLeadIds());
			
			task = taskRepository.saveAndFlush(t);
		} else {
			task.setTaskNum(appService.getSeqNum(SeqType.TA));
			task.setUserDetail(appDTO.getUserDetail());
			task = taskRepository.saveAndFlush(task);
		}

		return task;
	}

	public PageRes findAll(AppDTO appDto, Integer perPage, Integer pageNo, String searchTxt, String status,
			String priority, String startDate, String endDate) {
		PageRes res = new PageRes();

		Date sDate = Util.convert(startDate);
		Date eDate = Util.convert(endDate);

		Pageable pagable = PageRequest.of(pageNo, perPage);

		Page<Task> page = null;

		if (UserType.SA.equals(appDto.getUserType())) {
			page = taskRepository.search(searchTxt, status, priority, sDate, eDate, pagable);
		} else if (UserType.A.equals(appDto.getUserType())) {
			page = taskRepository.searchByBranch(appDto.getBranchId(), searchTxt, status, priority, sDate, eDate,
					pagable);
		} else {
			page = taskRepository.searchByUserDetail(appDto.getUserDetailId(), searchTxt, status, priority, sDate,
					eDate, pagable);
		}

		res = new PageRes(page);

		return res;
	}

	public void deleteInBatch(List<Long> ids) {
		taskRepository.deleteAllByIdInBatch(ids);
	}

	public List<Task> findAllByStartDateBetween(String startDate, String endDate) {
		Date sDate = Util.convert(startDate);
		Date eDate = Util.convert(endDate);
		return taskRepository.findAllByStartDateBetween(sDate, eDate);
	}

}
