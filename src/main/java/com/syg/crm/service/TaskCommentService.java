package com.syg.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syg.crm.dto.AppDTO;
import com.syg.crm.model.TaskComment;
import com.syg.crm.repository.TaskCommentRepository;

@Service
public class TaskCommentService {

	@Autowired
	private TaskCommentRepository taskCommentRepository;

	public TaskComment create(TaskComment taskComment) {
		TaskComment t = new TaskComment();
		if (taskComment.getId() != null) {
			t = taskCommentRepository.findById(taskComment.getId()).get();
			t.setComment(taskComment.getComment());

			taskComment = taskCommentRepository.saveAndFlush(t);
		} else {
			taskComment = taskCommentRepository.saveAndFlush(taskComment);
		}

		return taskComment;
	}

	public List<TaskComment> findAll(AppDTO appDto, Long taskLongId) {
		List<TaskComment> comments = taskCommentRepository.findAllByTaskId(taskLongId);
		return comments;
	}

	public void deleteInBatch(List<Long> ids) {
		taskCommentRepository.deleteAllByIdInBatch(ids);
	}

}
