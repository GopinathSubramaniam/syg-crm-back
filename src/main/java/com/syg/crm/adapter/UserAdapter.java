package com.syg.crm.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.syg.crm.dto.RegisterDTO;
import com.syg.crm.model.User;
import com.syg.crm.service.UserService;
import com.syg.crm.util.PageRes;
import com.syg.crm.util.Res;

@RestController
@RequestMapping("/user")
public class UserAdapter {

	@Autowired
	private UserService userService;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Res registerCompany(@RequestBody RegisterDTO registerDTO) {
		Res res = new Res(true, 200);
		try {
			User u = userService.registerCompany(registerDTO);
			res.setData(u);
		} catch (DataIntegrityViolationException e) {
			res = new Res(false, 500, e.getMessage());
		}

		return res;
	}

	@PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public Res login(@RequestBody User u) {
		Res res = new Res();

		try {
			u = userService.login(u);

			if (u != null) {
				res.setData(u);
			} else {
				res = new Res(false, 500);
				res.setStatusMsg("User not exists");
			}
		} catch (Exception e) {
			res = new Res(false, 500, e.getMessage());
		}

		return res;
	}

	@GetMapping(path = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
	public Res login(@RequestParam String token, @RequestParam Long userDetailId) {
		Res res = new Res();
		try {
			User u = userService.logout(token, userDetailId);
			if (u == null) {
				res = new Res(false, 500);
				res.setStatusMsg("User not exists");
			}
		} catch (Exception e) {
			res = new Res(false, 500, e.getMessage());
		}

		return res;
	}

	@GetMapping(path = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public PageRes findAll(@RequestHeader(value = "auth") String auth, @RequestParam Integer perPage,
			@RequestParam Integer pageNo, @RequestParam(defaultValue = "", required = false) String searchKey) {
		PageRes res = userService.findAll(auth, searchKey, perPage, pageNo);
		return res;
	}

	@PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public Res create(@RequestHeader(value = "auth") String auth, @RequestBody User user) {
		Res res = new Res(true, 200);

		try {
			User u = userService.create(auth, user);
			res.setData(u);
		} catch (Exception e) {
			res = new Res(false, 500, e.getMessage());
		}

		return res;
	}

	@DeleteMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public Res create(@RequestHeader(value = "auth") String auth, @RequestParam List<Long> ids) {
		Res res = new Res(true, 200);
		try {
			userService.delete(ids);
		} catch (Exception e) {
			res = new Res(false, 500, e.getMessage());
		}

		return res;
	}

}
