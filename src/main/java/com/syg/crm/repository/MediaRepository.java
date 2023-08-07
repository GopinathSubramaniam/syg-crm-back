package com.syg.crm.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.syg.crm.model.Media;

@Transactional
public interface MediaRepository extends JpaRepository<Media, Long> {

	@Query("SELECT m, GROUP_CONCAT(m.mediaUrl) mediaUrl FROM Media m GROUP BY m.product.id")
	List<Media> getGroupByProductId(Pageable page);

	void deleteAllByProductIdIn(List<Long> ids);

	List<Media> findAllByProductIdIn(List<Long> description);

}
