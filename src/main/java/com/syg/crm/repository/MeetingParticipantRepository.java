package com.syg.crm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.syg.crm.model.MeetingParticipant;

import jakarta.transaction.Transactional;

@Transactional
public interface MeetingParticipantRepository extends JpaRepository<MeetingParticipant, Long> {

	@Query("SELECT mp FROM MeetingParticipant mp WHERE (mp.participant.id=:participantId OR mp.meeting.host.id=:participantId)"
			+ " AND (CASE WHEN :period = 'T' THEN (DATE(mp.meeting.startTime) = CURDATE()) WHEN :period = 'W' THEN YEARWEEK(mp.meeting.startTime) = YEARWEEK(NOW()) "
			+ "	WHEN :period = 'M' THEN MONTH(mp.meeting.startTime) = MONTH(CURRENT_DATE()) ELSE mp.meeting.startTime IS NOT NULL END)"
			+ " AND (LOWER(mp.meeting.meetingId) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(mp.meeting.subject) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(mp.meeting.description) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(mp.meeting.place) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(mp.meeting.host.userCode) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(mp.meeting.host.firstName) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(mp.meeting.host.lastName) LIKE LOWER(CONCAT('%',:searchTxt, '%')))"
			+ " GROUP BY mp.meeting.id")
	Page<MeetingParticipant> findByParticipantIdOrHostId(Long participantId, String searchTxt, String period, Pageable page);

	// START - All meetings
	@Query("SELECT mp FROM MeetingParticipant mp WHERE (CASE WHEN :period = 'T' THEN (DATE(mp.meeting.startTime) = CURDATE()) WHEN :period = 'W' THEN YEARWEEK(mp.meeting.startTime) = YEARWEEK(NOW())"
			+ "	WHEN :period = 'M' THEN MONTH(mp.meeting.startTime) = MONTH(CURRENT_DATE()) ELSE mp.meeting.startTime IS NOT NULL END)"
			+ " AND (LOWER(mp.meeting.meetingId) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(mp.meeting.subject) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(mp.meeting.description) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(mp.meeting.place) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(mp.meeting.host.userCode) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(mp.meeting.host.firstName) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(mp.meeting.host.lastName) LIKE LOWER(CONCAT('%',:searchTxt, '%'))) GROUP BY mp.meeting.id")
	Page<MeetingParticipant> search(String searchTxt, String period, Pageable page);

	@Query("SELECT mp FROM MeetingParticipant mp GROUP BY mp.meeting.id")
	Page<MeetingParticipant> searchAll(Pageable page);

	// END

	// START - All Branch meetings
//	@Query("SELECT mp FROM MeetingParticipant mp WHERE mp.participant.branch.id =?1  GROUP BY mp.meeting.id")
	@Query("SELECT mp FROM MeetingParticipant mp WHERE (mp.participant.branch.id =:branchId OR mp.meeting.host.branch.id =:branchId) AND (CASE WHEN :period = 'T' THEN (DATE(mp.meeting.startTime) = CURDATE()) WHEN :period = 'W' THEN YEARWEEK(mp.meeting.startTime) = YEARWEEK(NOW()) "
			+ "	WHEN :period = 'M' THEN MONTH(mp.meeting.startTime) = MONTH(CURRENT_DATE()) ELSE mp.meeting.startTime IS NOT NULL END) AND (LOWER(mp.meeting.meetingId) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(mp.meeting.subject) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(mp.meeting.description) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(mp.meeting.place) LIKE LOWER(CONCAT('%',:searchTxt, '%')) "
			+ " OR LOWER(mp.meeting.host.userCode) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(mp.meeting.host.firstName) LIKE LOWER(CONCAT('%',:searchTxt, '%')) "
			+ " OR LOWER(mp.meeting.host.lastName) LIKE LOWER(CONCAT('%',:searchTxt, '%'))) GROUP BY mp.meeting.id")
	Page<MeetingParticipant> findDistinctBranchMeeting(Long branchId, String searchTxt, String period, Pageable page);

	List<MeetingParticipant> findByMeetingId(Long id);

	void deleteByMeetingIdAndParticipantIdIn(Long id, List<Long> ids);

	void deleteByMeetingIdIn(List<Long> ids);

	MeetingParticipant findByMeetingIdAndParticipantId(Long meetingId, Long participantId);

}
