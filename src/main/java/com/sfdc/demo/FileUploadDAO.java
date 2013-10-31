package com.sfdc.demo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileUploadDAO {

	@Autowired
	BasicDataSource dataSource;

	public void uploadFile(String fileName,String sfdcId, InputStream fileStream)
			throws SQLException, IOException {
		Connection conn = dataSource.getConnection();
		
		PreparedStatement ps = conn.prepareStatement("INSERT INTO sfdc_files(sfdc_id,file_name,file) VALUES (?,?, ?)");
		ps.setString(1, sfdcId);
		ps.setString(2,fileName);
		ps.setBinaryStream(3,fileStream,fileStream.available());
		ps.executeUpdate();
		conn.close();
		
	}
	
	public ArrayList<FileObj> getFilesForSObject(String sObjectId)
			throws SQLException {
		Connection conn = dataSource.getConnection();
		

		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select id,file_name,sfdc_id from sfdc_files where sfdc_id='" + sObjectId + "'");
        ArrayList<FileObj> results = new ArrayList<FileObj>();
		while (rs.next()) {
			FileObj file = new FileObj();
			file.setFileName(rs.getString("file_name"));
			file.setId(rs.getLong("id"));
			file.setsObjectId(rs.getString("sfdc_id"));
			
			results.add(file);
		}
		conn.close();
		return results;
	}
	
	public void deleteFile(String id)
			throws SQLException {
		Connection conn = dataSource.getConnection();
		Statement st = conn.createStatement();
		boolean rs = st.execute("delete from sfdc_files where id =" + id + "");
		conn.close();
		
	}
}
	

