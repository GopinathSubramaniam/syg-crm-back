package com.syg.crm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.syg.crm.dto.AppDTO;
import com.syg.crm.model.Media;
import com.syg.crm.repository.MediaRepository;
import com.syg.crm.util.PageRes;

@Service
public class MediaService {

	@Autowired
	private MediaRepository mediaRepository;

	public PageRes findAll(AppDTO appDto, Integer perPage, Integer pageNo) {
		PageRes res = new PageRes();

		Pageable page = PageRequest.of(pageNo, perPage);

		List<Media> products = new ArrayList<>();
		Long totalRecords = 0L;

		products = mediaRepository.getGroupByProductId(page);
		totalRecords = mediaRepository.count();

		res.setData(products);
		res.setTotalRecords(totalRecords);
		return res;
	}

	public void deleteInBatch(List<Long> ids) {
		mediaRepository.deleteAllByIdInBatch(ids);
	}

}
