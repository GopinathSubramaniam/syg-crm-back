package com.syg.crm.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.syg.crm.dto.AppDTO;
import com.syg.crm.enums.LeadStatus;
import com.syg.crm.model.Lead;
import com.syg.crm.service.AppService;
import com.syg.crm.service.LeadService;
import com.syg.crm.util.PageRes;
import com.syg.crm.util.Res;

@RestController
@RequestMapping("/lead")
public class LeadAdapter {

	@Autowired
	private AppService appService;

	@Autowired
	private LeadService leadService;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Res create(@RequestHeader(value = "auth") String auth, @RequestBody Lead lead) {
		AppDTO appDTO = appService.getLoggedInUser(auth);
		lead = leadService.create(appDTO, lead);
		Res res = new Res(true, 200);
		res.setData(lead);
		return res;
	}

	@GetMapping(value = "/updateTags", produces = MediaType.APPLICATION_JSON_VALUE)
	public Res updateTags(@RequestHeader(value = "auth") String auth, @RequestParam List<Long> leadIds,
			@RequestParam String tags) {
		leadService.updateTags(leadIds, tags);
		return new Res();
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public PageRes findAll(@RequestHeader(value = "auth") String auth, @RequestParam Integer perPage,
			@RequestParam Integer pageNo, @RequestParam(defaultValue = "", required = false) String searchTxt,
			@RequestParam(required = false) List<LeadStatus> statusList) {

		AppDTO appDTO = appService.getLoggedInUser(auth);
		PageRes res = leadService.findAll(appDTO, perPage, pageNo, statusList, searchTxt);
		return res;
	}

	@DeleteMapping
	public Res deleteInBatch(@RequestParam List<Long> ids) {
		System.out.println("Ids = " + ids);
		leadService.deleteInBatch(ids);
		return new Res();
	}

	@GetMapping("/updateStatus")
	public Res updateStatusInBulk(@RequestParam List<Long> ids, @RequestParam LeadStatus status) {
		System.out.println("Ids = " + ids);
		leadService.updateStatusInBulk(ids, status);
		return new Res();
	}

	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public PageRes search(@RequestHeader(value = "auth") String auth,
			@RequestParam(defaultValue = "", required = false) String searchTxt) {
		PageRes res = new PageRes();
		res.setData(leadService.search(searchTxt));
		return res;
	}

	@GetMapping(value = "/detail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PageRes detail(@RequestHeader(value = "auth") String auth, @PathVariable Long id) {
		PageRes res = new PageRes();
		res.setData(leadService.detail(id));
		return res;
	}

}
