package com.project.Reporter;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;

public class ExcelUtilities {
	Fillo fillo;
	Connection connection;
	public void connectExcel() throws Exception {
		fillo=new Fillo();
		connection=fillo.getConnection(".\\src\\test\\resources\\ExcelReport.xlsx");
		cleanExcel();
	}
	public void cleanExcel() throws Exception {
		String Query= "DELETE from ConsolidatedReport";
		connection.executeUpdate(Query);
		System.out.println("****************************** Excel cleanup Successful ******************************");
	}
	public void insertData(String FeatureLocation,String FeatureName,String ScenarioeName,String ExecutionFlow,String LastMethodExecuted, String Exception) throws Exception {
		String Query= "INSERT into ConsolidatedReport(FeatureLocation,FeatureName,ScenarioeName,ExecutionFlow,LastMethodExecuted,Exception)VALUES('"+FeatureLocation+"','"+FeatureName+"','"+ScenarioeName+"','"+ExecutionFlow+"','"+LastMethodExecuted+"','"+Exception+"')";
		connection.executeUpdate(Query);
	}
	public void disconnectExcel() throws Exception {
		connection.close();
	}
}
