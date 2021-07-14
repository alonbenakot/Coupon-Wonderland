package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import app.core.services.ImageStorageService;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {
	
	@Autowired
	private ImageStorageService storageService;

	@PostMapping("upload")
	public String uploadFFileile(@RequestParam MultipartFile file) {
		return this.storageService.storeFile(file);
	}
	
}
