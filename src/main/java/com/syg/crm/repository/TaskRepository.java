package com.syg.crm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.syg.crm.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	@Query("SELECT t FROM Task t WHERE CASE WHEN :status <> '' THEN t.status=:status ELSE t.status<>'' END "
			+ "	AND CASE WHEN :priority <> '' THEN t.priority=:priority ELSE t.priority IS NOT NULL END "
			+ " AND CASE WHEN :sDate IS NOT NULL THEN (t.startDate BETWEEN :sDate AND :eDate) ELSE t.startDate IS NOT NULL END "
			+ " AND (LOWER(t.taskNum) LIKE LOWER(CONCAT('%',:searchTxt, '%')) "
			+ " OR LOWER(t.subject) LIKE LOWER(CONCAT('%',:searchTxt, '%')) "
			+ " OR LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTxt, '%')) "
			+ " OR LOWER(t.priority) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(t.status) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(t.userDetail.userCode) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(t.userDetail.firstName) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(t.userDetail.lastName) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(t.userDetail.mobile) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(t.userDetail.branch.name) LIKE LOWER(CONCAT('%',:searchTxt, '%')))")
	Page<Task> search(String searchTxt, String status, String priority, Date sDate, Date eDate, Pageable pageable);

	@Query("SELECT t FROM Task t WHERE t.userDetail.branch.id=:branchId AND CASE WHEN :status <> '' THEN t.status=:status ELSE t.status<>'' END "
			+ "	AND CASE WHEN :priority <> '' THEN t.priority=:priority ELSE t.priority IS NOT NULL END "
			+ " AND CASE WHEN :sDate IS NOT NULL THEN (t.startDate BETWEEN :sDate AND :eDate) ELSE t.startDate IS NOT NULL END "
			+ " AND (LOWER(t.taskNum) LIKE LOWER(CONCAT('%',:searchTxt, '%')) "
			+ " OR LOWER(t.subject) LIKE LOWER(CONCAT('%',:searchTxt, '%')) "
			+ " OR LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTxt, '%')) "
			+ " OR LOWER(t.priority) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(t.status) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(t.userDetail.userCode) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(t.userDetail.firstName) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(t.userDetail.lastName) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(t.userDetail.mobile) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(t.userDetail.branch.name) LIKE LOWER(CONCAT('%',:searchTxt, '%')))")
	Page<Task> searchByBranch(Long branchId, String searchTxt, String status, String priority, Date sDate, Date eDate,
			Pageable pageable);

	@Query("SELECT t FROM Task t WHERE t.userDetail.id=:userDetailId AND CASE WHEN :status <> '' THEN t.status=:status ELSE t.status<>'' END "
			+ "	AND CASE WHEN :priority <> '' THEN t.priority=:priority ELSE t.priority IS NOT NULL END "
			+ " AND CASE WHEN :sDate IS NOT NULL THEN (t.startDate BETWEEN :sDate AND :eDate) ELSE t.startDate IS NOT NULL END "
			+ " AND (LOWER(t.taskNum) LIKE LOWER(CONCAT('%',:searchTxt, '%')) "
			+ " OR LOWER(t.subject) LIKE LOWER(CONCAT('%',:searchTxt, '%')) "
			+ " OR LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTxt, '%')) "
			+ " OR LOWER(t.priority) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(t.status) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(t.userDetail.userCode) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(t.userDetail.firstName) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(t.userDetail.lastName) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(t.userDetail.mobile) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(t.userDetail.branch.name) LIKE LOWER(CONCAT('%',:searchTxt, '%')))")
	Page<Task> searchByUserDetail(Long userDetailId, String searchTxt, String status, String priority, Date sDate,
			Date eDate, Pageable pageable);

	List<Task> findAllByStartDateBetween(Date start, Date end);

	@Query("SELECT t FROM Task t WHERE FIND_IN_SET(:leadIds, t.leadIds) > 0")
	List<Task> findByLeadIds(String leadIds);

}
