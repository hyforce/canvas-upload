package com.sfdc.demo;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillingDAO {

	@Autowired
	BasicDataSource dataSource;

	public Map<String,Object> getBillingData(String accountNumber)
			throws SQLException {
		Connection conn = dataSource.getConnection();
		
		Statement st = conn.createStatement();
		StringBuffer query = new StringBuffer();
		query.append("select account_number,billing_date,usage,delivery_charge,taxes,total from \"ENMAX_BILLING\" where account_number='");
		query.append(accountNumber);
		query.append("'");
		
		System.out.println(query.toString());
		ResultSet rs = st.executeQuery(query.toString());
        HashMap<String,Object> results = new HashMap<String,Object>();
		while (rs.next()) {
			results.put("accountNumber", rs.getObject("account_number"));
			results.put("usage", rs.getObject("usage"));
			results.put("deliveryCharge", rs.getObject("delivery_charge"));
			results.put("billingDate", rs.getObject("billing_date"));
			results.put("taxes", rs.getObject("taxes"));
			results.put("total", rs.getObject("total"));
		}
		conn.close();
		return results;
	}
	
}
