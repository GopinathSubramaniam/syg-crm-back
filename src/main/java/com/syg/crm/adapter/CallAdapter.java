package com.syg.crm.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.syg.crm.model.CallLead;
import com.syg.crm.service.AppService;
import com.syg.crm.service.CallService;
import com.syg.crm.util.PageRes;
import com.syg.crm.util.Res;

@RestController
@RequestMapping("/call")
public class CallAdapter {

	@Autowired
	private AppService appService;

	@Autowired
	private CallService callService;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Res create(@RequestHeader(value = "auth") String auth, @RequestBody CallLead call) {
		AppDTO appDTO = appService.getLoggedInUser(auth);
		call = callService.create(appDTO, call);
		Res res = new Res(true, 200);
		res.setData(call);
		return res;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public PageRes findAll(@RequestHeader(value = "auth") String auth, @RequestParam Integer perPage,
			@RequestParam Integer pageNo, @RequestParam(defaultValue = "", required = false) String searchTxt,
			@RequestParam(required = false) String type,
			@RequestParam(name = "startDate", required = false) String startDate,
			@RequestParam(name = "endDate", required = false) String endDate) {

		System.out.println("Start Date = " + startDate);
		PageRes res = new PageRes();
		try {
			Date sDate = startDate != null ? new SimpleDateFormat("yyyy-MM-dd").parse(startDate) : null;
			Date eDate = endDate != null ? new SimpleDateFormat("yyyy-MM-dd").parse(endDate) : null;
			
			AppDTO appDTO = appService.getLoggedInUser(auth);
			res = callService.findAll(appDTO, searchTxt, type, sDate, eDate, perPage, pageNo);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return res;
	}

	@DeleteMapping
	public Res deleteInBatch(@RequestParam List<Long> ids) {
		System.out.println("Ids = " + ids);
		callService.deleteInBatch(ids);
		return new Res();
	}

}
