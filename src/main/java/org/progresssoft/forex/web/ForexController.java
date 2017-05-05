package org.progresssoft.forex.web;

import java.io.IOException;

import org.progresssoft.forex.exception.ForexException;
import org.progresssoft.forex.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller for Forex web application.
 * 
 * @author hafeeztsd
 *
 */
@Controller
public class ForexController {

	private static final String MESSAGE_KEY = "message";
	private static final String DEALS = "deals";

	@Autowired
	private DealService dealService;

	@GetMapping("/list")
	public String findDeals(@RequestParam("fileName") String fileName, Model model) {
		model.addAttribute(DEALS, dealService.getDealsByFileName(fileName));
		return "list";
	}

	@PostMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file, Model model) {

		if (file.isEmpty()) {
			model.addAttribute(MESSAGE_KEY, "Please select a file to upload");
			return "uploadStatus";
		}

		try {
			dealService.loadDeals(file.getInputStream(), file.getOriginalFilename());
			model.addAttribute(MESSAGE_KEY, "Successfully uploaded  file '" + file.getOriginalFilename() + "'");
		} catch (IOException e) {
			model.addAttribute(MESSAGE_KEY, e.getMessage());
			return "uploadStatus";

		} catch (ForexException e) {
			model.addAttribute(MESSAGE_KEY, e.getForexErrorCode().getMessage());
			return "uploadStatus";

		}
		return "uploadStatus";
	}

	@GetMapping("/")
	public String index() {
		return "upload";
	}

}
