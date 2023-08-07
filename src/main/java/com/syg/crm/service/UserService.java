package com.syg.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syg.crm.dto.AppDTO;
import com.syg.crm.dto.RegisterDTO;
import com.syg.crm.enums.SeqType;
import com.syg.crm.enums.UserType;
import com.syg.crm.model.Branch;
import com.syg.crm.model.Company;
import com.syg.crm.model.User;
import com.syg.crm.model.UserDetail;
import com.syg.crm.repository.BranchRepository;
import com.syg.crm.repository.CompanyRepository;
import com.syg.crm.repository.UserDetailRepository;
import com.syg.crm.repository.UserRepository;
import com.syg.crm.util.PageRes;

@Service
public class UserService {

	@Autowired
	private AppService appService;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserDetailRepository userDetailRepository;

	@Autowired
	private BranchRepository branchRepository;

	@Transactional
	public User registerCompany(RegisterDTO registerDTO) {

		Company c = companyRepository.saveAndFlush(registerDTO.getCompany());

		// <> Save branch
		Branch b = new Branch();
		b.setName(c.getCity());
		b.setCity(c.getCity());
		b.setState(c.getState());
		b.setCountry(c.getCountry());
		b.setCompany(c);
		b = branchRepository.saveAndFlush(b);
		// </>

		// <> Save user detail
		UserDetail ud = new UserDetail();
		ud.setUserCode(appService.getSeqNum(SeqType.SA));
		ud.setEmail(registerDTO.getUser().getEmail());
		ud.setCity(b.getCity());
		ud.setState(b.getState());
		ud.setCountry(b.getCountry());
		ud.setBranch(b);
		ud = userDetailRepository.saveAndFlush(ud);
		// </>

		// <> Save user
		User u = registerDTO.getUser();
		u.setUserDetail(ud);
		u.setUserType(UserType.SA);
		u = userRepository.saveAndFlush(u);

		return u;
	}

	public User login(User u) {
		u = userRepository.findByUserNameOrEmail(u.getEmail(), u.getEmail());
		if (u != null) {
			u.setLoggedIn(1);
			userRepository.saveAndFlush(u);
		}
		return u;
	}

	public User logout(String token, Long userDetailId) {
		User u = userRepository.findByTokenAndUserDetailId(token, userDetailId);
		if (u != null) {
			u.setLoggedIn(0);
			u = userRepository.saveAndFlush(u);
		}
		return u;
	}

	public PageRes findAll(String auth, String searchKey, Integer perPage, Integer pageNo) {
		PageRes res = new PageRes();

		Pageable pageable = PageRequest.of(pageNo, perPage);

		Page<User> page = null;
		if (searchKey.isBlank()) {
			page = userRepository.findAll(pageable);
		} else {
			page = userRepository.searchUser(searchKey, pageable);
		}

		if (!page.isEmpty()) {
			res.setData(page.toList());
			res.setTotalRecords(page.getTotalElements());
			res.setTotalPages(page.getTotalPages());
		}

		return res;
	}

	@Transactional
	public User create(String auth, User user) {
		AppDTO appDto = appService.getLoggedInUser(auth);

		// <> Save user detail
		UserDetail ud = user.getUserDetail();

		if (ud.getId() != null) {
			UserDetail userDetail = userDetailRepository.findById(ud.getId()).get();
			userDetail.setAddress(ud.getAddress());
			userDetail.setBranch(ud.getBranch());
			userDetail.setCity(ud.getCity());
			userDetail.setCountry(ud.getCountry());
			userDetail.setFirstName(ud.getFirstName());
			userDetail.setLastName(ud.getLastName());
			userDetail.setMobile(ud.getMobile());
			userDetail.setState(ud.getState());
			userDetail.setWorkPosition(ud.getWorkPosition());

			ud = userDetailRepository.saveAndFlush(userDetail);
		} else {
			SeqType type = SeqType.SA;
			if (user.getUserType().toString().equals("A"))
				type = SeqType.A;
			else if (user.getUserType().toString().equals("E"))
				type = SeqType.E;

			ud.setUserCode(appService.getSeqNum(type));
			ud = userDetailRepository.saveAndFlush(ud);
		}
		// </>

		// <> Save user
		if (user.getId() != null) {
			User u = userRepository.findById(user.getId()).get();
			u.setUserName(user.getUserName());
			u.setEmail(user.getEmail());
			u.setPassword(user.getPassword());
			u.setUserDetail(ud);

			user = userRepository.saveAndFlush(u);
		} else {
			user.setCreatedUserDetail(appDto.getUserDetail());
			user.setUserDetail(ud);
			user = userRepository.saveAndFlush(user);
		}

		return user;
	}

	public void delete(List<Long> ids) {
		userRepository.deleteAllById(ids);
	}
}
