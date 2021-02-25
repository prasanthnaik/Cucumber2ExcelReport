package com.project.Reporter;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Utilities {

	public List<File> getFilesToList(String FilePath, String DotExtension) throws Exception {
		List<File> allFilesInFolder = new ArrayList<File>();
		List<File> filesInFolder = Files.walk(Paths.get(FilePath))
				.filter(Files::isRegularFile)
				.map(Path::toFile)
				.collect(Collectors.toList());
		for (File file:filesInFolder) {
			if((file.toString()).endsWith(DotExtension)) {
				allFilesInFolder.add(file);
			}
		}
		return allFilesInFolder;
	}

	public String removeRegex(String TextWithRegex) {
		String FilterOne=TextWithRegex;
		String FilterTwo=FilterOne.replace("\"", "");
		String FilterThree=FilterTwo.replace("'", "");
		String FilterFour=FilterThree.replace(",", "");
		return FilterFour.trim();
	}

	public String escapeMetaCharacters(String inputString){
		final String[] metaCharacters = {"\\","^","$","{","}","[","]","(",")",".","*","+","?","|","<",">","-","&","%","'",":"};

		for (int i = 0 ; i < metaCharacters.length ; i++){
			if(inputString.contains(metaCharacters[i])){
				inputString = inputString.replace(metaCharacters[i],"\\"+metaCharacters[i]);
			}
		}
		return inputString;
	}

	public String getJsonData(String json, String tagName) {
		String MethodName="";
		try {
			Scanner sc = new Scanner(json); 

			while(sc.hasNext()) {
				String line=sc.nextLine();
				if(line.contains(tagName)) {
					MethodName=line;
					return MethodName;
				}	        	
			}

		}catch(NoSuchElementException E) {
			return MethodName;
		}
		return MethodName;

	}

	public static <T> ArrayList<T> removeDuplicates(List<T> list) 
	{ 
		ArrayList<T> newList = new ArrayList<T>(); 
		for (T element : list) { 
			if (!newList.contains(element)) { 
				newList.add(element); 
			} 
		} 
		return newList; 
	} 
}
