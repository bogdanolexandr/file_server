package com.fs.controller;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fs.utils.FileManager;

@Controller
public class FileController {

	@Autowired
	private FileManager fileManager;
	
	@Autowired
	private ServletContext context;
	
	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
		if(file.isEmpty()) {
			return "redirect:/";
		}
		
		fileManager.save(file);
		return "redirect:/ ";
	}
	
	@GetMapping("/get")
	public ResponseEntity<InputStreamResource> getFile(@RequestParam String fileName){
		InputStreamResource resource = fileManager.getFile(fileName);
		System.out.println(fileName);
		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=" + fileName)
				.contentType(fileManager.getMediaTypeForFileName(context, new File(fileName).getName()))
				.contentLength(fileManager.getFileLength(fileName))
				.body(resource);
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam String fileName) {
		fileManager.delete(fileName);
		return "redirect:/";
	}
	
	@GetMapping("/")
	public String getResources(Model model) {
		model.addAttribute("files",fileManager.getAllNames());
		model.addAttribute("text","this is my text");
		model.addAttribute("obj", new dto(1,"Ivan"));
		Map<String,dto> map = new HashMap<>();
		map.put("first", new dto(2,"Petro"));
		model.addAttribute("map",map);
		
		List<String> list = Stream.of("first","second","third").collect(Collectors.toList());
	
		model.addAttribute("list",list);
		
		return "index";
	}
	
	@GetMapping("/here")
	public String getStatus(@RequestParam String status) {
		
		System.out.println(status);
		
		return "index";
	}
	
	
}
