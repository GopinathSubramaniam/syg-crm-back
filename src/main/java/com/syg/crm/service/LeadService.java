package com.syg.crm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.syg.crm.dto.AppDTO;
import com.syg.crm.dto.LeadDTO;
import com.syg.crm.enums.LeadStatus;
import com.syg.crm.enums.Operation;
import com.syg.crm.enums.Screen;
import com.syg.crm.enums.UserType;
import com.syg.crm.model.Lead;
import com.syg.crm.model.Task;
import com.syg.crm.model.UserHistory;
import com.syg.crm.repository.LeadRepository;
import com.syg.crm.repository.TaskRepository;
import com.syg.crm.repository.UserHistoryRepository;
import com.syg.crm.util.PageRes;
import com.syg.crm.util.Util;

@Service
public class LeadService {

	@Autowired
	private LeadRepository leadRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserHistoryRepository userHistoryRepository;

	public Lead create(AppDTO appDTO, Lead lead) {

		try {
			UserHistory uh = new UserHistory(Screen.L);
			uh.setField("JSON");
			uh.setValue(Util.toString(lead));

			Lead l = new Lead();
			if (lead.getId() != null) {

				l = leadRepository.findById(lead.getId()).get();

				l.setFirstName(lead.getFirstName());
				l.setLastName(lead.getLastName());
				l.setEmail(lead.getEmail());
				l.setMobile(lead.getMobile());
				l.setLeadSource(lead.getLeadSource());
				l.setAddress(lead.getAddress());
				l.setCity(lead.getCity());
				l.setState(lead.getState());
				l.setCountry(lead.getCountry());
				l.setDescription(lead.getDescription());
				l.setTags(lead.getTags());
				l.setZip(lead.getZip());

				l.setUserDetail(lead.getUserDetail());
				l.setCreatedUserDetail(appDTO.getUserDetail());

				lead = leadRepository.saveAndFlush(l);

				uh.setOperation(Operation.U);
			} else {
				lead.setCreatedUserDetail(appDTO.getUserDetail());
				lead = leadRepository.saveAndFlush(lead);
				uh.setOperation(Operation.I);
			}

			uh.setDataId(lead.getId());
			userHistoryRepository.saveAndFlush(uh);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lead;
	}

	public PageRes findAll(AppDTO appDto, Integer perPage, Integer pageNo, List<LeadStatus> statusList,
			String searchTxt) {
		PageRes res = new PageRes();

		Pageable pagable = PageRequest.of(pageNo, perPage);

		Page<Lead> page = null;

		if (statusList == null || statusList.size() == 0) {
			statusList.add(LeadStatus.N);
			statusList.add(LeadStatus.O);
			statusList.add(LeadStatus.L);
		}
		// Find all leads
		if (UserType.SA.equals(appDto.getUserType()))
			page = leadRepository.searchLead(statusList, searchTxt, pagable);
		// Find all leads belongs to the branch
		else if (UserType.A.equals(appDto.getUserType()))
			page = leadRepository.searchLeadByBranchId(statusList, appDto.getBranchId(), searchTxt, pagable);

		// Find all leads belongs to the particular loggedin user
		else
			page = leadRepository.searchLeadByUserDetailId(statusList, appDto.getUserDetailId(), searchTxt, pagable);

		res = new PageRes(page);

		return res;
	}

	public List<Lead> findAllByLeadIds(List<Long> leadIds) {
		List<Lead> leads = leadRepository.findAllById(leadIds);
		return leads;
	}

	public void deleteInBatch(List<Long> ids) {
		leadRepository.deleteAllByIdInBatch(ids);
	}

	public void updateStatusInBulk(List<Long> ids, LeadStatus status) {

		List<Lead> leads = leadRepository.findAllById(ids);
		List<UserHistory> histories = new ArrayList<>();

		for (Lead lead : leads) {
			lead.setStatus(status);

			UserHistory uh = new UserHistory(Screen.L);
			uh.setField("Status");
			uh.setValue(Util.getLeadStatusAbbr(status));
			uh.setOperation(Operation.U);
			uh.setDataId(lead.getId());
			histories.add(uh);
		}

		leadRepository.saveAllAndFlush(leads);
		userHistoryRepository.saveAllAndFlush(histories);
	}

	public void updateTags(List<Long> leadIds, String tags) {
		List<Lead> leads = leadRepository.findAllById(leadIds);
		List<UserHistory> histories = new ArrayList<>();

		for (Lead lead : leads) {
			lead.setTags(tags);

			UserHistory uh = new UserHistory(Screen.L);
			uh.setField("Tags");
			uh.setValue(tags);
			uh.setOperation(Operation.U);
			uh.setDataId(lead.getId());
			histories.add(uh);
		}
		leadRepository.saveAllAndFlush(leads);
		userHistoryRepository.saveAllAndFlush(histories);
	}

	public List<Lead> search(String searchTxt) {
		Pageable pagable = PageRequest.of(0, 200);
		List<LeadStatus> status = new ArrayList<>();
		status.add(LeadStatus.L);
		status.add(LeadStatus.N);
		status.add(LeadStatus.O);

		Page<Lead> page = leadRepository.searchLead(status, searchTxt, pagable);
		return page.toList();
	}

	public LeadDTO detail(Long leadId) {
		LeadDTO dto = new LeadDTO();
		Optional<Lead> opt = leadRepository.findById(leadId);
		if (opt.isPresent()) {
			Lead l = opt.get();
			dto.setLead(l);

			List<Task> tasks = taskRepository.findByLeadIds(leadId.toString());
			dto.setTasks(tasks);

			List<UserHistory> histories = userHistoryRepository.findAllByDataIdAndScreen(l.getId(), Screen.L);
			dto.setHistory(histories);

		}
		return dto;
	}

}
