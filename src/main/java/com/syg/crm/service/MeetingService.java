package com.syg.crm.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.syg.crm.dto.AppDTO;
import com.syg.crm.dto.EmailDTO;
import com.syg.crm.dto.MeetingDTO;
import com.syg.crm.enums.SeqType;
import com.syg.crm.enums.UserType;
import com.syg.crm.model.Meeting;
import com.syg.crm.model.MeetingParticipant;
import com.syg.crm.model.UserDetail;
import com.syg.crm.repository.MeetingParticipantRepository;
import com.syg.crm.repository.MeetingRepository;
import com.syg.crm.repository.UserDetailRepository;
import com.syg.crm.util.PageRes;

@Service
public class MeetingService {

	@Autowired
	private AppService appService;

	@Autowired
	private MeetingRepository meetingRepository;

	@Autowired
	private MeetingParticipantRepository meetingParticipantRepository;

	@Autowired
	private UserDetailRepository userDetailRepository;

	@Autowired
	private EmailService emailService;

	public Meeting createOrUpdate(AppDTO appDTO, MeetingDTO meetingDto) {

		Meeting meet = new Meeting();

		if (meetingDto.getId() != null) {
			meet = meetingRepository.findById(meetingDto.getId()).get();
			if (meetingDto.getRemovedParticipants().size() > 0) {
				meetingParticipantRepository.deleteByMeetingIdAndParticipantIdIn(meet.getId(),
						meetingDto.getRemovedParticipants());
			}
		} else {
			String meetingId = appService.getSeqNum(SeqType.MT);
			meet.setMeetingId(meetingId);
		}

		meet.setSubject(meetingDto.getSubject());
		meet.setDescription(meetingDto.getDescription());
		meet.setStartTime(meetingDto.getStartTime());
		meet.setEndTime(meetingDto.getEndTime());
		meet.setPlace(meetingDto.getPlace());
		meet.setReminderMins(meetingDto.getReminderMins());

		UserDetail host = userDetailRepository.findById(meetingDto.getHost()).get();
		meet.setHost(host);

		meet = meetingRepository.saveAndFlush(meet);

		final List<MeetingParticipant> participants = new ArrayList<>();
		for (Long participantId : meetingDto.getParticipants()) {
			MeetingParticipant participant = new MeetingParticipant();

			if (meet.getId() != null) {
				participant = meetingParticipantRepository.findByMeetingIdAndParticipantId(meet.getId(), participantId);
			}

			if (participant == null) {
				participant = new MeetingParticipant();
				participant.setMeeting(meet);

				UserDetail ud = userDetailRepository.findById(participantId).get();
				participant.setParticipant(ud);

				participants.add(participant);
				meetingParticipantRepository.saveAllAndFlush(participants);
			}
		}

		return meet;
	}

	public PageRes findAll(AppDTO appDto, Integer perPage, Integer pageNo, String searchTxt, String period) {

		PageRes res = new PageRes();
		Pageable pagable = PageRequest.of(pageNo, perPage);

		Page<MeetingParticipant> page = null;

		// Find all meetings
		if (UserType.SA.equals(appDto.getUserType())) {
			if (period.isBlank() && searchTxt.isBlank()) {
				page = meetingParticipantRepository.searchAll(pagable);
			} else {
				page = meetingParticipantRepository.search(searchTxt, period, pagable);
			}
		}
		// Find all meetings belongs to the branch
		else if (UserType.A.equals(appDto.getUserType())) {
			page = meetingParticipantRepository.findDistinctBranchMeeting(appDto.getBranchId(), searchTxt, period,
					pagable);
		}

		// Find all meetings belongs to the particular loggedin user
		else {
			page = meetingParticipantRepository.findByParticipantIdOrHostId(appDto.getUserDetailId(), searchTxt, period,
					pagable);
		}

		res = new PageRes(page);

		return res;
	}

	public void deleteInBatch(List<Long> ids) {
//		meetingRepository.deleteAllById(ids);
		meetingParticipantRepository.deleteByMeetingIdIn(ids);
	}

	public List<MeetingParticipant> getParticipants(Long meetingId) {
		List<MeetingParticipant> participants = meetingParticipantRepository.findByMeetingId(meetingId);
		return participants;
	}

	public void sendEmailNotification(Long meetingId) {
		List<MeetingParticipant> mparticipants = meetingParticipantRepository.findByMeetingId(meetingId);

		String[] emailIds = new String[mparticipants.size() + 1];
		for (int i = 0; i < mparticipants.size(); i++) {
			System.out.println(i);
			// Adding participants in the list
			emailIds[i] = mparticipants.get(i).getParticipant().getEmail();
		}

		// Adding host in the list
		Meeting m = mparticipants.get(0).getMeeting();
		emailIds[mparticipants.size()] = m.getHost().getEmail();
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm z");
		String startDate = simpleDateFormat.format(m.getStartTime());
		String subject = "Meeting: "+m.getSubject()+" @ "+startDate;
		String content = mparticipants.get(0).getMeeting().getDescription();

		EmailDTO email = new EmailDTO(emailIds, null, subject, content);
		emailService.sendSimpleMail(email);
	}

}
