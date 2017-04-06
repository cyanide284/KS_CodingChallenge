package com.ks.jslint;

import java.util.HashMap;

public interface JavaScriptLintInterface {
	
	void findUnusedVariables(HashMap<Integer,String> parsedJS);
	
	void findSingleLinedConditionals(HashMap<Integer,String> parsedJS);
	
	void findUndeclaredFunctions(HashMap<Integer,String> parsedJS);
	
	void findUnbalancedBraces(HashMap<Integer,String> parsedJS);	

}
