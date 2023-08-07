package com.syg.crm.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.syg.crm.dto.AppDTO;
import com.syg.crm.model.Branch;
import com.syg.crm.service.AppService;
import com.syg.crm.service.BranchService;
import com.syg.crm.util.PageRes;
import com.syg.crm.util.Res;

@RestController
@RequestMapping("/branch")
public class BranchAdapter {

	@Autowired
	private AppService appService;

	@Autowired
	private BranchService branchService;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Res create(@RequestHeader(value = "auth") String auth, @RequestBody Branch branch) {
		AppDTO appDTO = appService.getLoggedInUser(auth);
		branch = branchService.create(appDTO, branch);
		Res res = new Res(true, 200);
		res.setData(branch);

		return res;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public PageRes findAll(@RequestHeader(value = "auth") String auth, @RequestParam Integer perPage,
			@RequestParam Integer pageNo) {
		AppDTO appDTO = appService.getLoggedInUser(auth);
		PageRes res = branchService.findAll(appDTO, perPage, pageNo);
		return res;
	}

	@DeleteMapping
	public Res deleteInBatch(@RequestParam List<Long> ids) {
		branchService.deleteInBatch(ids);
		return new Res();
	}

}
