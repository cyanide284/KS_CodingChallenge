package com.ks.main;

import java.util.HashMap;

import com.ks.jslint.JavaScriptLintImpl;
import com.ks.parser.JavaScriptParser;

public class JSLint {
	
	public static void main(String[] args) {
		String fileName = args[0];
		HashMap<Integer, String> parsedJS = new JavaScriptParser().readFile(fileName);
		JavaScriptLintImpl jsLint = new JavaScriptLintImpl();
		jsLint.findUnusedVariables(parsedJS);
		jsLint.findSingleLinedConditionals(parsedJS);
		jsLint.findUnbalancedBraces(parsedJS);
		jsLint.findUndeclaredFunctions(parsedJS);
	}

}
