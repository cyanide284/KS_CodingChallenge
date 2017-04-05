package com.ks.jslint;

import java.util.HashMap;

public interface JavaScriptLintInterface {
	
	void findUnusedVariables(String fileName, HashMap<String,String> variables);
	
	void findSingleLinedConditionals(String fileName);
	
	void findUndeclaredFunctions(String fileName);
	
	void findUnbalancedBraces(String fileName);
	
	HashMap<String,String> detectVariables(String fileName);

}
