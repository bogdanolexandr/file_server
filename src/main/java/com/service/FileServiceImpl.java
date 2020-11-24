package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fs.utils.FileManager;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private FileManager fileManager;
	
	@Override
	public boolean save(MultipartFile file) {
		return fileManager.save(file);
	}

	@Override
	public List<String> getAllFilesNames() {
		return fileManager.getAllNames();
	}

	@Override
	public void delete(String file) {
		fileManager.delete(file);
	}

}
