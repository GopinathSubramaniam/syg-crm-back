package com.syg.crm.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syg.crm.dto.AppDTO;
import com.syg.crm.dto.DashboardDTO;
import com.syg.crm.service.AppService;
import com.syg.crm.service.DashboardService;
import com.syg.crm.util.Res;

@RestController
@RequestMapping("/dashboard")
public class DashboardAdapter {

	@Autowired
	private AppService appService;
	
	@Autowired
	private DashboardService dashboardService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Res overview(@RequestHeader(value = "auth") String auth) {
		Res res = new Res();
		try {
			AppDTO appDTO = appService.getLoggedInUser(auth);
			DashboardDTO d = dashboardService.overview(appDTO);
			res.setData(d);
		} catch (Exception e) {
			res = new Res(false, 500, e.getMessage());
		}
		return res;
	}

}
