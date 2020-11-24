package com.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	boolean save(MultipartFile file);
	
	List<String> getAllFilesNames();
	
	void delete(String file);
	
}
