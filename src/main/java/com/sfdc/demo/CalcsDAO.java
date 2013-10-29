package com.sfdc.demo;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalcsDAO {

	@Autowired
	BasicDataSource dataSource;

	public HashMap<String, String> getCalcs(String state,
			String transportUnits, String transportType, String year) {
		HashMap<String, String> returnValues = new HashMap<String, String>();
		returnValues.put("state", state);
		returnValues.put("transportUnits", transportUnits);
		returnValues.put("transportType", transportType);
		returnValues.put("year", year);

		try {

			double personalIncome = getPersonalIncome(state, year);
			returnValues.put("personalIncome",
					this.roundTwoDecimals(new Double(personalIncome)));

			double population = getPopulation(state, year);
			returnValues.put("population", round(population));

			double stateGDP = personalIncome / population;
			double gdpOfIndustry = this.getGDPOfIndustry(state, year,
					transportUnits,transportType);

			
			returnValues.put("gdpOfIndustry",
					roundTwoDecimals(gdpOfIndustry));

			Map<String, String> transportationModeCosts = this
					.getTransportationModeCosts(state, transportType);
			returnValues.putAll(transportationModeCosts);

			double impact = this
					.getImpact(gdpOfIndustry, Double.parseDouble(returnValues
							.get("gdpImpactPerUnitYR1")), Long
							.parseLong(transportUnits), Double
							.parseDouble(returnValues.get("costPerUnitMil")));

			double impactDeltaPerPerson = (impact * 1000000) / population;
			double perCapitaIncome = stateGDP * 1000;
			int pollutionIndex = Integer.parseInt(returnValues
					.get("pollutionIndex"));
			returnValues.put("impactDelta", roundTwoDecimals(impact));
			returnValues.put("impactDeltaPerPerson",
					roundTwoDecimals(impactDeltaPerPerson));
			returnValues
			.put("perCapitaIncome", this.roundTwoDecimals(perCapitaIncome));
			returnValues.put("newPerCapitaIncome",
					roundTwoDecimals(perCapitaIncome + impactDeltaPerPerson));
			returnValues.put(
					"pollutionImpactDelta",
					round(pollutionIndex
							* Integer.parseInt(transportUnits)));

		} catch (SQLException e) {

			e.printStackTrace();

		}

		return returnValues;
	}

	public long getPopulation(String state, String year) throws SQLException {
		Connection conn = dataSource.getConnection();
		long value = 0;
		Statement st = conn.createStatement();
		StringBuffer query = new StringBuffer();
		query.append("select value from population where area='");
		query.append(state);
		query.append("' and ");
		query.append("year=");
		query.append(year);
		System.out.println(query.toString());
		ResultSet rs = st.executeQuery(query.toString());

		while (rs.next()) {
			value = rs.getLong("value");
		}
		conn.close();
		return value;
	}

	public long getPersonalIncome(String state, String year)
			throws SQLException {
		Connection conn = dataSource.getConnection();
		long value = 0;
		Statement st = conn.createStatement();
		StringBuffer query = new StringBuffer();
		query.append("select value from personal_income where area='");
		query.append(state);
		query.append("' and ");
		query.append("year=");
		query.append(year);
		System.out.println(query.toString());
		ResultSet rs = st.executeQuery(query.toString());

		while (rs.next()) {
			value = rs.getLong("value");
		}
		conn.close();
		return value;
	}

	public double getGDPOfIndustry(String state, String year,
			String transportationUnits, String transportType) throws SQLException {
		Connection conn = dataSource.getConnection();
		Statement st = conn.createStatement();
		StringBuffer query = new StringBuffer();
		query.append("select sum(value) from gdp_data where area='");
		query.append(state);
		query.append("' and ");
		query.append("year='");
		query.append(year);
		query.append("' and ");
		query.append("primary_transport_mode='");
		query.append(transportType);
		query.append("'");
		System.out.println(query.toString());
		ResultSet rs = st.executeQuery(query.toString());
		double gdpOfIndustry = 0;
		if(rs.next()){
			gdpOfIndustry = rs.getDouble(1);
		}
		/* while (rs.next()) {
			gdpOfIndustry += rs.getLong("value");
					//* Long.parseLong(transportationUnits);
		} */
		conn.close();
		return gdpOfIndustry;
	}

	public Map<String, String> getTransportationModeCosts(String state,
			String transportType) throws SQLException {
		Connection conn = dataSource.getConnection();
		HashMap<String, String> values = new HashMap<String, String>();
		Statement st = conn.createStatement();
		StringBuffer query = new StringBuffer();
		query.append("select cost_per_unit_mil,gdp_impact_per_unit_yr1,pollution_index from transport_mode_costs where area='");
		query.append(state);
		query.append("' and ");
		query.append("trim(transportation_mode)='");
		query.append(transportType);
		query.append("'");
		System.out.println(query.toString());
		ResultSet rs = st.executeQuery(query.toString());

		while (rs.next()) {
			values.put("costPerUnitMil", rs.getString("cost_per_unit_mil"));
			values.put("gdpImpactPerUnitYR1",
					rs.getString("gdp_impact_per_unit_yr1"));
			values.put("pollutionIndex", rs.getString("pollution_index"));

		}
		conn.close();
		return values;
	}

	/*
	 * ((GDPOfIndustry*(1+impactOfTransportPerUnit))-(transportUnits*
	 * TransportCostPerUnit))-(GDPOfIndustry)
	 */
	public double getImpact(double industryGDP, double impactOfTransportPerUnit,
			long transportUnits, double transportCostPerUnit) {
		return (industryGDP * (1 + impactOfTransportPerUnit))
				- (transportUnits * transportCostPerUnit) - (industryGDP);
	}

	/* public double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	} */
	
	public String roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.00");
		return twoDForm.format(d);
	}
	
	public String round(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#");
		return twoDForm.format(d);
	}
}
