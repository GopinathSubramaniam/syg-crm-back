package com.syg.crm.service;

import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syg.crm.dto.AppDTO;
import com.syg.crm.enums.SeqType;
import com.syg.crm.enums.UserType;
import com.syg.crm.model.Seq;
import com.syg.crm.model.User;
import com.syg.crm.model.UserDetail;
import com.syg.crm.repository.SeqRepository;
import com.syg.crm.repository.UserDetailRepository;
import com.syg.crm.repository.UserRepository;

@Service
public class AppService {

	@Autowired
	private SeqRepository seqRepository;

	@Autowired
	private UserDetailRepository userDetailRepository;

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public String getSeqNum(SeqType seqType) {
		Seq s = seqRepository.findByType(seqType);

		Long val = s.getValue() + 1;
		s.setValue(val);
		seqRepository.saveAndFlush(s);

		String id = String.format("%04d", val);
		return s.getPrefix().toString() + id;
	}

	@Transactional
	public String getSeqNum(UserType seqType) {
		Seq s = seqRepository.findByType(seqType.toString());

		Long val = s.getValue() + 1;
		s.setValue(val);
		seqRepository.saveAndFlush(s);

		String id = String.format("%04d", val);
		return s.getPrefix().toString() + id;
	}

	public UserDetail getLoggedInUserDetail(Long userDetailId) {
		return userDetailRepository.findById(userDetailId).get();

	}

	public AppDTO getLoggedInUser(String data) {
		byte[] decodedBytes = Base64.getDecoder().decode(data);
		String decodedVal = new String(decodedBytes);

		String[] vals = decodedVal.split("~");

		Long companyId = Long.parseLong(vals[0]);
		Long branchId = Long.parseLong(vals[1]);
		Long userDetailId = Long.parseLong(vals[2]);
		String token = vals[3];
		Optional<UserDetail> udOptional = userDetailRepository.findById(userDetailId);

		AppDTO appDTO = new AppDTO();
		if (udOptional.isPresent()) {
			UserDetail ud = userDetailRepository.findById(userDetailId).get();
			User u = userRepository.findByUserDetailId(ud.getId());
			appDTO = new AppDTO(companyId, branchId, userDetailId, token, ud.getFirstName());
			appDTO.setUserType(u.getUserType());
			appDTO.setUserDetail(ud);
			appDTO.setUser(u);
		}
		return appDTO;
	}

}
