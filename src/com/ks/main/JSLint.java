package com.ks.main;

import com.ks.jslint.JavaScriptLintImpl;

public class JSLint {
	
	public static void main(String[] args) {
		String fileName = args[0];
		JavaScriptLintImpl jsLint = new JavaScriptLintImpl();
		jsLint.findSingleLinedConditionals(fileName);
	}

}
