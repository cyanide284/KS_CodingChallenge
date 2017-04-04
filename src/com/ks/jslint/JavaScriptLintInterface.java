package com.ks.jslint;

public interface JavaScriptLintInterface {
	
	public void findUnusedVariables();
	
	public void findSingleLinedConditionals(String fileName);
	
	public void findUndeclaredFunctions();
	
	public void findUnbalancedBraces();	

}
