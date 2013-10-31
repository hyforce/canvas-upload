package com.sfdc.demo;

public class FileObj {
  public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getsObjectId() {
		return sObjectId;
	}
	public void setsObjectId(String sObjectId) {
		this.sObjectId = sObjectId;
	}
  protected long id;
  protected String fileName;
  protected String sObjectId;
}
