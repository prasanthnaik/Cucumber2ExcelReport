package com.project.Reporter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Reporting {

	static Utilities Utility=new Utilities();
	static ExcelUtilities dataUtil=new ExcelUtilities();
	private static String ResultsLocation;
	public static void main(String[] args) throws Exception{
		if(args.length>0) {
			ResultsLocation=args[0];
		}else {
			ResultsLocation="provide your path here if you are using Eclipse IDE";
			//ResultsLocation="C:\\Report_Analyzer\\Karate-cucumber-html-reports";

		}
		String AllFailedSteps="div[class='row']>div>div>div[class='elements inner-level']>div[class='element']>div>div>div>div[class='step']:nth-child(n)>div.failed+div>div>div>pre";
		try {
			dataUtil.connectExcel();
			List<File> Report=Utility.getFilesToList(ResultsLocation, ".html");
			for(File ReportFile:Report) {
				Document doc = Jsoup.parse(new File(ReportFile.toString()),"utf-8");
				List<Element> FailedSteps = new ArrayList<Element>();
				FailedSteps=doc.select(AllFailedSteps);
				if(FailedSteps.size()>0) {
					System.out.println("******************"+ReportFile.toString()+" - "+FailedSteps.size()+" Failed Test Cases******************");
					System.out.println("-----------------------------------------------------------------");
					String UniqueMethods;
					String LastMethod;
					try {
						//Flow Extractor

						String AllPayloadSteps="div[class='row']>div>div>div[class='elements inner-level']>div[class='element'] div[class='step'] div.docstring pre";
						List<Element> DocStringStep = new ArrayList<Element>();
						List<String> ListOfMethods = new ArrayList<String>();
						List<String> ListOfUniqueMethods = new ArrayList<String>();
						DocStringStep=doc.select(AllPayloadSteps);
						for(Element payloadStep:DocStringStep) {
							String Method=Utility.getJsonData(payloadStep.toString(),"\"method\":");
							ListOfMethods.add(Method);
						}
						ListOfMethods.removeAll(Collections.singleton(""));
						ListOfUniqueMethods=Utility.removeDuplicates(ListOfMethods);
						String AllMethods=ListOfUniqueMethods.toString().trim();
						String RemoveMethodLabel=AllMethods.replaceAll("\"method\": \"", "");
						String RemoveSpecialChar=RemoveMethodLabel.replaceAll("\",,  ", " >");
						UniqueMethods=RemoveSpecialChar.replaceAll("\",", "").trim();
						LastMethod=ListOfUniqueMethods.get(ListOfUniqueMethods.size()-1);
						System.out.println("Flow Extracted");
					}catch(Exception e) {
						UniqueMethods="Customized Column for E1P";
						LastMethod="Customized Column for E1P";
					}

					for(Element FailedStep:FailedSteps) {
						//FeatureName Extractor
						Element FailedFeatureName=FailedStep.parent().parent().parent().parent().parent().parent().parent().parent().parent().previousElementSibling().previousElementSibling();
						String FeatureName=FailedFeatureName.select("span.name").text();
						System.out.println("FeatureName Extracted");

						//ScenarioeName Extractor
						Element FailedScenarioeName=FailedStep.parent().parent().parent().parent().parent().parent().parent().previousElementSibling().previousElementSibling();
						String ScenarioeName=FailedScenarioeName.select("div span.name").text();
						System.out.println("ScenarioeName Extracted");

						//Exception Extractor
						String Exception=FailedStep.text();
						System.out.println("Exception Extractor");

						//Failed Method Extractor
						Element MethodStep=FailedStep.parent().parent().parent().parent().previousElementSibling();
						String MethodPayload=MethodStep.select("div pre").text();
						String MethodName=Utility.getJsonData(MethodPayload,"method");
						System.out.println("Method Extracted");

						//Update to Excel
						dataUtil.insertData(Utility.removeRegex(ReportFile.toString()), Utility.removeRegex(FeatureName), Utility.removeRegex(ScenarioeName), Utility.removeRegex(UniqueMethods), Utility.removeRegex(LastMethod), Utility.removeRegex(Exception));
						System.out.println("Updated in DataSheet");
						System.out.println("-----------------------------------------------------------------");
					}
					System.out.println("****************** "+ReportFile.toString()+" Evaluated Successfully "+"******************");
				}
			}
			dataUtil.disconnectExcel();
			System.out.println("****************** Completed Successfully ******************");
		}catch(Exception e) {
			e.printStackTrace();
			dataUtil.disconnectExcel();
		}
	}
}
