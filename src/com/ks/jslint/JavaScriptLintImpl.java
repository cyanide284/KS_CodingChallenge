package com.ks.jslint;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ks.parser.JavaScriptParser;

public class JavaScriptLintImpl implements JavaScriptLintInterface {

	@Override
	public void findUnusedVariables() {
		// TODO Auto-generated method stub

	}

	@Override
	public void findSingleLinedConditionals(String fileName) {
		HashMap<Integer, String> parsedJS = new JavaScriptParser().readFile(fileName);
		Iterator iter = parsedJS.entrySet().iterator();

		while (iter.hasNext()) {
			Map.Entry pair = (Map.Entry) iter.next();
			String currentLine = pair.getValue().toString();
			if (currentLine.contains("if") || currentLine.contains("else")) {
				if (currentLine.trim().substring(0, 2).equalsIgnoreCase("if")
						|| currentLine.trim().substring(0, 4).equalsIgnoreCase("else")) {
					System.out.println(pair.getKey() + "." + " " + pair.getValue());
				}
			}
		}
	}

	@Override
	public void findUndeclaredFunctions() {
		// TODO Auto-generated method stub

	}

	@Override
	public void findUnbalancedBraces() {
		// TODO Auto-generated method stub

	}

}
