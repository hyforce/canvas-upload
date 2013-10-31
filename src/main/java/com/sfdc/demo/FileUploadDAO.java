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
	
	public ArrayList<String> getFileNames()
			throws SQLException {
		Connection conn = dataSource.getConnection();
		

		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select file_name from sfdc_files");
        ArrayList<String> results = new ArrayList<String>();
		while (rs.next()) {
			results.add(rs.getString("file_name"));
		}
		conn.close();
		return results;
	}
	
	public void deleteFile(String fileName)
			throws SQLException {
		Connection conn = dataSource.getConnection();
		Statement st = conn.createStatement();
		boolean rs = st.execute("delete from sfdc_files where file_name ='" + fileName + "'");
		conn.close();
		
	}
}
	

