package com.syg.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.syg.crm.dto.AppDTO;
import com.syg.crm.enums.SeqType;
import com.syg.crm.model.Branch;
import com.syg.crm.model.Company;
import com.syg.crm.repository.BranchRepository;
import com.syg.crm.repository.CompanyRepository;
import com.syg.crm.util.PageRes;

@Service
public class BranchService {

	@Autowired
	private AppService appService;

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private CompanyRepository companyRepository;

	public Branch create(AppDTO appDTO, Branch branch) {
		Branch b = new Branch();
		if (branch.getId() != null) {
			b = branchRepository.findById(branch.getId()).get();

			b.setName(branch.getName());
			b.setEmail(branch.getEmail());
			b.setMobile(branch.getMobile());
			b.setCity(branch.getCity());
			b.setState(branch.getState());
			b.setCountry(branch.getCountry());
			b.setContactPerson(branch.getContactPerson());

			b.setUpdatedBy(appDTO.getLoggedInUser());

			Company company = companyRepository.findById(appDTO.getCompanyId()).get();
			b.setCompany(company);

			b = branchRepository.saveAndFlush(b);
		} else {
			b.setCode(appService.getSeqNum(SeqType.BR));
			branch.setCreatedBy(appDTO.getLoggedInUser());
			branch.setUpdatedBy(appDTO.getLoggedInUser());

			Company company = companyRepository.findById(appDTO.getCompanyId()).get();
			branch.setCompany(company);

			branch = branchRepository.saveAndFlush(branch);
		}

		return branch;
	}

	public PageRes findAll(AppDTO appDTO, Integer perPage, Integer pageNo) {
		PageRes res = new PageRes();
		Page<Branch> page = branchRepository.findAllByCompanyId(appDTO.getCompanyId(), PageRequest.of(pageNo, perPage));
		res = new PageRes(page);

		return res;
	}

	public void deleteInBatch(List<Long> ids) {
		branchRepository.deleteAllByIdInBatch(ids);
	}

}
