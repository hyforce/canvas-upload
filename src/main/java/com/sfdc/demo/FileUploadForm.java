package com.sfdc.demo;


 
import org.springframework.web.multipart.MultipartFile;
 
public class FileUploadForm {
 
    private MultipartFile file;
    private String sObjectId;

	public String getsObjectId() {
		return sObjectId;
	}

	public void setsObjectId(String sObjectId) {
		this.sObjectId = sObjectId;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
     
  
}