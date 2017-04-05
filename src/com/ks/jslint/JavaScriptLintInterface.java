package com.ks.jslint;

public interface JavaScriptLintInterface {
	
	public void findUnusedVariables(String fileName);
	
	public void findSingleLinedConditionals(String fileName);
	
	public void findUndeclaredFunctions(String fileName);
	
	public void findUnbalancedBraces(String fileName);	

}
