package com.syg.crm.adapter;

import java.util.ArrayList;
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
import com.syg.crm.dto.MeetingDTO;
import com.syg.crm.model.MeetingParticipant;
import com.syg.crm.model.UserDetail;
import com.syg.crm.service.AppService;
import com.syg.crm.service.MeetingService;
import com.syg.crm.util.PageRes;
import com.syg.crm.util.Res;

@RestController
@RequestMapping("/meeting")
public class MeetingAdapter {

	@Autowired
	private AppService appService;

	@Autowired
	private MeetingService meetingService;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Res create(@RequestHeader(value = "auth") String auth, @RequestBody MeetingDTO meeting) {
		AppDTO appDTO = appService.getLoggedInUser(auth);
		meetingService.createOrUpdate(appDTO, meeting);
		Res res = new Res(true, 200);
		return res;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public PageRes findAll(@RequestHeader(value = "auth") String auth, @RequestParam Integer perPage,
			@RequestParam Integer pageNo, @RequestParam(defaultValue = "", required = false) String searchTxt,
			@RequestParam(defaultValue = "", required = false) String period) {

		AppDTO appDTO = appService.getLoggedInUser(auth);
		PageRes res = meetingService.findAll(appDTO, perPage, pageNo, searchTxt, period);
		return res;
	}

	@GetMapping(path = "/getParticipants", produces = MediaType.APPLICATION_JSON_VALUE)
	public Res getParticipants(@RequestHeader(value = "auth") String auth, @RequestParam Long meetingId) {
		Res res = new Res();
		List<MeetingParticipant> meetingParticipants = meetingService.getParticipants(meetingId);
		List<UserDetail> participants = new ArrayList<UserDetail>();
		meetingParticipants.forEach(m -> {
			participants.add(m.getParticipant());
		});
		res.setData(participants);
		return res;
	}

	@DeleteMapping
	public Res deleteInBatch(@RequestParam List<Long> ids) {
		meetingService.deleteInBatch(ids);
		return new Res();
	}

	@GetMapping(path = "/sendEmailNotification", produces = MediaType.APPLICATION_JSON_VALUE)
	public Res sendEmailNotification(@RequestHeader(value = "auth") String auth, @RequestParam Long meetingId) {
		Res res = new Res();
		try {
			meetingService.sendEmailNotification(meetingId);
		} catch (Exception e) {
			e.printStackTrace();
			res = new Res(false, 500);
		}
		return res;
	}

}
