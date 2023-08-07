package com.syg.crm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.syg.crm.enums.LeadStatus;
import com.syg.crm.model.Lead;

public interface LeadRepository extends JpaRepository<Lead, Long> {

	Page<Lead> findAllByUserDetailId(Long userDetailId, Pageable pageable);

	Page<Lead> findAllByUserDetailBranchId(Long branchId, Pageable pageable);

	@Query("SELECT l FROM Lead l WHERE l.status IN (:statusList) AND (LOWER(l.leadSource) LIKE LOWER(('%:searchTxt%')) OR LOWER(l.firstName) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(l.lastName) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(l.email) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(l.mobile) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ "	OR LOWER(l.address) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(l.city) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(l.state) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ "	OR LOWER(l.country) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(l.zip) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(l.description) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ "	OR LOWER(l.tags) LIKE LOWER(CONCAT('%',:searchTxt, '%')))")
	Page<Lead> searchLead(List<LeadStatus> statusList, String searchTxt, Pageable pageable);

	@Query("SELECT l FROM Lead l WHERE l.status IN (:statusList) AND l.userDetail.id = :userDetailId AND (LOWER(l.leadSource) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(l.firstName) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(l.lastName) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(l.email) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(l.mobile) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(l.address) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(l.city) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(l.state) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(l.country) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(l.zip) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(l.description) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(l.tags) LIKE LOWER(CONCAT('%',:searchTxt, '%')))")
	Page<Lead> searchLeadByUserDetailId(List<LeadStatus> statusList, Long userDetailId, String searchTxt,
			Pageable pageable);

	@Query("SELECT l FROM Lead l WHERE l.status IN (:statusList) AND l.userDetail.branch.id = :branchId AND (LOWER(l.leadSource) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(l.firstName) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(l.lastName) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(l.email) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(l.mobile) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(l.address) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(l.city) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(l.state) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(l.country) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(l.zip) LIKE LOWER(CONCAT('%',:searchTxt, '%')) OR LOWER(l.description) LIKE LOWER(CONCAT('%',:searchTxt, '%'))"
			+ " OR LOWER(l.tags) LIKE LOWER(CONCAT('%',:searchTxt, '%')))")
	Page<Lead> searchLeadByBranchId(List<LeadStatus> statusList, Long branchId, String searchTxt, Pageable pageable);

	Long countByUserDetailBranchId(Long branchId);

	Long countByStatus(LeadStatus status);

	@Query("SELECT COUNT(l) FROM Lead l WHERE l.status = :status GROUP BY YEAR(l.createdDate), MONTH(l.createdDate)")
	List<Long> graphDataByYearAndMonth(LeadStatus status);

	@Query("SELECT COUNT(l) FROM Lead l WHERE MONTH(l.createdDate) = MONTH(NOW()) GROUP BY YEAR(l.createdDate), MONTH(l.createdDate), l.leadSource")
	List<Long> getTrafficSummaryGSeriesData();

}
