package com.sfdc.demo;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Handles requests for the application home page.
 */
@Controller
public class FileUploadController {
	
	@Autowired
	private CalcsDAO tmc;
	
	@Autowired
	private BillingDAO billing;
	
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		/* Map<String,String> vals = tmc.getCalcs("Alabama","1","Air","2007");
		for (Map.Entry<String,String> entry : vals.entrySet()) {
			  String key = entry.getKey();
			  String value = entry.getValue();
			  System.out.println(key + ":" + value);
			  
			} */
		return "home";
	}
	
	 @RequestMapping(value = "/rest/calcs", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> getCalcs(@RequestParam("year") String year
	                            , @RequestParam("state") String state
	                            , @RequestParam("transport_type") String transportType
	                            , @RequestParam("transport_units") String transUnits)
	{
		Map<String,String> vals = tmc.getCalcs(state,transUnits,transportType,year);
		for (Map.Entry<String,String> entry : vals.entrySet()) {
			  String key = entry.getKey();
			  String value = entry.getValue();
			  System.out.println(key + ":" + value);
			  
			}
		return vals;
	} 
	 
	 @RequestMapping(value = "/upload", method = RequestMethod.POST)
	    public String save(
	            @ModelAttribute("uploadForm") FileUploadForm uploadForm,
	                    Model modelMap) {
	         
	        MultipartFile uploadedFile = uploadForm.getFile();
	 
	        
	         
	        if(null != uploadedFile) {
	           
	 
	                String fileName = uploadedFile.getOriginalFilename();
	           
	                //Handle file content - multipartFile.getInputStream()
	                modelMap.addAttribute("file", fileName);
	            }
	        
	        
	        
	        return "success";
	    }
}
