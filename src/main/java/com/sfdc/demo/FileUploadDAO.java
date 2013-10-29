package com.sfdc.demo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.DecimalFormat;
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
	
}
