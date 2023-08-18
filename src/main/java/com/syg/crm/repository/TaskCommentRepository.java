package com.syg.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syg.crm.model.TaskComment;

public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {

	List<TaskComment> findAllByTaskId(Long id);

}
