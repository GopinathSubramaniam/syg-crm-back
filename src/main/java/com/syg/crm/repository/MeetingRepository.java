package com.syg.crm.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syg.crm.model.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

	Long countByStartTimeBetween(Date start, Date end);

}
