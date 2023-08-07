package com.syg.crm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.syg.crm.dto.AppDTO;
import com.syg.crm.enums.UserType;
import com.syg.crm.model.CallLead;
import com.syg.crm.repository.CallLeadRepository;
import com.syg.crm.util.PageRes;

@Service
public class CallService {

	@Autowired
	private CallLeadRepository callRepository;
	
	public CallLead create(AppDTO appDTO, CallLead call) {
		CallLead c = new CallLead();
		if (call.getId() != null) {

			c = callRepository.findById(call.getId()).get();

			c.setSubject(call.getSubject());
			c.setPurposeOfCall(call.getPurposeOfCall());
			c.setCallTo(call.getCallTo());
			c.setCallType(call.getCallType());
			c.setStartTime(call.getStartTime());
			c.setEndTime(call.getEndTime());
			c.setDuration(call.getDuration());
			c.setResult(call.getResult());

			// c.setUserDetail(appDTO.getUserDetail());

			call = callRepository.saveAndFlush(c);
		} else {
			call.setUserDetail(appDTO.getUserDetail());
			call = callRepository.saveAndFlush(call);
		}

		return call;
	}

	public PageRes findAll(AppDTO appDto, String searchTxt, String type, Date startDate, Date endDate, Integer perPage, Integer pageNo) {
		PageRes res = new PageRes();

		Pageable pagable = PageRequest.of(pageNo, perPage);
		Page<CallLead> page = null;

		// Find all calls
		if (UserType.SA.equals(appDto.getUserType())) {
			page = callRepository.search(searchTxt, type, startDate, endDate, pagable);
		}

		// Find all calls belongs to the branch
		else if (UserType.A.equals(appDto.getUserType())) {
			page = callRepository.findAllByUserDetailBranchId(appDto.getBranchId(), searchTxt, type, startDate, endDate, pagable);
		}

		// Find all calls belongs to the particular loggedin user
		else {
			page = callRepository.findAllByUserDetailId(appDto.getUserDetailId(), searchTxt, type, startDate, endDate, pagable);
		}

		res = new PageRes(page);
		return res;
	}

	public void deleteInBatch(List<Long> ids) {
		callRepository.deleteAllByIdInBatch(ids);
	}

}
