package com.sfdc.demo;

import java.io.IOException;

import java.sql.SQLException;
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

import canvas.CanvasRequest;
import canvas.SignedRequest;

/**
 * Handles requests for the application home page.
 */
@Controller
public class FileUploadController {
	
	@Autowired
	private FileUploadDAO fileUploadDAO;
	
	
	
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
		
		
		return "home";
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/uploadPage", method = {RequestMethod.POST,RequestMethod.GET} )
	public String uploadPage(@RequestParam(value="signed_request",defaultValue="") String signedRequestParam,
			                 Locale locale, Model model) {
		
		String sObjectId = "";
		if(signedRequestParam != null && !"".equals(signedRequestParam))
		{
		  CanvasRequest canvasRequest =  SignedRequest.verifyAndDecode(signedRequestParam, "9120955182827941175");
		
		  Map<String, Object> parameters = canvasRequest.getContext().getEnvironmentContext().getParameters();
		  sObjectId = (String)parameters.get("sObjectId");
	      model.addAttribute("sObjectId", sObjectId) ;
		}
		try {
			model.addAttribute("uploadedFiles",fileUploadDAO.getFilesForSObject(sObjectId));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "upload";
	}
	
	
	 
	 @RequestMapping(value = "/upload", method = RequestMethod.POST)
	    public String upload(@ModelAttribute("uploadForm") FileUploadForm uploadForm,
	                    Model modelMap) {
	         
	        MultipartFile uploadedFile = uploadForm.getFile();
	        String sObjectId = uploadForm.getsObjectId();
	        
	         
	        if(null != uploadedFile) {
	           
	 
	                String fileName = uploadedFile.getOriginalFilename();
	           
	                //Handle file content - multipartFile.getInputStream()
	                try {
						fileUploadDAO.uploadFile(fileName, sObjectId, uploadedFile.getInputStream());
						modelMap.addAttribute("uploadedFiles",fileUploadDAO.getFilesForSObject(sObjectId));
						modelMap.addAttribute("sObjectId",sObjectId);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						modelMap.addAttribute("error", e.getMessage());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						modelMap.addAttribute("error", e.getMessage());
					}
	                modelMap.addAttribute("file", fileName);
	                
	            }
	        
	        
	        
	        return "upload";
	    }


@RequestMapping(value = "/delete", method = {RequestMethod.POST,RequestMethod.GET} )
public String deleteFile(@RequestParam(value="id") String id,@RequestParam(value="sObjectId") String sObjectId,
		                 Locale locale, Model model) {
	try {
		fileUploadDAO.deleteFile(id);
		model.addAttribute("uploadedFiles",fileUploadDAO.getFilesForSObject(sObjectId));
		model.addAttribute("sObjectId",sObjectId);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return "upload";
}

}