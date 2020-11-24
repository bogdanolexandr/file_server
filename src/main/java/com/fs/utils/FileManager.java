package com.fs.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletContext;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManager {

	FileOutputStream os;
	String path = "storage/";

	public List<String> getAllNames() {
		File file = new File("storage");
		return Stream.of(file.list()).collect(Collectors.toList());
	}
	
	public void delete(String file) {
		File f = new File(path + file);
		f.delete();
	}

	public boolean save(MultipartFile file) {
		try {
			os = new FileOutputStream(path + file.getOriginalFilename());
			os.write(file.getBytes());
			close();
		} catch (IOException e) {
			close();
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public InputStreamResource getFile(String name) {
		File file = new File(name);
		InputStreamResource resource = null;
		try {
			resource = new InputStreamResource(new FileInputStream(path + file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return resource;
		
	}
	
	public long getFileLength(String name) {
		return new File(path + name).length();
	}

	public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
        // application/pdf
        // application/xml
        // image/gif, ...
        String mineType = servletContext.getMimeType(fileName);
        try {
            MediaType mediaType = MediaType.parseMediaType(mineType);
            return mediaType;
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
	
	
	private void close() {
		try {
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
