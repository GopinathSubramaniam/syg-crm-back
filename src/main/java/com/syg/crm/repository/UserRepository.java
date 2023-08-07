package com.syg.crm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.syg.crm.enums.UserType;
import com.syg.crm.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserNameOrEmail(String userName, String email);

	User findByTokenAndUserDetailId(String token, Long userDetailId);

	List<User> findAllByUserDetailBranchId(Long branchId, Pageable pageable);

	User findByUserDetailId(Long userDetailId);

	@Query(value = "SELECT u FROM User u"
			+ " WHERE LOWER(u.userName) LIKE LOWER(CONCAT('%',:searchKey, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%',:searchKey, '%')) "
			+ " OR LOWER(u.userDetail.userCode) LIKE LOWER(CONCAT('%',:searchKey, '%')) OR LOWER(u.userDetail.firstName) LIKE LOWER(CONCAT('%',:searchKey, '%'))"
			+ " OR LOWER(u.userDetail.lastName) LIKE LOWER(CONCAT('%',:searchKey, '%')) OR LOWER(u.userDetail.mobile) LIKE LOWER(CONCAT('%',:searchKey, '%')) "
			+ " OR LOWER(u.userDetail.workPosition) LIKE LOWER(CONCAT('%',:searchKey, '%')) OR LOWER(u.userDetail.address) LIKE LOWER(CONCAT('%',:searchKey, '%')) "
			+ " OR LOWER(u.userDetail.city) LIKE LOWER(CONCAT('%',:searchKey, '%')) OR LOWER(u.userDetail.state) LIKE LOWER(CONCAT('%',:searchKey, '%')) "
			+ " OR LOWER(u.userDetail.country) LIKE LOWER(CONCAT('%',:searchKey, '%')) OR LOWER(u.userDetail.branch.name) LIKE LOWER(CONCAT('%',:searchKey, '%'))")
	Page<User> searchUser(String searchKey, Pageable p);

	Long countByUserType(UserType type);
	
}
