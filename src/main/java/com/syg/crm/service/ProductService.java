package com.syg.crm.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syg.crm.dto.AppDTO;
import com.syg.crm.dto.ProductDTO;
import com.syg.crm.model.Media;
import com.syg.crm.model.Product;
import com.syg.crm.repository.MediaRepository;
import com.syg.crm.repository.ProductRepository;
import com.syg.crm.util.PageRes;
import com.syg.crm.util.Util;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private MediaRepository mediaRepository;

	@Value("${media.upload.product.img}")
	private String mediaUploadProdImgPath;

	@Value("${server.base.path}")
	private String serverBasePath;

	public Product create(AppDTO appDTO, MultipartFile[] files, String model) throws Exception {
		Product product = new Product();
		ObjectMapper mapper = new ObjectMapper();
		ProductDTO prodDTO = mapper.readValue(model, ProductDTO.class);
		Product reqBody = prodDTO.getProduct();

		// START - Deleting media
		if (prodDTO.getDeletedMediaIds().size() > 0) {
			List<Media> medias = mediaRepository.findAllById(prodDTO.getDeletedMediaIds());
			for (Media media : medias) {
				if (media.getMediaPath() != null) {
					Path path = Paths.get(media.getMediaPath());
					Files.delete(path); // Deleting stored files
				}
			}

			mediaRepository.deleteAllById(prodDTO.getDeletedMediaIds());
		}
		// END - Deleting media

		if (reqBody.getId() != null) {
			product = productRepository.findById(reqBody.getId()).get();
		}

		product.setName(reqBody.getName());
		product.setDescription(reqBody.getDescription());
		product.setExpiryDate(reqBody.getExpiryDate());
		product.setPrice(reqBody.getPrice());
		product.setActive(reqBody.getActive());
		product.setBranch(reqBody.getBranch());

		product = productRepository.saveAndFlush(product);

		System.out.println("Media Upload Prod Img Path = " + mediaUploadProdImgPath);
		// <> Saving media files
		if (files != null && files.length > 0) {
			List<Media> medias = new ArrayList<Media>();
			for (MultipartFile file : files) {
				byte[] bytes = file.getBytes();

				String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
				String fileName = UUID.randomUUID().toString() + "-" + product.getId() + ext;
				String filePath = Util.UPLOAD_PROD_IMG_FOLDER + "/" + fileName;
				Path path = Paths.get(mediaUploadProdImgPath + filePath);
				Files.write(path, bytes);

				Media m = new Media();
				m.setName(product.getName());
				m.setMediaPath(filePath);
				m.setMediaUrl(serverBasePath + filePath);
				m.setProduct(product);
				medias.add(m);
			}
			mediaRepository.saveAllAndFlush(medias);
		}
		// </>

		return product;
	}

	public PageRes findAll(AppDTO appDto, Integer perPage, Integer pageNo) {
		PageRes res = new PageRes();

		Pageable page = PageRequest.of(pageNo, perPage);

		List<Product> products = new ArrayList<>();
		Long totalRecords = 0L;

		products = productRepository.findAll(page).toList();
		totalRecords = productRepository.count();

		res.setData(products);
		res.setTotalRecords(totalRecords);
		return res;
	}

	@Transactional
	public void deleteInBatch(List<Long> ids) throws Exception {

		// START - Deleting media
		List<Media> medias = mediaRepository.findAllByProductIdIn(ids);
		for (Media media : medias) {
			if (media.getMediaPath() != null) {
				Path path = Paths.get(mediaUploadProdImgPath + media.getMediaPath());
				Files.delete(path); // Deleting stored files
			}
		}
		mediaRepository.deleteAll(medias);
		productRepository.deleteAllById(ids);
		// END - Deleting media
	}

}
