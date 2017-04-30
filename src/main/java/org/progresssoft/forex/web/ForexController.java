package org.progresssoft.forex.web;

import java.io.IOException;

import org.progresssoft.forex.exception.ForexException;
import org.progresssoft.forex.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for Forex web application.
 * 
 * @author hafeeztsd
 *
 */
@Controller
public class ForexController {

	@Autowired
	private DealService dealService;

	@PostMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:uploadStatus";
		}

		try {
			dealService.loadDeals(file.getInputStream(), file.getOriginalFilename());
			redirectAttributes.addFlashAttribute("message",
					"Successfully uploaded  file '" + file.getOriginalFilename() + "'");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ForexException e) {
			e.printStackTrace();
		}

		return "redirect:/uploadStatus";
	}
}
