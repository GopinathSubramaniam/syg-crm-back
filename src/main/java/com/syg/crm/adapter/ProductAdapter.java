package com.syg.crm.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.syg.crm.dto.AppDTO;
import com.syg.crm.service.AppService;
import com.syg.crm.service.MediaService;
import com.syg.crm.service.ProductService;
import com.syg.crm.util.PageRes;
import com.syg.crm.util.Res;

@RestController
@RequestMapping("/product")
public class ProductAdapter {

	@Autowired
	private AppService appService;

	@Autowired
	private ProductService productService;

	@Autowired
	private MediaService mediaService;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Res create(@RequestHeader(value = "auth") String auth, @RequestParam("model") String model,
			@RequestParam(value = "files", required = false) MultipartFile[] files) {
		Res res = new Res(true, 200);
		try {
			AppDTO appDTO = appService.getLoggedInUser(auth);
			productService.create(appDTO, files, model);
		} catch (Exception e) {
			e.printStackTrace();
			res = new Res(false, 500);
		}

		return res;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public PageRes findAll(@RequestHeader(value = "auth") String auth, @RequestParam Integer perPage,
			@RequestParam Integer pageNo) {
		AppDTO appDTO = appService.getLoggedInUser(auth);
		PageRes res = mediaService.findAll(appDTO, perPage, pageNo);
		return res;
	}

	@DeleteMapping
	public Res deleteInBatch(@RequestParam List<Long> ids) {
		Res res = new Res();
		try {
			productService.deleteInBatch(ids);
		} catch (Exception e) {
			res = new Res(false, 500, e.getMessage());
		}
		return res;
	}

}
