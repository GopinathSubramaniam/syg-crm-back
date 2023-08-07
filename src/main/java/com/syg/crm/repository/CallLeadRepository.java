package com.syg.crm.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.syg.crm.model.CallLead;

public interface CallLeadRepository extends JpaRepository<CallLead, Long> {

	@Query("SELECT cl FROM CallLead cl WHERE CASE WHEN (:type <> '' AND :type IS NOT NULL) THEN cl.callType = :type ELSE (cl.callType <> '') END "
			+ " AND CASE WHEN (:startDate IS NOT NULL) THEN (cl.startTime BETWEEN :startDate AND :endDate) ELSE (cl.startTime IS NOT NULL) END"
			+ " AND (LOWER(cl.subject) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(cl.purposeOfCall) LIKE LOWER(CONCAT('%',:searchTxt, '%')) "
			+ " OR LOWER(cl.callTo) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(cl.userDetail.firstName) LIKE LOWER(CONCAT('%',:searchTxt, '%')) "
			+ " OR LOWER(cl.userDetail.lastName) LIKE LOWER(CONCAT('%',:searchTxt, '%')))")
	Page<CallLead> search(String searchTxt, String type, Date startDate, Date endDate, Pageable page);

	@Query("SELECT cl FROM CallLead cl WHERE cl.userDetail.id = :userDetailId AND CASE WHEN (:type <> '' AND :type IS NOT NULL) THEN cl.callType = :type ELSE (cl.callType <> '') END "
			+ " AND CASE WHEN (:startDate IS NOT NULL) THEN (cl.startTime BETWEEN :startDate AND :endDate) ELSE (cl.startTime IS NOT NULL) END"
			+ " AND (LOWER(cl.subject) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(cl.purposeOfCall) LIKE LOWER(CONCAT('%',:searchTxt, '%')) "
			+ " OR LOWER(cl.callTo) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(cl.userDetail.firstName) LIKE LOWER(CONCAT('%',:searchTxt, '%')) "
			+ " OR LOWER(cl.userDetail.lastName) LIKE LOWER(CONCAT('%',:searchTxt, '%')))")
	Page<CallLead> findAllByUserDetailId(Long userDetailId, String searchTxt, String type, Date startDate, Date endDate,
			Pageable pageable);

	@Query("SELECT cl FROM CallLead cl WHERE cl.userDetail.branch.id = :branchId AND CASE WHEN (:type <> '' AND :type IS NOT NULL) THEN cl.callType = :type ELSE (cl.callType <> '') END "
			+ " AND CASE WHEN (:startDate IS NOT NULL) THEN (cl.startTime BETWEEN :startDate AND :endDate) ELSE (cl.startTime IS NOT NULL) END"
			+ " AND (LOWER(cl.subject) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(cl.purposeOfCall) LIKE LOWER(CONCAT('%',:searchTxt, '%')) "
			+ " OR LOWER(cl.callTo) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(cl.userDetail.firstName) LIKE LOWER(CONCAT('%',:searchTxt, '%')) "
			+ " OR LOWER(cl.userDetail.lastName) LIKE LOWER(CONCAT('%',:searchTxt, '%')))")
	Page<CallLead> findAllByUserDetailBranchId(Long branchId, String searchTxt, String type, Date startDate,
			Date endDate, Pageable pageable);

	Long countByStartTimeGreaterThan(Date date);

}
