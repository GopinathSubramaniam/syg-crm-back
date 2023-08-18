package com.syg.crm.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.syg.crm.dto.AppDTO;
import com.syg.crm.dto.TaskDTO;
import com.syg.crm.model.Task;
import com.syg.crm.model.TaskComment;
import com.syg.crm.service.AppService;
import com.syg.crm.service.TaskCommentService;
import com.syg.crm.service.TaskService;
import com.syg.crm.util.PageRes;
import com.syg.crm.util.Res;

@RestController
@RequestMapping("/task")
public class TaskAdapter {

	@Autowired
	private AppService appService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private TaskCommentService taskCommentService;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Res create(@RequestHeader(value = "auth") String auth, @RequestBody Task task) {
		AppDTO appDTO = appService.getLoggedInUser(auth);
		task = taskService.create(appDTO, task);
		Res res = new Res(true, 200);
		res.setData(task);
		return res;
	}

	@PostMapping(value = "/comment", produces = MediaType.APPLICATION_JSON_VALUE)
	public Res createComment(@RequestBody TaskComment comment) {
		comment = taskCommentService.create(comment);
		Res res = new Res(true, 200);
		res.setData(comment);
		return res;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public PageRes findAll(@RequestHeader(value = "auth") String auth, @RequestParam Integer perPage,
			@RequestParam Integer pageNo, @RequestParam String searchTxt, @RequestParam String status,
			@RequestParam String priority, @RequestParam(name = "startDate", required = false) String startDate,
			@RequestParam(name = "endDate", required = false) String endDate) {

		AppDTO appDTO = appService.getLoggedInUser(auth);
		PageRes res = taskService.findAll(appDTO, perPage, pageNo, searchTxt, status, priority, startDate, endDate);
		return res;
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Res findOne(@RequestHeader(value = "auth") String auth, @PathVariable Long id) {
		AppDTO appDTO = appService.getLoggedInUser(auth);
		TaskDTO t = taskService.findOne(appDTO, id);
		Res res = new Res();
		res.setData(t);
		return res;
	}

	@DeleteMapping
	public Res deleteInBatch(@RequestParam List<Long> ids) {
		System.out.println("Ids = " + ids);
		taskService.deleteInBatch(ids);
		return new Res();
	}

}
