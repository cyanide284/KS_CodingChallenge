package com.ks.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class JavaScriptParser {
	
	public HashMap<Integer,String> readFile(String FILENAME) {

		BufferedReader br = null;
		FileReader fr = null;
		
		HashMap<Integer, String> parsedJS = new HashMap<Integer, String>();

		try {
			
			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);

			String currentLine;
			int lineNumber = 0;
			
			while ((currentLine = br.readLine()) != null) {
				lineNumber++;
				parsedJS.put(lineNumber, currentLine);
			}
			
		}

		catch (IOException ioe) {
			ioe.getMessage();
		}

		finally {

			try {

				if(br!=null)
					br.close();
				if(fr!=null)
					fr.close();

			}
			
			catch (IOException ioe) {
				ioe.getMessage();
			}
		}
		
		return parsedJS;

	}
	
}